/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package p2048.controleur;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import p2048.P2048;
import static p2048.P2048.changerScene;
import p2048.model.reseau.InfosPartie;
import p2048.model.reseau.Reseau;

/**
 *
 * @author Nicolas QUEIGNEC
 */
public class ReseauRejoindreControleur implements Controleur {
    @FXML
    private Label id1;
    @FXML
    private Label id2;
    @FXML
    private Label id3;
    @FXML
    private Label j1;
    @FXML
    private Label j2;
    @FXML
    private Label j3;
    @FXML
    private Label nbJoueurs1;
    @FXML
    private Label nbJoueurs2;
    @FXML
    private Label nbJoueurs3;
    @FXML
    private Label pageLabel;
    @FXML
    private Label nbPagesLabel;
    @FXML
    private AnchorPane partie1;
    @FXML
    private AnchorPane partie2;
    @FXML
    private AnchorPane partie3;
    private int nbPages;
    private int page;
    private List<InfosPartie> parties;
    
    @FXML
    public void onClick(ActionEvent e) {
        
    }
    
    @FXML
    public void pageSuiv() {
        if (this.page>1) {
            this.page--;
            update();
        }
    }
    
    @FXML
    public void pagePrec() {
        if (this.page<this.nbPages) {
            this.page++;
            update();
        }
    }

    private void init(List<InfosPartie> parties) {
        this.page=1;
        this.parties=parties;
        this.nbPages=this.parties.size()/3;
        if (this.parties.size()%3>0)
            this.nbPages++;
        this.nbPagesLabel.setText(this.nbPages+"");
        this.update();
    }
    
    public void update() {
        this.pageLabel.setText(this.page+"");
        this.id1.setText(this.parties.get((this.page-1)*3).getId()+"");
        this.j1.setText(this.parties.get((this.page-1)*3).getNomJoueur());
        this.nbJoueurs1.setText(1+"");
        if (this.parties.size()%3==2) {
            this.partie2.setVisible(true);
            this.id2.setText(this.parties.get((this.page-1)*3+1).getId()+"");
            this.j2.setText(this.parties.get((this.page-1)*3+1).getNomJoueur());
            this.nbJoueurs2.setText(1+"");
        }  else
            this.partie2.setVisible(false);
        if (this.parties.size()%3==0) {
            this.partie3.setVisible(true);
            this.id3.setText(this.parties.get((this.page-1)*3+2).getId()+"");
            this.j3.setText(this.parties.get((this.page-1)*3+2).getNomJoueur());
            this.nbJoueurs3.setText(1+"");
        }  else
            this.partie3.setVisible(false);
    }
    
    public void initCoop() {
        init(Reseau.getInstance().afficherPartiesCoop());
    }
    
    @FXML
    public void retourAccueil(){
        P2048.changerScene("vue/FXMLAccueil.fxml");
    }
}
