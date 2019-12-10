package serveur;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Client connecté au serveur. Reçoit ses messages et agit en fonction.
 * @author Nicolas QUEIGNEC
 */
public class Client implements Runnable {
    /**
     * Flux pour envoyer des messages au client.
     */
    private PrintWriter envoyeut;
    /**
     * Flux pour recevoir les messages du client.
     */
    private BufferedReader receveur;
    /**
     * Socket connecté au client.
     */
    private Socket socket;
    /**
     * Partie que le client a rejoint et est est en cours.
     */
    private Partie partieEnCours;
    /**
     * Pseudo du compte connecté.
     */
    private String pseudo;
    
    /**
     * Constructeur.
     * @param sock 
     *  Socket dont on doit recevoir les messages.
     */
    public Client(Socket sock) {
        try {
            this.envoyeut=new PrintWriter(sock.getOutputStream());
            this.receveur=new BufferedReader(new InputStreamReader(sock.getInputStream()));
            this.socket=sock;
        } catch (IOException ex) {
            System.err.println("Erreur lors de la connexion de "+sock.getInetAddress());
        }   
    }
    
    /**
     * Créer une partie.
     * @param type 
     *  Type de la partie. {@link Partie#TYPE_PARTIE_COOP}
     */
    private void creerPartie(int type) {
        if (partieEnCours==null) {
            this.partieEnCours=new Partie(type, this);
            Main.parties.put(this.partieEnCours.getId(), this.partieEnCours);
            envoyerMessage(Protocole.REP_CREER_PARTIE_REUSSI(this.partieEnCours.getId()));
       }
       envoyerMessage(Protocole.REP_CREER_PARTIE_ECHOUE);
   }
    
    /**
     * Rejoint une partie.
     * @param id
     *  Identifiant de la partie.
     * @return 
     *  <code>true</code> si la partie a bien été rejoint sinon <code>false</code>.
     */
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
    
    /**
     * Quitte la partie.
     */
    public void quitterPartie() {
        if (partieEnCours!=null) {
            this.partieEnCours.quitter(this);
            this.partieEnCours=null;
        } 
    }
    
    /**
     * Joue dans la direction passée en paramètre.
     * @param direction 
     *  Direction.
     */
    public void jouer(int direction) {
        if (partieEnCours!=null)
            this.partieEnCours.jouer(this, direction);
    }
    
    /**
     * Etre prêt à commenter la partie.
     */
    public void estPret() {
        if (partieEnCours!=null)
            partieEnCours.estPret(this);
    }
    
    /**
     * Envoi un message au client.
     * @param message 
     *  Message à envoyer.
     */
    public void envoyerMessage(String message) {
        this.envoyeut.println(message);
        this.envoyeut.flush();
    }
    
    /**
     * Déconnecte le client du serveur.
     */
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
                        this.partieEnCours.mouvRecu();
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
                        break;
                    case Protocole.REQ_AJOUTER_COMPTE:
                        String pseudoJoueur = Protocole.getParams(ligne).get("Pseudo");
                        String mdpJoueur = Protocole.getParams(ligne).get("MotDePasse");
                        boolean ajoutReussi = RequetesBDD.creerJoueur(pseudoJoueur, "", "", mdpJoueur);
                        if (ajoutReussi)
                            envoyerMessage(Protocole.REP_AJOUT_REUSSI);
                        else {
                            envoyerMessage(Protocole.REP_AJOUT_ECHOUE);
                            deconnecter();
                        }
                        break;
                    case Protocole.REQ_CONNECTER_COMPTE:
                        String pseudoConnexion = Protocole.getParams(ligne).get("Id");
                        String mdpConnexion = Protocole.getParams(ligne).get("Mdp");
                        Joueur joueurBDD = RequetesBDD.donnerJoueur(pseudoConnexion);
                        if (joueurBDD == null || !joueurBDD.getMdp().equals(mdpConnexion)){
                            envoyerMessage(Protocole.REP_CONNEXION_ECHOUEE);
                            deconnecter();
                        }
                        else { 
                            envoyerMessage(Protocole.REP_CONNEXION_REUSSIE);
                            this.pseudo=pseudoConnexion;
                        }
                        break;
                    case Protocole.REQ_SAUVEGARDER_SCORE:
                        String pseudoSauvegarde = Protocole.getParams(ligne).get("Pseudo");
                        String typePartie = Protocole.getParams(ligne).get("Type");
                        int score = Integer.parseInt(Protocole.getParams(ligne).get("Score"));
                        boolean sauvegardeReussie = RequetesBDD.insererPartie(typePartie)
                        && RequetesBDD.insererJouer(pseudoSauvegarde, score);
                        if (sauvegardeReussie)
                            envoyerMessage(Protocole.REP_SAUVEGARDE_REUSSIE);
                        else {
                            envoyerMessage(Protocole.REP_SAUVEGARDE_ECHOUEE);
                            deconnecter();
                        }
                        break;
                }
            } catch (IOException ex) {
                deconnecter();
            }
        }
    }

    @Override
    public String toString() {
        return pseudo!=null?pseudo:socket.getInetAddress().getHostAddress();
    }
    
    
}
