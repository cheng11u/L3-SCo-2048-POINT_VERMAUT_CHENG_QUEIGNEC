/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package p2048.controleur;

import javafx.fxml.FXML;
import p2048.Parametres;

/**
 * Cette classe est responsable des événements de la page d'accueil
 * @author Nicolas QUEIGNEC
 */
public class AccueilControleur implements Controleur {
    
    /**
     * Crée une partie solo. Si l'utilisateur n'est pas connecté, il est redirigé vers 
     * la page de connexion
     */
    @FXML
    public void creerSolo() {
        if (!Parametres.getInstance().getPseudo().equals(""))
            ((SoloControleur)p2048.P2048.changerScene("vue/FXMLSolo.fxml")).nouvellePartie();
        else
            this.chargerConnexion();
    }
    
    /**
     * Charge une partie solo. Si l'utilisateur n'est pas connecté, il est redirigé vers la 
     * page de connexion
     */
    @FXML
    public void chargerSolo() {
        if (!Parametres.getInstance().getPseudo().equals(""))
            ((SoloControleur)p2048.P2048.changerScene("vue/FXMLSolo.fxml")).chargerPartie();
        else
            this.chargerConnexion();
    }
    
    /**
     * Affiche la page de connexion
     */
    @FXML
    public void chargerConnexion(){
        p2048.P2048.changerScene("vue/FXMLConnexion.fxml");
    }
    
    /**
     * Affiche la page expliquant les règles du jeu
     */
    @FXML
    public void afficherRegles(){
        p2048.P2048.changerScene("vue/FXMLRegles.fxml");
    }
    
    /**
     * Affiche le classement
     */
    @FXML
    public void afficherClassement(){
        p2048.P2048.changerScene("vue/FXMLClassement.fxml");
    }    
    
    /**
     * Crée une partie multijoueurs en mode coopératif
     */
    public void creerPartieCoop() {
        if (!Parametres.getInstance().getPseudo().equals(""))
            ((AttentePartieControleur)p2048.P2048.changerScene("vue/FXMLAttentePartie.fxml")).creerPartieCoop();
        else
            this.chargerConnexion();
    }
    
    /**
     * Initialise la partie lorsque l'utilisateur la rejoint
     */
    @FXML
    public void rejoindrePartieCoop(){
        if (!Parametres.getInstance().getPseudo().equals(""))
            ((ReseauRejoindreControleur)p2048.P2048.changerScene("vue/FXMLRejoindrePartie.fxml")).initCoop();
        else
            this.chargerConnexion();
    }
}
