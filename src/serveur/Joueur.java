/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serveur;

import java.sql.*;

/**
 *
 * @author Luc Cheng
 */
public class Joueur {
    
    private String pseudo;
    private String nom;
    private String prenom;
    private String mdp;
    
    public Joueur(String pseudo, String nom, String prenom, String mdp){
        this.pseudo = pseudo;
        this.nom = nom;
        this.prenom = prenom;
        this.mdp = mdp;
    }
    
    public String getPseudo(){
        return this.pseudo;
    } 
    
    public void setPseudo(String pseudo){
        this.pseudo = pseudo;
    }
    
    public String getNom(){
        return this.nom;
    }
    
    public void setNom(String nom){
        this.nom = nom;
    }
    
    public String getPrenom(){
        return this.prenom;
    }
    
    public void setPrenom(String prenom){
        this.prenom = prenom;
    }
    
    public String getMdp(){
        return this.mdp;
    }
}
