/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serveur;

/**
 * Cette classe représente les données de la table jouer.
 * @author Luc Cheng
 */
public class Jouer {
    /**
     * Partie jouée
     */
    private PartieBDD partie;
    /**
     * Joueur ayant joué la partie
     */
    private Joueur joueur;
    /**
     * Score obtenu par le joueur
     */
    private int score;
    
    /**
     * Constructeur
     * @param partie partie jouée
     * @param joueur joueur jouant la partie
     * @param score score obtenu
     */
    public Jouer(PartieBDD partie, Joueur joueur, int score){
        this.partie = partie;
        this.joueur = joueur;
        this.score = score;
    }
    
    /**
     * Retourne la partie
     * @return partie
     */
    public PartieBDD getPartie(){
        return this.partie;
    }
    /**
     * Modifie la partie
     * @param partie nouvelle partie
     */
    public void setPartie(PartieBDD partie){
        this.partie = partie;
    }
    /**
     * Retourne les informations sur le joueur
     * @return joueur
     */
    public Joueur getJoueur(){
        return this.joueur;
    }
    /**
     * Modifie le joueur
     * @param joueur nouveau joueur
     */
    public void setJoueur(Joueur joueur){
        this.joueur = joueur;
    }
    /**
     * Retourne le score obtenu par le joueur
     * @return score
     */
    public int getScore(){
        return this.score;
    }
    
    /**
     * Modifie le score obtenu par le joueur
     * @param score nouveau score
     */
    public void setScore(int score){
        this.score = score;
    }

}
