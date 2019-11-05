/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package p2048.controleur;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javax.xml.bind.Marshaller;
import p2048.model.CubeGrille;
import p2048.P2048;
import p2048.model.Solo;
/**
 *
 * @author Nicolas QUEIGNEC
 */
public class SoloControleur implements Controleur, Initializable {
    @FXML
    private Button haut;
    @FXML
    private Button bas;
    @FXML
    private Button gauche;
    @FXML
    private Button droite;
    @FXML
    private Button inf;
    @FXML
    private Button sup;
    @FXML
    private GridPane etage0;
    @FXML
    private GridPane etage1;
    @FXML
    private GridPane etage2;
    @FXML
    private Button quitter;
    @FXML
    private Button enregistrer;
    @FXML
    private Label points;
    private List<Pane> panes=new ArrayList<Pane>();
    private Solo solo;
    private ChangeListener listener;
    
    @FXML
    public void buttonClicked(Event e) {
        CubeGrille grille = solo.getGrille();
        if (e.getSource()==haut)
            grille.setDirection(CubeGrille.DIR_HAUT);
        else if (e.getSource()==bas)
            grille.setDirection(CubeGrille.DIR_BAS);
        else if (e.getSource()==gauche)
            grille.setDirection(CubeGrille.DIR_GAUCHE);
        else if (e.getSource()==droite)
            grille.setDirection(CubeGrille.DIR_DROITE);
        else if (e.getSource()==sup)
            grille.setDirection(CubeGrille.DIR_DESSUS);
        else if (e.getSource()==inf)
            grille.setDirection(CubeGrille.DIR_DESSOUS);
        else if (e.getSource()==quitter) {
            solo.quitterPartie();   
            P2048.changerScene("vue/FXMLAccueil.fxml");
        }
        else if (e.getSource()==enregistrer)
            solo.sauvegarder();
    }
    
    public void update() {
        etage0.getChildren().removeAll(panes);
        etage1.getChildren().removeAll(panes);
        etage2.getChildren().removeAll(panes);
        panes.clear();
        CubeGrille grille = solo.getGrille();
        points.setText(grille.getScore()+"");
        for (int numGrille=0; numGrille<3; numGrille++) {
            int[][] grilleEtage=grille.getGrilleEtage(numGrille);
            for (int i=0; i<3; i++)
                for (int j=0; j<3; j++) {
                    Pane pane=null;
                    if (grilleEtage[i][j]!=1) {
                        pane=new Pane();
                        pane.setStyle("-fx-background-color:#999999");
                        
                        Label label=new Label(grilleEtage[i][j]+"");
                        label.setTextFill(Color.web("#000000"));
                        label.setFont(new Font(25));
                        label.setPadding(new Insets(30));
                        pane.getChildren().add(label);
                        panes.add(pane);
                        if (numGrille==0)
                            etage0.add(pane, i, j);
                        else if (numGrille==1)
                            etage1.add(pane, i, j);
                        else
                            etage2.add(pane, i, j);
                    }
                }
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Solo partie=new Solo(); 
        this.solo=partie;
        listener=new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() { 
                        update();
                    }
                });
            }
        };
    }
    
    public void nouvellePartie() {
        solo.getGrille().ajouterListenerCases(listener);
        solo.commencerPartie();
    }
    
    public void chargerPartie() {
        solo.charger();
        nouvellePartie();
    }
}
