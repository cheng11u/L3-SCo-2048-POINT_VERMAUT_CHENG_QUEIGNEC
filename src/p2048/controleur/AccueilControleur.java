/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package p2048.controleur;

import javafx.fxml.FXML;

/**
 *
 * @author Nicolas QUEIGNEC
 */
public class AccueilControleur implements Controleur {
    @FXML
    public void creerSolo() {
        ((SoloControleur)p2048.P2048.changerScene("vue/FXMLSolo.fxml")).nouvellePartie();
    }
    
    @FXML
    public void chargerSolo() {
        ((SoloControleur)p2048.P2048.changerScene("vue/FXMLSolo.fxml")).chargerPartie();
    }
    
    @FXML
    public void afficherRegles(){
        p2048.P2048.changerScene("vue/FXMLRegles.fxml");
    }
}
