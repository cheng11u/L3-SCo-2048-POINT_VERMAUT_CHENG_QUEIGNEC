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
public class AccueilControleur {
    @FXML
    public void creerSolo() {
        p2048.P2048.changerScene("vue/FXMLSolo.fxml");
    }
}
