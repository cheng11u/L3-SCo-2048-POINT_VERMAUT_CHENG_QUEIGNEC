/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package p2048.controleur;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import p2048.model.CubeGrille;
import p2048.P2048;
import p2048.model.Solo;
/**
 *
 * @author Nicolas QUEIGNEC
 */
public class SoloControleur {
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
    
    private Solo solo;
    
    public void ajouterGrille(Solo solo){
        this.solo=solo;
    }
    
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
        else if (e.getSource()==quitter)
            System.exit(0);
        else if (e.getSource()==enregistrer)
            solo.sauvegarder();
    }
    
    @FXML
    public void keyPressed(Event e){
        CubeGrille grille = this.solo.getGrille();
        if (e instanceof KeyEvent){
            String lettre = ((KeyEvent)e).getCode().getName().toLowerCase();
            switch (lettre){
                case "z":
                    grille.setDirection(CubeGrille.DIR_HAUT);
                    break;
                case "q":
                    grille.setDirection(CubeGrille.DIR_GAUCHE);
                    break;
                case "s":
                    grille.setDirection(CubeGrille.DIR_BAS);
                    break;
                case "d":
                    grille.setDirection(CubeGrille.DIR_DROITE);
                    break;
                case "f":
                    grille.setDirection(CubeGrille.DIR_DESSOUS);
                    break;
                case "r":
                    grille.setDirection(CubeGrille.DIR_DESSUS);
                    break;
            }
        }
    }
    
    public void update() {
        etage0.getChildren().clear();
        etage1.getChildren().clear();
        etage2.getChildren().clear();
        etage0.setGridLinesVisible(false);
        etage0.setGridLinesVisible(true);
        etage1.setGridLinesVisible(false);
        etage1.setGridLinesVisible(true);
        etage2.setGridLinesVisible(false);
        etage2.setGridLinesVisible(true);
        CubeGrille grille = solo.getGrille();
        for (int numGrille=0; numGrille<3; numGrille++) {
            int[][] grilleEtage=grille.getGrilleEtage(numGrille);
            for (int i=0; i<3; i++)
                for (int j=0; j<3; j++) {
                    Pane pane=null;
                    if (grilleEtage[i][j]!=1) {
                        int valeur = grilleEtage[i][j];
                        int logBase2 = (int)(Math.log(valeur)/Math.log(2));
                        pane=new Pane();
                        
                        int vert = (int)(255 - (255.0/11.0)*logBase2);
                        if (vert < 0)
                            vert = 0;
                        pane.setStyle("-fx-background-color:rgb(255," + vert + ",0)");
                        
                        Label label=new Label(valeur+"");
                        label.setTextFill(Color.web("#000000"));
                        label.setFont(new Font(25));
                        label.setPadding(new Insets(30));
                        pane.getChildren().add(label);
                        
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
}
