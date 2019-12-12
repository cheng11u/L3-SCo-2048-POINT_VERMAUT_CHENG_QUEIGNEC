package p2048.model.reseau;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import serveur.Partie;
import serveur.Protocole;

/**
 * Singleton permettant de communiquer avec le serveur.
 * @author Nicolas QUEIGNEC
 */
public class Reseau {     
    /**
     * Socket connecté au serveur.
     */
    private Socket socket;
    /**
     * Flux qui envoi les messages au serveur.
     */
    private PrintWriter envoyeur;
    /**
     * Flux qui reçoit les messages du serveur.
     */
    private BufferedReader receveur;
    /**
     * Instance utilisée. Pattern Singleton.
     */
    private static Reseau instance;
    
    /**
     * Constructeur. Charge les paramètres (adresse IP et port) du serveur distant depuis un fichier puis se connecte.
     */
    private Reseau(){
        try {
            BufferedReader lectureConf=new BufferedReader(new FileReader(new File("2048.conf")));
            HashMap<String,String> params=new HashMap<String,String>();
            String ligne=null;
            while ((ligne=lectureConf.readLine())!=null) {                
                params.put(ligne.split("=")[0], ligne.split("=")[1]);
            }
            this.socket=new Socket(params.get("IP"), Integer.parseInt(params.get(("PORT"))));
            this.socket.setSoTimeout(3000);
            this.envoyeur=new PrintWriter(this.socket.getOutputStream());
            this.receveur=new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    /**
     * Retourne l'instance actuellement utilisée.
     * @return 
     *  Instance de {@link Reseau}.
     */
    public static synchronized Reseau getInstance() {
        if (instance==null)
            instance=new Reseau();
        else if (instance.socket.isClosed() || !instance.socket.isConnected() || instance.socket.isInputShutdown() || instance.socket.isOutputShutdown()) { 
            instance.deconnecter();
            instance=new Reseau();
        }
        return instance;
    }
    
    /**
     * Envoi un message au serveur.
     * @param message 
     *  Message à envoyer.
     */
    public void envoyerMessage(String message) {
        this.envoyeur.println(message);
        this.envoyeur.flush();
    }
    
    /**
     * Reçoit un message du serveur.
     * @return
     *  Message reçu.
     * @throws IOException 
     *  Exception
     */
    public String recevoirMessage() throws IOException {
        return this.receveur.readLine();
    }
    
    /**
     * Déconnecte du réseau.
     */
    public void deconnecter() {
        try {     
            instance=null;
            this.envoyeur.close();
            this.receveur.close();     
            this.socket.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    /**
     * Pour savoir si on est connecté au serveur.
     * @return 
     *  <code>true</code> si on est bien connecté sinon <code>false</code>.
     */
    public static boolean estConnecter() {
        return instance!=null;
    }
    
    /**
     * Créer une partie en coopération.
     * @return 
     *  Partie créée.
     */
    public Cooperation creerPartieCoop() {
        envoyerMessage(Protocole.REQ_CREER_PARTIE(Partie.TYPE_PARTIE_COOP));
        try {
            String recu=recevoirMessage();
            if (recu.split(Protocole.SEPARATEUR_PARAM)[0].equals(Protocole.REP_CREER_PARTIE_REUSSI))
                return new Cooperation(Integer.parseInt(Protocole.getParams(recu).get("Id")), true);
        } catch (IOException ex) {
               ex.printStackTrace();
        }
        return null;
    }
    
    /**
     * Rejoint une partie en coopération.
     * @param id
     *  Identifiant de la partie à rejoindre.
     * @param nomJoueur
     *  Nom du joueur propriétaire de la partie.
     * @return 
     *  Partie rejoint.
     */
    public Cooperation rejoindrePartieCoop(int id, String nomJoueur) {
        envoyerMessage(Protocole.REQ_REJOINDRE_PARTIE(id));
        try {
            String recu=recevoirMessage();
            if (recu.split(Protocole.SEPARATEUR_PARAM)[0].equals(Protocole.REP_REJOINDRE_PARTIE_REUSSI)) {
                Cooperation cooperation=new Cooperation(id, false);
                cooperation.ajouterJoueur(nomJoueur);
                return cooperation;
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    /**
     * Demandes la récupération des données des parties en coopération.
     * @return 
     *  Liste des parties en coopération disponibles.
     */
    public List<InfosPartie> afficherPartiesCoop() {
        envoyerMessage(Protocole.REQ_AFFICHER_PARTIES(Partie.TYPE_PARTIE_COOP));
        try {
            String recu=recevoirMessage();
            if (recu.split(Protocole.SEPARATEUR_PARAM)[0].equals(Protocole.REP_AFFICHER_PARTIES)) {
                List<InfosPartie> infos=new ArrayList<InfosPartie>();
                String[] ids=Protocole.getParams(recu).get("Ids").split(Protocole.SEPARATEUR_VALEUR_MULTIPLE);
                String[] noms=Protocole.getParams(recu).get("Proprios").split(Protocole.SEPARATEUR_VALEUR_MULTIPLE);
                if (!ids[0].equals(""))
                    for (int i=0;i<ids.length;i++)
                        infos.add(new InfosPartie(Integer.parseInt(ids[i]), noms[i]));
                return infos;
            }
        } catch (IOException ex) {
            return null;
        }
        return null;
    }
    
    /**
     * Demande la récupération du classement des scores des joueurs.
     * @return 
     *  Liste des scores des joueurs.
     */
    public List<JoueurPoints> afficherClassement(){
        List<JoueurPoints> res = new ArrayList<JoueurPoints>();
        envoyerMessage(Protocole.REQ_AFFICHER_CLASSEMENT);
        try {
            String recu = recevoirMessage();
            if (recu.split(Protocole.SEPARATEUR_PARAM)[0].equals(Protocole.REP_AFFICHER_CLASSEMENT)){
                String[] pseudos = Protocole.getParams(recu).get("Pseudos").split(Protocole.SEPARATEUR_VALEUR_MULTIPLE);
                String[] scores = Protocole.getParams(recu).get("Scores").split(Protocole.SEPARATEUR_VALEUR_MULTIPLE);
                if (pseudos.length == scores.length){
                    for (int i=0; i<pseudos.length; i++)
                        res.add(new JoueurPoints(pseudos[i], Integer.parseInt(scores[i])));
                }
            }
        } catch (IOException e){
            res = null;
        }
        return res;
    }
    
    /**
     * Demande la création d'un compte.
     * @param pseudo
     *  Pseudo du compte à créer.
     * @param mdp
     *  Mot de passe du compte.
     * @return 
     *  <code>true</code> si le compte a bien été créé sinon <code>false</code>.
     */
    public boolean ajouterJoueur(String pseudo, String mdp){
        boolean res;
        String message = Protocole.REQ_AJOUTER_COMPTE
                + Protocole.SEPARATEUR_PARAM + "Pseudo" + Protocole.SEPARATEUR_VALEUR_PARAM + pseudo
                + Protocole.SEPARATEUR_PARAM + "MotDePasse" + Protocole.SEPARATEUR_VALEUR_PARAM + mdp;
        envoyerMessage(message);
        try {
            String recu = recevoirMessage();
            if (recu.equals(Protocole.REP_AJOUT_REUSSI))
                res = true;
            else
                res = false;
        }
        catch (IOException e){
            res = false;
        } finally {
            deconnecter();
        }
        return res;
    }
    
    /**
     * Se connecte à un compte.
     * @param pseudo
     *  Pseudo du compte.
     * @param mdp
     *  Mot de passe du compte.
     * @return 
     *  <code>true</code> si la connexion a réussie sinon <code>false</code>.
     */
    public boolean connecter(String pseudo, String mdp){
        boolean res;
        String message = Protocole.REQ_CONNECTER_COMPTE(pseudo, mdp);
        envoyerMessage(message);
        try {
            String recu = recevoirMessage();
            if (recu.equals(Protocole.REP_CONNEXION_REUSSIE))
                res = true;
            else {
                res = false;
                deconnecter();
            }
        }
        catch (IOException e){
            res = false;
        }
        return res;
    }
    
    /**
     * Demande la sauvegarde d'un score dans le classement.
     * @param pseudo
     *  Pseudo du joueur.
     * @param typePartie
     *  Type de la partie dont on souhaite enregistrer le score.
     * @param score
     *  Score de la partie.
     * @return 
     *  <code>true</code> si la sauvegarde a réussie sinon <code>false</code>.
     */
    public boolean ajouterPartie(String pseudo, String typePartie, int score){
        boolean res;
        String message = Protocole.REQ_SAUVEGARDER_SCORE
                + Protocole.SEPARATEUR_PARAM + "Pseudo" + Protocole.SEPARATEUR_VALEUR_PARAM + pseudo
                + Protocole.SEPARATEUR_PARAM + "Type" + Protocole.SEPARATEUR_VALEUR_PARAM + typePartie
                + Protocole.SEPARATEUR_PARAM + "Score" + Protocole.SEPARATEUR_VALEUR_PARAM + score;
        envoyerMessage(message);
        try {
            String recu = recevoirMessage();
            res = recu.equals(Protocole.REP_SAUVEGARDE_REUSSIE);
        }
        catch (IOException e){
            res = false;
        }
        return res;
    }
}
