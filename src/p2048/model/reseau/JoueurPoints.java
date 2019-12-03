/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package p2048.model.reseau;

/**
 *
 * @author Luc_2
 */
public class JoueurPoints {
    
    private String pseudo;
    private int score;
    
    public JoueurPoints(String pseudo, int score){
        this.pseudo = pseudo;
        this.score = score;
    }
    
    public String getPseudo(){
        return this.pseudo;
    }
    
    public void setPseudo(String pseudo){
        this.pseudo = pseudo;
    }
    
    public int getScore(){
        return this.score;
    }
    
    public void setScore(int score){
        this.score = score;
    }
}
