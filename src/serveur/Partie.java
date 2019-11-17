/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serveur;

/**
 * @author Nicolas QUEIGNEC
 */
public class Partie {
    public static final int TYPE_PARTIE_COOP=1;
    public static final int TYPE_PARTIE_COMPET=2;
    private final int typePartie;
    private static int idDispo=0;
    private int id;
    private Client client1;
    private Client client2;
    private boolean client1Pret;
    private boolean client2Pret;
    
    public Partie(int type, Client client) {
        this.typePartie=type;
        this.id=idDispo;
        idDispo++;
        this.client1=client;
        this.client1Pret=false;
        this.client2Pret=false;
    }
    
    public boolean estJoignable() {
        return client2==null && !client1Pret && !client2Pret;
    }
    
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

    public void quitter(Client client) {
        if (client.equals(client1)) {
            client1=null;
            if (client2!=null)
                client2.envoyerMessage(Protocole.REP_A_QUITTER(client));
        }
        else if (client.equals(client2)) {
            client2=null;
            if (client1!=null)
                client1.envoyerMessage(Protocole.REP_A_QUITTER(client));
        }
        if (client1==null && client2==null)
            Main.parties.remove(this.id);
    } 
    
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
    
    public void jouer(Client client, int direction){
        if (client.equals(client1) && client2!=null) {
            client2.envoyerMessage(Protocole.REP_A_JOUER(client, direction));
        } else if (client.equals(client2) && client1!=null) {
            client1.envoyerMessage(Protocole.REP_A_JOUER(client, direction));
        }
    }
    
    public int getId() {
        return id;
    }   

    public Client getClient1() {
        return client1;
    }


    public int getTypePartie() {
        return typePartie;
    }
}
