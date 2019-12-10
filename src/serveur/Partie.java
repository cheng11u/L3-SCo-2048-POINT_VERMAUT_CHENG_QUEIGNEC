package serveur;

import java.util.Random;

/**
 * Partie permettant de relier et gérer 2 clients.
 * @author Nicolas QUEIGNEC
 */
public class Partie {
    /**
     * Type de partie : coopération.
     */
    public static final int TYPE_PARTIE_COOP=1;
    /**
     * Type de partie : compétitivité.
     */
    public static final int TYPE_PARTIE_COMPET=2;
    /**
     * Type de la partie.
     */
    private final int typePartie;
    /**
     * Identifiant de partie encore disponible.
     */
    private static int idDispo=0;
    /**
     * Identifiant de la partie.
     */
    private int id;
    /**
     * Client qui a créé la partie.
     */
    private Client client1;
    /**
     * Client qui a rejoint la partie.
     */
    private Client client2;
    /**
     * Pour savoir si le client 1 est prêt à commencer la partie.
     */
    private boolean client1Pret;
    /**
     * Pour savoir si le client 3 est prêt à commencer la partie.
     */
    private boolean client2Pret;
    /**
     * Pour savoir si un mouvement a été initié par un joueur. <code>true</code> tant que l'autre joueur n'a pas reçu le mouvement.
     */
    private boolean mouvementEnCours;
    
    /**
     * Constructeur.
     * @param type
     *  {@link #typePartie}
     * @param client 
     * {@link #client1}
     */
    public Partie(int type, Client client) {
        this.typePartie=type;
        this.id=idDispo;
        idDispo++;
        this.client1=client;
        this.client1Pret=false;
        this.client2Pret=false;
        this.mouvementEnCours=false;
    }
    
    /**
     * Pour savoir si la partie est joignable par un autre joueur.
     * @return 
     *  <code>true</code> si la partie est joignable par un autre joueur sinon <code>false</code>.
     */
    public boolean estJoignable() {
        return client2==null && !client1Pret && !client2Pret;
    }
    
    /**
     * Ajoute un client à la partie si possible.
     * @param client
     *  Client qui veut rejoindre la partie.
     * @return 
     * <code>true</code> si le client a bien rejoint la partie sinon <code>false</code>.
     */
    public synchronized boolean rejoindre(Client client) {
        if (estJoignable()) {
            this.client2=client;
            client.envoyerMessage(Protocole.REP_REJOINDRE_PARTIE_REUSSI);
            this.client1.envoyerMessage(Protocole.REP_A_REJOINT_PARTIE(client));
            return true;
        }
        client.envoyerMessage(Protocole.REP_REJOINDRE_PARTIE_ECHOUE);
        return false;
    }

    /**
     * Permet au climat passé en paramètre de quitter la partie.
     * @param client 
     *  Client ayant quitté la partie.
     */
    public void quitter(Client client) {
        if (client.equals(client1)) {
            client1=null;
            if (client2!=null) {
                client2.envoyerMessage(Protocole.REP_A_QUITTER(client));
                if (!client1Pret && client2Pret)
                    client2Pret=false;
            } 
        }
        else if (client.equals(client2)) {
            client2=null;
            if (client1!=null) { 
                client1.envoyerMessage(Protocole.REP_A_QUITTER(client));
                if (client1Pret && !client2Pret)
                    client1Pret=false;
            } 
        }
        if (client1==null && client2==null)
            Main.parties.remove(this.id);
    } 
    
    /**
     * Signifie que le client passé en paramètre est prêt à commencer.
     * @param client 
     *  Client qui est prêt.
     */
    public void estPret(Client client){
        if (client.equals(client1)) {
            client1Pret=true;
            client2.envoyerMessage(Protocole.REP_PRET(client));
        } 
        else if (client.equals(client2)) { 
            client2Pret=true;
            client1.envoyerMessage(Protocole.REP_PRET(client));
        } 
    }
    
    /**
     * Permet à un client de jouer dans une direction.
     * @param client
     *  Client qui a joué.
     * @param direction 
     *  Direction dans laquelle il client a joué.
     */
    public void jouer(Client client, int direction){
        if (typePartie==TYPE_PARTIE_COOP) {
            synchronized (this) { 
                if (!mouvementEnCours) { 
                    if ((client2!=null && client==client1) || (client1!=null && client==client2))
                        mouvementEnCours=true;
                    if (client2!=null && client==client1)
                        client2.envoyerMessage(Protocole.REP_A_JOUER(client, direction));
                    else if (client1!=null && client==client2)
                        client1.envoyerMessage(Protocole.REP_A_JOUER(client, direction));
                } 
            } 
        }
        else if (client.equals(client1) && client2!=null) {
            client2.envoyerMessage(Protocole.REP_A_JOUER(client, direction));
        } else if (client.equals(client2) && client1!=null) {
            client1.envoyerMessage(Protocole.REP_A_JOUER(client, direction));
        }
    }
    
    /**
     * Passe {@link #mouvementEnCours} à <code>false</code>.
     */
    public void mouvRecu() {
        mouvementEnCours=false;
    }
    
    /**
     * Permet à un client de demander la création d'une case aléatoire.
     * @param client 
     *  Client qui veut créé une case.
     */
    public synchronized void creerCase(Client client) {
        if (client==client1 || (client==client2 && client1==null)) { 
            Random r=new Random();
            int indexCase=r.nextInt(27);
            int val=r.nextDouble()<0.66?2:4;
            if (client1!=null)
                client1.envoyerMessage(Protocole.REP_CREER_CASE(indexCase, val));   
            if (client2!=null)
                client2.envoyerMessage(Protocole.REP_CREER_CASE(indexCase, val));
        }
    }

    /**
     * Getter.
     * @return
     *  {@link #id}
     */
    public int getId() {
        return id;
    }   

    /**
     * Getter.
     * @return
     *  {@link #client1}
     */
    public Client getClient1() {
        return client1;
    }

    /**
     * Getter.
     * @return
     *  {@link #typePartie}
     */
    public int getTypePartie() {
        return typePartie;
    }
}
