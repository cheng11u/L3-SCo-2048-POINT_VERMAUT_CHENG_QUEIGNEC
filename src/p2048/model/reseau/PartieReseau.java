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
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import jdk.net.Sockets;
import p2048.model.CubeGrille;
import p2048.model.TypePartie;
import serveur.Protocole;

/**
 * @author Nicolas QUEIGNEC
 */
public abstract class PartieReseau implements TypePartie {
    private int id;
    private BooleanProperty joueur1pret;
    private BooleanProperty joueur2pret;
    private boolean estJoueur1;
    private StringProperty nomAutreJoueur;
    private Thread receveur;
    private boolean morte;
    
    public PartieReseau(int id, boolean estJoueur1) {
        this.id=id;
        this.estJoueur1=estJoueur1;
        this.joueur1pret=new SimpleBooleanProperty(false);
        this.joueur2pret=new SimpleBooleanProperty(false);
        this.receveur=new Thread(new ReceveurServeur(this));
        this.nomAutreJoueur=new SimpleStringProperty(null);
        this.receveur.start();
        this.morte=false;
    }
    
    public void pret() {
        if (getNomAutreJoueur()!=null) { 
            if (estJoueur1) {
                joueur1pret.set(true);
            } else {
                joueur2pret.set(true);
            }
            Reseau.getInstance().envoyerMessage(Protocole.REQ_PRET);
            if (joueur1pret() && joueur2pret())
                commencerPartie();
        } 
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
        if (getNomAutreJoueur()==null)
            nomAutreJoueur.set(nom);
    }
    
    public void enleverJoueur(String nom) {
        if (nomAutreJoueur.equals(nom)) {
            nomAutreJoueur=null;
            if (estJoueur1 && joueur1pret() && !joueur2pret())
                joueur1pret.set(false);
            else if (!estJoueur1 && !joueur1pret() && joueur2pret())
                joueur2pret.set(false);
        }
    }
    
    public void autreJoueurPret(String nom) {
        if (nomAutreJoueur.equals(nom)) {
            if (estJoueur1)
                joueur2pret.set(true);
            else
                joueur1pret.set(true);
            if (joueur1pret() && joueur2pret())
                commencerPartie();
        }
    }
    
    public void autreJoueurAJouer(String nom, int direction) {
        if (nomAutreJoueur.equals(nom))
            getGrilleReseauRecu().setDirection(direction);
    }
    
    @Override
    public void jouer(int direction) {
        this.getGrilleReseauEnvoi().setDirection(direction);
        Reseau.getInstance().envoyerMessage(Protocole.REQ_JOUER(direction));
    }
    
    @Override
    public void quitter() {
        Reseau.getInstance().envoyerMessage(Protocole.REQ_QUITTER_PARTIE);
        morte=true;
    }

    public boolean estMorte() {
        return morte;
    }

    public String getNomAutreJoueur() {
        return nomAutreJoueur.get();
    }

    public boolean estJoueur1() {
        return estJoueur1;
    }

    public boolean joueur1pret() {
        return joueur1pret.get();
    }

    public boolean joueur2pret() {
        return joueur2pret.get();
    }
    
    public void ajouterListener(ChangeListener listener) {
        joueur1pret.addListener(listener);
        joueur2pret.addListener(listener);
        nomAutreJoueur.addListener(listener);
    }
    
}
