/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serveur;

/**
 * Cette classe représente les données de la table partie.
 * @author Luc Cheng
 */
public class PartieBDD {
    /**
     * Identifiant de la partie
     */
    private int id;
    /**
     * Type de la partie
     */
    private String typePartie;
    
    /**
     * Constructeur
     * @param id identifiant
     * @param typePartie type de la partie
     */
    public PartieBDD(int id, String typePartie){
        this.id = id;
        this.typePartie = typePartie;
    }
    
    /**
     * Retourne l'identifiant de la partie
     * @return identifiant
     */
    public int getId(){
        return this.id;
    }
    
    /**
     * Modifie l'identifiant de la partie
     * @param id nouvel identifiant
     */
    public void setId(int id){
        this.id = id;
    }
    
    /**
     * Retourne le type de la partie
     * @return type de la partie
     */
    public String getTypePartie(){
        return this.typePartie;
    }
    
    /**
     * Modifie le type de la partie
     * @param typePartie type de la partie
     */
    public void setTypePartie(String typePartie){
        this.typePartie = typePartie;
    }
}
