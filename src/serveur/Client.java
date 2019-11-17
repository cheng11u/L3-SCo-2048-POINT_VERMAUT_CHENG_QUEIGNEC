/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serveur;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Nicolas QUEIGNEC
 */
public class Client implements Runnable {
    private PrintWriter envoyeut;
    private BufferedInputStream receveur;
    private Partie partieEnCours;
    
    public Client(Socket sock) {
        try {
            this.envoyeut=new PrintWriter(sock.getOutputStream());
            this.receveur=new BufferedInputStream(sock.getInputStream());
        } catch (IOException ex) {
            System.err.println("Erreur lors de la connexion de "+sock.getInetAddress());
        }   
    }
    
    private void creerPartie(int type) {
        this.partieEnCours=new Partie(type, this);
        Main.parties.put(this.partieEnCours.getId(), this.partieEnCours);
    }
    
    private boolean rejoindrePartie(int id) {
        this.partieEnCours=Main.parties.get(id);
        if (this.partieEnCours.rejoindre(this))
            return true;
        this.partieEnCours=null;
        return false;
    }
    
    public void quitterPartie() {
        this.partieEnCours.quitter(this);
        this.partieEnCours=null;
    }
    
    public void jouer(int direction) {
        
    }
    
    public void envoyerMessage(String message) {
        this.envoyeut.println(message);
        this.envoyeut.flush();
    }
    
    @Override
    public void run() {

    }
}
