/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serveur;

/**
 *
 * @author nicol
 */
public class Partie {
    public static final int TYPE_PARTIE_COOP=1;
    public static final int TYPE_PARTIE_COMPET=2;
    private final int typePartie;
    private static int idDispo=0;
    private int id;
    private Client client1;
    private Client client2;
    
    public Partie(int type, Client client) {
        this.typePartie=type;
        this.id=idDispo;
        idDispo++;
        this.client1=client;
    }
    
    public boolean estJoignable() {
        return client2==null;
    }
    
    public synchronized boolean rejoindre(Client client) {
        if (estJoignable()) {
            this.client2=client;
            return true;
        }
        return false;
    }

    public void quitter(Client client) {
        if (client.equals(client1))
            client1=null;
        else if (client.equals(client2))
            client2=null;
        if (client1==null && client2==null)
            Main.parties.remove(this.id);
    }
    
    public void 
    
    public int getId() {
        return id;
    }   

    public Client getClient1() {
        return client1;
    }

    public Client getClient2() {
        return client2;
    }

    public int getTypePartie() {
        return typePartie;
    }
}
