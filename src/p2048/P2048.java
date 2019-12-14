/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package p2048;

import java.net.URL;
import java.util.Scanner;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import p2048.controleur.Controleur;
import p2048.model.CubeGrille;

/**
 * Classe principale permettant de démarrer l'application et gérer la scène à afficher. 
 * @author Nicolas QUEIGNEC, Luc Cheng
 */
public class P2048 extends Application {
    /**
     * Stage de l'application JavaFX
     */
    private static Stage stage;
    
    /**
     * Change la scène a afficher.
     * @param fxml
     *  Chemin du fichier FXML à afficher.
     * @return 
     *  Controleur lié au FXML.
     */
    public static Controleur changerScene(String fxml) {
        URL fxmlURL=P2048.class.getResource(fxml);
        FXMLLoader loader=new FXMLLoader(fxmlURL);
        try {
            Parent root = loader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);
        } catch (Exception e) {}
        return loader.getController();
    }
    
    /**
     * Démarre l'application JavaFX et affiche la page de connexion.
     * @param stage
     *  Stage de l'application JavaFX
     */
    @Override
    public void start(Stage stage) {
        this.stage=stage;
        changerScene("vue/FXMLConnexion.fxml");
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                Platform.exit();
                System.exit(0);
            }
        });
        stage.show();
    }

    /**
     * Méthode main exécutée au lancement de l'application. Si l'argument -c est entré en premier, démarre une partie de 2048-3D en mode console sinon démarre l'application complète avec interface graphique.
     * @param args 
     *  Arguments en ligne de commande. -c pour démarrer en mode console.
     */
    public static void main(String[] args) {
        if (args.length == 0 || !args[0].equals("-c"))
            launch(args);
        else {
            Scanner sc = new Scanner(System.in);
            CubeGrille cg = new CubeGrille(3);
            while (!cg.partieTerminee()){
                cg.ajouterAleatoireCase();
                System.out.println(cg);
                String commande = sc.nextLine();
                switch (commande){
                    case "q":
                        cg.setDirection(CubeGrille.DIR_GAUCHE);
                        break;
                    case "d":
                        cg.setDirection(CubeGrille.DIR_DROITE);
                        break;
                    case "s":
                        cg.setDirection(CubeGrille.DIR_BAS);
                        break;
                    case "z":
                        cg.setDirection(CubeGrille.DIR_HAUT);
                        break;
                    case "f":
                        cg.setDirection(CubeGrille.DIR_DESSOUS);
                        break;
                    case "r":
                        cg.setDirection(CubeGrille.DIR_DESSUS);
                        break;
                    case "x":
                        System.exit(0);
                        break;
                }
                cg.deplacer(cg.getDirection());
            }
        }
    }
    
}
