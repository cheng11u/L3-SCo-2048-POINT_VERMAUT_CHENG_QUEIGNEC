/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serveur;

/**
 *
 * @author Luc Cheng
 */
public class Jouer {
    
    private PartieBDD partie;
    private Joueur joueur;
    private int score;
    
    public Jouer(PartieBDD partie, Joueur joueur, int score){
        this.partie = partie;
        this.joueur = joueur;
        this.score = score;
    }
    
    public Jouer(int idPartie, String pseudo, int score){
        this.partie = new PartieBDD(idPartie, "");
        this.joueur = new Joueur(pseudo, "", "", "");
        this.score = score;
    }
    
    public PartieBDD getPartie(){
        return this.partie;
    }
    public void setPartie(PartieBDD partie){
        this.partie = partie;
    }
    public Joueur getJoueur(){
        return this.joueur;
    }
    public void setJoueur(Joueur joueur){
        this.joueur = joueur;
    }
    public int getScore(){
        return this.score;
    }
    public void setScore(int score){
        this.score = score;
    }

}
