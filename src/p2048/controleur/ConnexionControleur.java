/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package p2048.controleur;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import p2048.Parametres;
import p2048.model.reseau.Cryptage;
import p2048.model.reseau.Reseau;
import serveur.Protocole;

/**
 * Cette classe est responsable des éléments permettant de se connecter et de s'inscrire
 * @author Luc Cheng
 */
public class ConnexionControleur implements Controleur {
    
    /**
     * Label contenant l'éventuel message d'erreur
     */
    @FXML
    private Label labelErreur;
    
    /**
     * Champ correpondant au pseudo pour la connexion
     */
    @FXML
    private TextField pseudoConnexion;
    
    /**
     * Champ correspondant au mot de passe pour la connexion
     */
    @FXML
    private PasswordField entrermdp;
    
    /**
     * Zone de texte permettant d'entrer son pseudo pour l'inscription
     */
    @FXML
    private TextField pseudo;
    
    /**
     * Zone de texte permettant d'entrer son mot de passe à l'inscription
     */
    @FXML
    private PasswordField mdp;
    
    /**
     * Zone de texte permettant de confirmet son mot de passe
     */
    @FXML
    private PasswordField confirmation;
    
    
    
    /**
     * Cette méthode permet de vérifier les information entrées et effectue le traitement.
     * Elle vérifie que le mot de passe entré est identique à la confirmation
     * et envoie une requête pour ajouter le compte dans la base de données
     * @param e événement lié au bouton
     */
    @FXML
    public void verifierInfos(Event e){
        Parametres params = Parametres.getInstance();
        String contenuPseudo = pseudo.getText();
        String motDePasse = mdp.getText();
        boolean conditionsOk = motDePasse.length() >= 8 && !contenuPseudo.contains(Protocole.SEPARATEUR_PARAM)
                && !contenuPseudo.contains(Protocole.SEPARATEUR_VALEUR_MULTIPLE)
                && !contenuPseudo.contains(Protocole.SEPARATEUR_VALEUR_MULTIPLE);
        if (motDePasse.equals(confirmation.getText()) && !contenuPseudo.equals("") && conditionsOk){
            boolean ajoutReussi = Reseau.getInstance().ajouterJoueur(contenuPseudo, Cryptage.crypter(motDePasse));
            System.out.println(ajoutReussi);
            if (ajoutReussi){
                params.setPseudo(contenuPseudo);
                params.setMessage("L'inscription a bien été effectuée");
            }
            else {
                params.setMessage("Erreur lors de l'inscription");
            }
            
        }
        labelErreur.setText(params.getMessage());
    }
    
    @FXML
    public void connecter(){
        Parametres params = Parametres.getInstance();
        String contenuPseudo = pseudoConnexion.getText();
        String contenuMdp = Cryptage.crypter(entrermdp.getText());
        boolean res = Reseau.getInstance().connecter(contenuPseudo, contenuMdp);
        if (res){
            System.out.println(res);
            params.setPseudo(contenuPseudo);
            p2048.P2048.changerScene("vue/FXMLAccueil.fxml");
        }
        else {
            params.setMessage("Erreur lors de la connexion");
        }
        labelErreur.setText(params.getMessage());
    }
}
