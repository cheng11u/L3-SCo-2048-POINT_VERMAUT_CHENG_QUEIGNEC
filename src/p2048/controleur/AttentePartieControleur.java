/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package p2048.controleur;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import p2048.P2048;
import p2048.model.reseau.Cooperation;
import p2048.model.reseau.PartieReseau;
import p2048.model.reseau.Reseau;

/**
 *
 * @author Nicolas QUEIGNEC
 */
public class AttentePartieControleur implements Controleur{
    @FXML
    private Label j1;
    @FXML
    private Label j2;
    @FXML
    private Label statut1;
    @FXML
    private Label statut2;
    private PartieReseau partie;
    private boolean partieCoop;
    
    public void creerPartieCoop() {
        partie=Reseau.getInstance().creerPartieCoop();
        partie.ajouterListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        update();
                    }
                });
                if (partie.joueur1pret() && partie.joueur2pret() && partieCoop)
                    ((SoloControleur)P2048.changerScene("vue/FXMLSolo.fxml")).initCoop((Cooperation)partie);
            }
        });
        partieCoop=true;
        update();
    }
    
    public void rejoindrePartieCoop(Cooperation partie) {
        this.partie=partie;
        partie.ajouterListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        update();
                    }
                });
                if (partie.joueur1pret() && partie.joueur2pret() && partieCoop)
                    ((SoloControleur)P2048.changerScene("vue/FXMLSolo.fxml")).initCoop((Cooperation)partie);
            }
        });
        partieCoop=true;
        update();
    }
    
    public void update() {
        if (partie.estJoueur1()) {
           j1.setText("vous");
           j2.setText(partie.getNomAutreJoueur()!=null?partie.getNomAutreJoueur():"En attente");
        } else {
           j1.setText(partie.getNomAutreJoueur());
           j2.setText("vous");
        }
        statut1.setText(partie.joueur1pret()?"Prêt":"Pas prêt");
        statut2.setText(partie.joueur2pret()?"Prêt":"Pas prêt");
    }
    
    @FXML
    public void retourAccueil(){
        partie.quitter();
        P2048.changerScene("vue/FXMLAccueil.fxml");
    }
    
    @FXML
    public void pret() {
        partie.pret();
    } 
}

