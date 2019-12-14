/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package p2048.controleur;

import javafx.fxml.FXML;
import p2048.P2048;

/**
 * Cette classe est responsable de l'interface permettant d'afficher les règles
 * @author Nicolas QUEIGNEC
 */
public class ReglesControleur implements Controleur {
    /**
     * Redirige vers l'accueil
     */
    @FXML
    public void retourAccueil(){
        P2048.changerScene("vue/FXMLAccueil.fxml");
    }
}
