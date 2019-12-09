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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import p2048.P2048;
import p2048.model.reseau.Cooperation;
import p2048.model.reseau.PartieReseau;
import p2048.model.reseau.Reseau;

/**
 * Cette classe est responsable des événement ayant lieu sur l'interface d'attente d'une partie
 * @author Nicolas QUEIGNEC
 */
public class AttentePartieControleur implements Controleur{
    /**
     * Label représentant le joueur 1
     */
    @FXML
    private Label j1;
    /**
     * Label représentant le joueur 2
     */
    @FXML
    private Label j2;
    /**
     * Statut du joueur 1
     */
    @FXML
    private Label statut1;
    /**
     * Statut du joueur 2
     */
    @FXML
    private Label statut2;
    /**
     * Bouton permettant de lancer la partie
     */
    @FXML
    private Button go;
    /**
     * Partie à créer
     */
    private PartieReseau partie;
    /**
     * Indique si la partie à créer est une partie en coopération
     */
    private boolean partieCoop;
    
    /**
     * Crée une partie en coopération
     */
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
            }
        });
        partieCoop=true;
        update();
    }
    
    /**
     * Méthode permettant de rejoindre une partie en coopération
     * @param partie partie en cours
     */
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
            }
        });
        partieCoop=true;
        update();
    }
    
    /**
     * Met à jour les différentes informations
     */
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
        if (partie.joueur1pret() && partie.joueur2pret())
            go.setText("Commencer la partie");
    }
    
    /**
     * Cette méthode permet de retourner à l'accueil
     */
    @FXML
    public void retourAccueil(){
        partie.quitter();
        P2048.changerScene("vue/FXMLAccueil.fxml");
    }
    
    /**
     * Initialise la partie
     */
    @FXML
    public void pret() {
        partie.pret();
        if (partie.joueur1pret() && partie.joueur2pret() && partieCoop)
           ((SoloControleur)P2048.changerScene("vue/FXMLSolo.fxml")).initCoop((Cooperation)partie);
    } 
}
