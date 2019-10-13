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
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import p2048.model.CubeGrille;

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
    private CubeGrille grille;
    
    public void ajouterGrille(CubeGrille grille){
        this.grille=grille;
    }
    
    @FXML
    public void buttonClicked(Event e) {
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
    }
    
    public void update() {
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
                        label.setFont(new Font(20));
                        System.out.println(j+","+i+"="+grilleEtage[i][j]);
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
