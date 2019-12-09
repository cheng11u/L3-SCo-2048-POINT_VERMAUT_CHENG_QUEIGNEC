/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package p2048.controleur;

import java.util.List;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import p2048.P2048;
import p2048.model.reseau.Cooperation;
import p2048.model.reseau.InfosPartie;
import p2048.model.reseau.Reseau;

/**
 * Cette classe est responsable de l'interface permettant de rejoindre une partie
 * @author Nicolas QUEIGNEC
 */
public class ReseauRejoindreControleur implements Controleur {
    @FXML
    private Label id1;
    @FXML
    private Label id2;
    @FXML
    private Label id3;
    /**
     * Label contenant le nom du joueur de la première partie
     */
    @FXML
    private Label j1;
    /**
     * Label contenant le nom du joueur de la deuxième partie
     */
    @FXML
    private Label j2;
    /**
     * Label contenant le nom du joueur de la troisième partie
     */
    @FXML
    private Label j3;
    /**
     * Label contenant le nombre de joueurs de la première partie
     */
    @FXML
    private Label nbJoueurs1;
    /**
     * Label contenant le nombre de joueurs de la deuxième partie
     */
    @FXML
    private Label nbJoueurs2;
    /**
     * Label contenant le nombre de joueurs de la troisième partie
     */
    @FXML
    private Label nbJoueurs3;
    /**
     * Label contenant le numéro de page
     */
    @FXML
    private Label pageLabel;
    /**
     * Label contenant le nombre total de pages
     */
    @FXML
    private Label nbPagesLabel;
    /**
     * Elément contenant les informations à propos de la première partie
     */
    @FXML
    private AnchorPane partie1;
    /**
     * Elément contenant les informations à propos de la deuxième partie
     */
    @FXML
    private AnchorPane partie2;
    /**
     * Elément contenant les informations à propos de la troisième partie
     */
    @FXML
    private AnchorPane partie3;
    /**
     * Nombre de pages total
     */
    private int nbPages;
    /**
     * Page actuelle
     */
    private int page;
    /**
     * Liste des parties
     */
    private List<InfosPartie> parties;
    
    /**
     * Cette méthode est exécutée à chaque clic sur une partie
     * @param e événement
     */
    @FXML
    public void onClick(Event e) {
        int indexPartie=0;
        if (e.getSource()==partie1)
            indexPartie=(page-1)*3;
        else if (e.getSource()==partie2)
           indexPartie=(page-1)*3+1;
        else if (e.getSource()==partie3)
           indexPartie=(page-1)*3+2;   
        if (e.getSource()==partie1 || e.getSource()==partie2 || e.getSource()==partie3) {
            int id=parties.get(indexPartie).getId();
            String nom=parties.get(indexPartie).getNomJoueur();
            Cooperation partie=Reseau.getInstance().rejoindrePartieCoop(id, nom);
            ((AttentePartieControleur)P2048.changerScene("vue/FXMLAttentePartie.fxml")).rejoindrePartieCoop(partie);
        }
    }
    
    /**
     * Affiche la page suivante
     */
    @FXML
    public void pageSuiv() {
        if (this.page>1) {
            this.page--;
            update();
        }
    }
    
    /**
     * Affiche la page précédente
     */
    @FXML
    public void pagePrec() {
        if (this.page<this.nbPages) {
            this.page++;
            update();
        }
    }
    
    /**
     * Initialise l'affichage
     * @param parties liste des parties
     */
    private void init(List<InfosPartie> parties) {
        this.page=1;
        this.parties=parties;
        this.nbPages=1+(this.parties.size()-1)/3;
        if (this.parties.size()%3>0)
            this.nbPages++;
        this.nbPagesLabel.setText(this.nbPages+"");
        this.update();
    }
    
    /**
     * Met à jour l'affichage
     */
    public void update() {
        this.pageLabel.setText(this.page+"");
        if (this.parties.size()>0) {
            this.partie1.setVisible(true);
            this.id1.setText(this.parties.get((this.page-1)*3).getId()+"");
            this.j1.setText(this.parties.get((this.page-1)*3).getNomJoueur());
            this.nbJoueurs1.setText(1+"");
        }   else
            this.partie1.setVisible(false);
        if (this.parties.size()%3==2) {
            this.partie2.setVisible(true);
            this.id2.setText(this.parties.get((this.page-1)*3+1).getId()+"");
            this.j2.setText(this.parties.get((this.page-1)*3+1).getNomJoueur());
            this.nbJoueurs2.setText(1+"");
        }  else
            this.partie2.setVisible(false);
        if (this.parties.size()%3==0 && this.parties.size()>0) {
            this.partie3.setVisible(true);
            this.id3.setText(this.parties.get((this.page-1)*3+2).getId()+"");
            this.j3.setText(this.parties.get((this.page-1)*3+2).getNomJoueur());
            this.nbJoueurs3.setText(1+"");
        }  else
            this.partie3.setVisible(false);
    }
    
    /**
     * Gère l'initialisation
     */
    public void initCoop() {
        init(Reseau.getInstance().afficherPartiesCoop());
    }
    
    /**
     * Redirige vers l'accueil
     */
    @FXML
    public void retourAccueil(){
        P2048.changerScene("vue/FXMLAccueil.fxml");
    }
}
