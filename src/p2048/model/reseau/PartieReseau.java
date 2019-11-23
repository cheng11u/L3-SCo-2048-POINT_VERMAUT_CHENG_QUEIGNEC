/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package p2048.model.reseau;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import jdk.net.Sockets;
import p2048.model.CubeGrille;
import p2048.model.TypePartie;
import serveur.Protocole;

/**
 * @author Nicolas QUEIGNEC
 */
public abstract class PartieReseau implements TypePartie {
    private int id;
    private boolean joueur1pret;
    private boolean joueur2pret;
    private boolean estJoueur1;
    private String nomAutreJoueur;
    
    public PartieReseau(int id, boolean estJoueur1) {
        this.id=id;
        this.estJoueur1=estJoueur1;
        this.joueur1pret=false;
        this.joueur2pret=false;
        new Thread(new ReceveurServeur(this)).start();
    }
    
    public void pret() {
        if (estJoueur1) {
            joueur1pret=true;
        } else {
            joueur2pret=true;
        }
        Reseau.getInstance().envoyerMessage(Protocole.REQ_PRET);
        if (joueur1pret && joueur2pret)
            commencerPartie();
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    public abstract GrilleReseau getGrilleReseauRecu();
    public abstract GrilleReseau getGrilleReseauEnvoi();
    
    public void ajouterJoueur(String nom) {
        if (nomAutreJoueur==null)
            nomAutreJoueur=nom;
    }
    
    public void enleverJoueur(String nom) {
        if (nomAutreJoueur.equals(nom)) {
            nomAutreJoueur=null;
        }
    }
    
    public void autreJoueurPret(String nom) {
        if (nomAutreJoueur.equals(nom)) {
            if (estJoueur1)
                joueur2pret=true;
            else
                joueur1pret=true;
            if (joueur1pret && joueur2pret)
                commencerPartie();
        }
    }
    
    public void autreJoueurAJouer(String nom, int direction) {
        if (nomAutreJoueur.equals(nom))
            getGrilleReseauRecu().setDirection(direction);
    }
}
