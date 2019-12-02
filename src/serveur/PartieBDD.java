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
public class PartieBDD {
    
    private int id;
    private String typePartie;
    
    public PartieBDD(int id, String typePartie){
        this.id = id;
        this.typePartie = typePartie;
    }
    
    public int getId(){
        return this.id;
    }
    
    public void setId(int id){
        this.id = id;
    }
    
    public String getTypePartie(){
        return this.typePartie;
    }
    
    public void setTypePartie(String typePartie){
        this.typePartie = typePartie;
    }
}
