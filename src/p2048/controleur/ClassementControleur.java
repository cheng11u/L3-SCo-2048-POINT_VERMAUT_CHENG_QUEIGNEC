/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package p2048.controleur;

import java.net.URL;
import java.util.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import p2048.model.reseau.JoueurPoints;
import p2048.model.reseau.Reseau;
/**
 * Cette classe est le controleur de l'affichage du classement
 * @author Luc Cheng
 */
public class ClassementControleur implements Controleur, Initializable { 
    
    @FXML
    private ListView classement;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        List<JoueurPoints> listeClassement = Reseau.getInstance().afficherClassement();
        for (int i=0; i<listeClassement.size(); i++){
            JoueurPoints joueurPoints = listeClassement.get(i);
            int rang = i+1;
            String pseudo = joueurPoints.getPseudo();
            int score = joueurPoints.getScore();
            String chaine = rang + "    " + pseudo + "    " + score;
            classement.getItems().add(chaine);
        }
    }
}
