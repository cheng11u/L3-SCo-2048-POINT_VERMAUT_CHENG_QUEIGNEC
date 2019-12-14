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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import p2048.model.ia.IA;
import p2048.model.CubeGrille;
import p2048.P2048;
import p2048.Parametres;
import p2048.model.PartieMonoGrille;
import p2048.model.Solo;
import p2048.model.reseau.Cooperation;
import p2048.model.reseau.Reseau;
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
     * Bouton permettant d'effectuer un coup avec l'IA
     */
    @FXML
    private Button auto;

    /**
     * Bouton permettant de terminer la partie avec l'IA
     */
    @FXML
    private Button finir;
    
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
    
    /**
     * Label contenant le pseudo du joueur
     */
    @FXML
    private Label pseudoJoueur;
    
    /**
     * Bouton permettant de supprimer le fond sombre
     */
    @FXML
    private MenuItem fondNormal;
    
    /**
     * Bouton permettant d'ajouter un fond sombre
     */
    @FXML
    private MenuItem fondSombre;
    
    /**
     * Indique si la partie courante a été sauvegardée dans la base de données
     */
    private boolean partieSauvegardee = false;
    
    /**
     * Liste des cases
     */
    private List<Pane> panes=new ArrayList<Pane>();
    
    /**
     * Partie courante
     */
    private PartieMonoGrille partie;
    
    /**
     * Listener de la partie solo
     */
    private ChangeListener listener;
    /**
     * IA de la partie.
     */
    private IA ia;
    
    /**
     * Cette méthode met à jour l'état du jeu. Elle s'exécute lorsqu'un clic sur l'un des boutons
     * de l'interface graphique est effectué
     * @param e événement
     */
    @FXML
    public void buttonClicked(Event e) {
        if (e.getSource()==haut)
            partie.jouer(CubeGrille.DIR_HAUT);
        else if (e.getSource()==bas)
            partie.jouer(CubeGrille.DIR_BAS);
        else if (e.getSource()==gauche)
            partie.jouer(CubeGrille.DIR_GAUCHE);
        else if (e.getSource()==droite)
            partie.jouer(CubeGrille.DIR_DROITE);
        else if (e.getSource()==sup)
            partie.jouer(CubeGrille.DIR_DESSUS);
        else if (e.getSource()==inf)
            partie.jouer(CubeGrille.DIR_DESSOUS);
        else if (e.getSource()==auto){
            if (ia==null)
                ia = new IA(partie.getGrille());
            partie.jouer(ia.action());
        }
        else if (e.getSource()==finir){
            if (ia==null)
               ia=new IA(partie.getGrille());
            if (ia.isAutoActive()) {
                ia.stopAuto();
                finir.setText("Activer Auto");
            } else {
                new Thread(ia).start();
                finir.setText("Désctiiver Auto");
            }
        }
        else if (e.getSource()==quitter) {
            partie.quitter();
            if (ia!=null && ia.isAutoActive()) {
                ia.stopAuto();
            }
            P2048.changerScene("vue/FXMLAccueil.fxml");
        }
        else if (e.getSource()==enregistrer)
            ((Solo)partie).sauvegarder();
    }
    
    /**
     * Ajoute ou enlève l'image de fond de la partie
     * @param e événement
     */
    @FXML
    public void changerFond(Event e){
        Parametres params = Parametres.getInstance();
        if (e.getSource() == fondNormal){
            params.setChemin("");
        }
        else {
            params.setChemin("p2048/CSS/dark_theme.css");
        }
        update();
        this.chargerStyle();
    }
    
    /**
     * Cette méthode, qui met à jour l'état de la partie, est exécutée à chaque frappe de clavier.
     * @param e événement
     */
    @FXML
    public void keyPressed(Event e){
        CubeGrille grille = this.partie.getGrille();
        if (e instanceof KeyEvent){
            String lettre = ((KeyEvent)e).getCode().getName().toLowerCase();
            switch (lettre){
                case "z":
                    partie.jouer(CubeGrille.DIR_HAUT);
                    break;
                case "q":
                    partie.jouer(CubeGrille.DIR_GAUCHE);
                    break;
                case "s":
                    partie.jouer(CubeGrille.DIR_BAS);
                    break;
                case "d":
                    partie.jouer(CubeGrille.DIR_DROITE);
                    break;
                case "f":
                    partie.jouer(CubeGrille.DIR_DESSOUS);
                    break;
                case "r":
                    partie.jouer(CubeGrille.DIR_DESSUS);
                    break;
            }
        }
    }
    
    /**
     * Charge l'image à mettre en fond, si elle existe
     */
    public void chargerStyle(){
        fenetre.getStylesheets().clear();
        String chemin = Parametres.getInstance().getChemin();
        if (!chemin.equals("")){
            fenetre.getStylesheets().add(chemin);
        }
    }
    
    /**
     * Met à jour l'état de la grille
     */
    public void update() {
        etage0.getChildren().removeAll(panes);
        etage1.getChildren().removeAll(panes);
        etage2.getChildren().removeAll(panes);
        panes.clear();
        CubeGrille grille = partie.getGrille();
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
                        if (Parametres.getInstance().getChemin().equals("p2048/CSS/dark_theme.css")) {
                           int intensite=(int)(127-(127/11)*logBase2);
                            if (intensite<0)
                                intensite=0;
                            pane.setStyle("-fx-background-color:rgb("+intensite+"," +intensite+ ","+intensite+")");
                        } else {
                            int vert = (int)(255 - (255.0/11.0)*logBase2);
                            if (vert < 0)
                            vert = 0;
                        pane.setStyle("-fx-background-color:rgb(255," + vert + ",0)");
                         }
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
        if (grille.getStop() && !partieSauvegardee){
            String pseudo = Parametres.getInstance().getPseudo();
            Reseau.getInstance().ajouterPartie(pseudo, "Solo", grille.getScore());
            partieSauvegardee = true;
        }        
        
    }

    /**
     * @see <a href="https://docs.oracle.com/javase/8/javafx/api/javafx/fxml/Initializable.html">javafx.fxml.Initializable.initialize</a>
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        String pseudo = Parametres.getInstance().getPseudo(); 
        if (!pseudo.equals(""))
            this.pseudoJoueur.setText(pseudo);
        listener=new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() { 
                        chargerStyle();
                        update();
                    }
                });
            }
        };
    }
    
    /**
     * Crée une nouvelle partie solo
     */
    public void nouvellePartie() {
        this.partie=new Solo();
        partie.getGrille().ajouterListener(listener);
        partie.commencerPartie();
    }
    
    /**
     * Charge une partie solo
     */
    public void chargerPartie() {
        this.partie=new Solo();
        ((Solo)partie).charger();
        partie.getGrille().ajouterListener(listener);
        partie.commencerPartie();
        this.chargerStyle();
        update();
    }
    
    /**
     * Démarre la partie en coopération que 2 joueurs ont rejoint au préalable.
     * @param partie partie à démarrer
     */
    public void initCoop(Cooperation partie) {
        enregistrer.setVisible(false);
        auto.setVisible(false);
        finir.setVisible(false);
        this.partie=partie;
        partie.ajouterListener(listener);
        partie.commencerPartie();
        update();
    }
}
