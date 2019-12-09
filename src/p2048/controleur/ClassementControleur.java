/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package p2048.controleur;

import java.net.URL;
import java.util.*;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import p2048.Parametres;
import p2048.model.reseau.JoueurPoints;
import p2048.model.reseau.Reseau;
/**
 * Cette classe est le controleur de l'affichage du classement
 * @author Luc Cheng
 */
public class ClassementControleur implements Controleur, Initializable { 
    
    /**
     * Elément affichant le classement
     */
    @FXML
    private ListView classement;
    
    /**
     * Bouton permettant de retourner à l'accueil
     */
    @FXML
    private Button retourAcceuil;
    
    /**
     * Initialise le controleur.
     * Affiche les dix premiers du classement, et ajoute le joueur et son rang, s'il 
     * n'en fait pas parti
     * @param url 
     *  URL
     * @param rb 
     *  RessourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        boolean joueurDixPremiers = false;
        List<JoueurPoints> listeClassement = Reseau.getInstance().afficherClassement();
        for (int i=0; i<listeClassement.size() && i<10; i++){
            JoueurPoints joueurPoints = listeClassement.get(i);
            int rang = i+1;
            String pseudo = joueurPoints.getPseudo();
            int score = joueurPoints.getScore();
            String chaine = rang + "    " + pseudo + "    " + score;
            classement.getItems().add(chaine);
            if (pseudo.equals(Parametres.getInstance().getPseudo()))
                joueurDixPremiers = true;
        }
        
        //si le joueur n'est pas dans les dix premiers, on affiche son rang à part
        if (!joueurDixPremiers){
            int rang = 11;
            boolean trouve = false;
            while (rang <= listeClassement.size() && !trouve){
                String pseudoListeClassement = listeClassement.get(rang-1).getPseudo();
                String pseudoJoueur = Parametres.getInstance().getPseudo();
                if (pseudoListeClassement.equals(pseudoJoueur))
                    trouve = true;
                else
                    rang++;
            }
            
            if (trouve){
                JoueurPoints elt = listeClassement.get(rang-1);
                String chaine = rang + "    " + elt.getPseudo() + "    " + elt.getScore();
                classement.getItems().add(chaine);
            }
        }
    }
    
    /**
     * Cette méthode permet de revenir à l'accueil
     * @param e événement
     */
    @FXML
    public void chargerAccueil(Event e){
        if (e.getSource() == retourAcceuil)
            p2048.P2048.changerScene("vue/FXMLAccueil.fxml");
    }
}
