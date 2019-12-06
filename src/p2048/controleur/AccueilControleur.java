/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package p2048.controleur;

import javafx.fxml.FXML;
import p2048.Parametres;

/**
 *
 * @author Nicolas QUEIGNEC
 */
public class AccueilControleur implements Controleur {
    @FXML
    public void creerSolo() {
        if (!Parametres.getInstance().getPseudo().equals(""))
            ((SoloControleur)p2048.P2048.changerScene("vue/FXMLSolo.fxml")).nouvellePartie();
        else
            this.chargerConnexion();
    }
    
    @FXML
    public void chargerSolo() {
        if (!Parametres.getInstance().getPseudo().equals(""))
            ((SoloControleur)p2048.P2048.changerScene("vue/FXMLSolo.fxml")).chargerPartie();
        else
            this.chargerConnexion();
    }
    
    @FXML
    public void chargerConnexion(){
        p2048.P2048.changerScene("vue/FXMLConnexion.fxml");
    }
    @FXML
    public void afficherRegles(){
        p2048.P2048.changerScene("vue/FXMLRegles.fxml");
    }
    
    @FXML
    public void afficherClassement(){
        p2048.P2048.changerScene("vue/FXMLClassement.fxml");
    }    

    public void creerPartieCoop() {
        ((AttentePartieControleur)p2048.P2048.changerScene("vue/FXMLAttentePartie.fxml")).creerPartieCoop();
    }
    
    @FXML
    public void rejoindrePartieCoop(){
        ((ReseauRejoindreControleur)p2048.P2048.changerScene("vue/FXMLRejoindrePartie.fxml")).initCoop();
    }
}
