/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serveur;

import java.sql.*;

/**
 * Cette classe représente les données de la table joueur.
 * @author Luc Cheng
 */
public class Joueur {
    /**
     * Pseudo du joueur
     */
    private String pseudo;
    /**
     * Nom du joueur
     */
    private String nom;
    /**
     * Prénom du joueur
     */
    private String prenom;
    /**
     * Mot de passe du joueur
     */
    private String mdp;
    
    /**
     * Constructeur
     * @param pseudo pseudo du joueur
     * @param nom nom du joueur
     * @param prenom prénom du joueur
     * @param mdp mot de passe du joueur
     */
    public Joueur(String pseudo, String nom, String prenom, String mdp){
        this.pseudo = pseudo;
        this.nom = nom;
        this.prenom = prenom;
        this.mdp = mdp;
    }
    
    /**
     * Retourne le pseudo du joueur
     * @return pseudo
     */
    public String getPseudo(){
        return this.pseudo;
    } 
    
    /**
     * Modifie le pseudo du joueur
     * @param pseudo nouveau pseudo
     */
    public void setPseudo(String pseudo){
        this.pseudo = pseudo;
    }
    
    /**
     * Retourne le nom du joueur
     * @return nom
     */
    public String getNom(){
        return this.nom;
    }
    
    /**
     * Modifie le nom du joueur
     * @param nom nouveau nom
     */
    public void setNom(String nom){
        this.nom = nom;
    }
    
    /**
     * Retourne le prénom du joueur
     * @return prénom
     */
    public String getPrenom(){
        return this.prenom;
    }
    
    /**
     * Modifie le prénom du joueur
     * @param prenom nouveau prénom
     */
    public void setPrenom(String prenom){
        this.prenom = prenom;
    }
    
    /**
     * Retourne le mot de passe du joueur
     * @return mot de passe
     */
    public String getMdp(){
        return this.mdp;
    }
}
