/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package p2048;

/**
 * Cette classe permet d'accéder à des variables telles que le pseudo de l'utilisateur
 * ou le message d'erreur à afficher.
 * @author Luc Cheng
 */
public class Parametres {
    
    /**
     * Pseudo de l'utilisateur
     */
    private String pseudo;
    
    /**
     * Message d'erreur à afficher
     */
    private String message;
    
    /**
     * Chemin menant vers l'image de fond de l'application
     */
    private String chemin;
    
    
    /**
     * Instance courante
     */
    private static Parametres instance = null;
    
    /**
     * Constructeur privé
     */
    private Parametres(){
        this.pseudo = "";
        this.message = "";
        this.chemin = "";
    }
    
    /**
     * Permet d'obtenir l'instance courante.
     * @return instance courante
     */
    public static synchronized Parametres getInstance(){
       if (instance == null)
           instance = new Parametres();
       return instance;
    }
    
    /**
     * Retourne le pseudo de l'utilisateur
     * @return pseudo
     */
    public String getPseudo(){
        return this.pseudo;
    }
    
    /**
     * Modifie le pseudo
     * @param pseudo nouveau pseudo
     */
    public void setPseudo(String pseudo){
        this.pseudo = pseudo;
    }
    
    /**
     * Retourne le message d'erreur
     * @return message d'erreur
     */
    public String getMessage(){
        return this.message;
    }
    
    /**
     * Modifie le message d'erreur à afficher
     * @param message nouveau message d'erreur
     */
    public void setMessage(String message){
        this.message = message;
    }
    
    /**
     * Retourne le chemin vers l'image de l'interface graphique
     * @return chemin
     */
    public String getChemin(){
        return this.chemin;
    }
    
    /**
     * Modifie le chemin vers l'image de l'interface graphique
     * @param chemin nouveau chemin
     */
    public void setChemin(String chemin){
        this.chemin = chemin;
    }
    
}
