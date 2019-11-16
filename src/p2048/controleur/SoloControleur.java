/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package p2048.controleur;

import com.sun.javafx.css.Stylesheet;
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
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
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
 * Classe permettant de gérer les éléments d'une partie en solo
 * @author Nicolas QUEIGNEC
 */
public class SoloControleur implements Controleur, Initializable {
    /**
     * Bouton permettant de déplacer les cases vers le haut
     */
    @FXML
    private Button haut;
    
    /**
     * Bouton permettant de déplacer les cases vers le bas
     */
    @FXML
    private Button bas;
    
    /**
     * Bouton permettant de déplacer les cases vers la gauche
     */
    @FXML
    private Button gauche;
    
    /**
     * Bouton permettant de déplacer les cases vers la droite
     */
    @FXML
    private Button droite;
    
    /**
     * Bouton permettant de déplacer les cases vers l'étage inférieur
     */
    @FXML
    private Button inf;
    
    /**
     * Bouton permettant de déplacer les cases vers l'étage supérieur
     */
    @FXML
    private Button sup;
    
    /**
     * Grille située à gauche représentant l'étage inférieur
     */
    @FXML
    private GridPane etage0;
    
    /**
     * Grille du milieu représentant l'étage intermédiaire
     */
    @FXML
    private GridPane etage1;
    
    /**
     * Grille située à droite représentant l'étage supérieur
     */
    @FXML
    private GridPane etage2;
    
    /**
     * Bouton permettant de quitter l'application
     */
    @FXML
    private Button quitter;
    
    /**
     * Bouton permettant d'enregistrer la partie en cours
     */
    @FXML
    private Button enregistrer;
    
    /**
     * Label indiquant le score du joueur
     */
    @FXML
    private Label points;
    
    /**
     * AnchorPane regroupant les trois grilles
     */
    @FXML
    private AnchorPane fenetre;
    
    @FXML
    private MenuItem fondNormal;
    
    @FXML
    private MenuItem fondSombre;
    
    /**
     * Liste des cases
     */
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
    
    @FXML
    public void changerFond(Event e){
        if (e.getSource() == fondNormal){
            if (!fenetre.getStylesheets().isEmpty())
                fenetre.getStylesheets().remove(0, fenetre.getStylesheets().size());
        }
        else {
            if (fenetre.getStylesheets().isEmpty()){
                fenetre.getStylesheets().add("p2048/CSS/dark_theme.css");
            }
        }
        System.out.println("changerFond");
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
        solo.getGrille().ajouterListener(listener);
        solo.commencerPartie();
    }
    
    public void chargerPartie() {
        solo.charger();
        nouvellePartie();
    }
}
