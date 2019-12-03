/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serveur;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import p2048.model.reseau.JoueurPoints;

/**
 * @author Nicolas QUEIGNEC
 */
public class Client implements Runnable {
    private PrintWriter envoyeut;
    private BufferedReader receveur;
    private Socket socket;
    private Partie partieEnCours;
    
    public Client(Socket sock) {
        try {
            this.envoyeut=new PrintWriter(sock.getOutputStream());
            this.receveur=new BufferedReader(new InputStreamReader(sock.getInputStream()));
            this.socket=sock;
        } catch (IOException ex) {
            System.err.println("Erreur lors de la connexion de "+sock.getInetAddress());
        }   
    }
    
    private void creerPartie(int type) {
        if (partieEnCours==null) {
            this.partieEnCours=new Partie(type, this);
            Main.parties.put(this.partieEnCours.getId(), this.partieEnCours);
            envoyerMessage(Protocole.REP_CREER_PARTIE_REUSSI(this.partieEnCours.getId()));
       }
       envoyerMessage(Protocole.REP_CREER_PARTIE_ECHOUE);
   }
    
    private boolean rejoindrePartie(int id) {
        if (partieEnCours==null) {
            this.partieEnCours=Main.parties.get(id);
            if (this.partieEnCours.rejoindre(this))
                return true;
            this.partieEnCours=null;
            return false;
       }
       return false;
    }
    
    public void quitterPartie() {
        if (partieEnCours!=null) {
            this.partieEnCours.quitter(this);
            this.partieEnCours=null;
        } 
    }
    
    public void jouer(int direction) {
        if (partieEnCours!=null)
            this.partieEnCours.jouer(this, direction);
    }
    
    public void estPret() {
        if (partieEnCours!=null)
            partieEnCours.estPret(this);
    }
    
    public void envoyerMessage(String message) {
        this.envoyeut.println(message);
        this.envoyeut.flush();
    }
    
    public void deconnecter(){
        quitterPartie();
        try {
            this.socket.close();
            this.receveur.close();
            this.envoyeut.close();
            Main.clients.remove(this);
        } catch (IOException ex) {
            System.err.println("Erreur");
        }
    }
    
    @Override
    public void run() {
        while (!this.socket.isClosed()) {
            try {
                String ligne=this.receveur.readLine();
                String cmd=ligne.split(Protocole.SEPARATEUR_PARAM)[0];
                switch (cmd) {
                    case Protocole.REQ_CREER_PARTIE:
                        int type=Integer.parseInt(Protocole.getParams(ligne).get("Type"));
                        if (type==Partie.TYPE_PARTIE_COMPET || type==Partie.TYPE_PARTIE_COOP)
                            creerPartie(type);
                        break;
                    case Protocole.REQ_REJOINDRE_PARTIE:
                        rejoindrePartie(Integer.parseInt(Protocole.getParams(ligne).get("Id")));
                        break;
                    case Protocole.REQ_PRET:
                        estPret();
                        break;
                    case Protocole.REQ_JOUER:
                        jouer(Integer.parseInt(Protocole.getParams(ligne).get("Dir")));
                        break;
                    case Protocole.REQ_QUITTER_PARTIE:
                        quitterPartie();
                        break;
                    case Protocole.REQ_AFFICHER_PARTIES:
                        int type2=Integer.parseInt(Protocole.getParams(ligne).get("Type"));
                        ArrayList<Partie> aAfficher=new ArrayList<Partie>();
                        for (Partie p:Main.parties.values())
                            if (p.getTypePartie()==type2 && p.estJoignable())
                                aAfficher.add(p);
                        envoyerMessage(Protocole.REP_AFFICHER_PARTIES(aAfficher));
                        break;
                    case Protocole.ACC_RECEP_A_JOUER:
                        this.partieEnCours.mouvRecu(this);
                        break;
                    case Protocole.REQ_DECONNECTER:
                        deconnecter();
                        break;
                    case Protocole.REQ_AFFICHER_CLASSEMENT:
                        ArrayList<Jouer> classement = RequetesBDD.donnerClassement();
                        String message = Protocole.REP_AFFICHER_CLASSEMENT;
                        String pseudo = Protocole.SEPARATEUR_PARAM + "Pseudos" + Protocole.SEPARATEUR_VALEUR_PARAM;
                        String scores = Protocole.SEPARATEUR_PARAM + "Scores" + Protocole.SEPARATEUR_VALEUR_PARAM;
                        for (int i=0; i<classement.size(); i++){
                            Jouer jouer = classement.get(i);
                            pseudo += jouer.getJoueur().getPseudo();
                            scores += jouer.getScore();
                            if (i < classement.size()-1){
                                pseudo += Protocole.SEPARATEUR_VALEUR_MULTIPLE;
                                scores += Protocole.SEPARATEUR_VALEUR_MULTIPLE;
                            }
                        }
                        envoyerMessage(message + pseudo + scores);
                        break;
                    case Protocole.REQ_CREER_CASE:
                        partieEnCours.creerCase(this);
                }
            } catch (IOException ex) {
                deconnecter();
            }
        }
    }

    @Override
    public String toString() {
        return socket.getInetAddress().getHostAddress();
    }
    
    
}
