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
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import p2048.controleur.SoloControleur;
import p2048.model.CubeGrille;
import p2048.model.Solo;

public class P2048 extends Application {
    private static Stage stage;
    
    public static void changerScene(String fxml) {
        URL fxmlURL=P2048.class.getResource(fxml);
        FXMLLoader loader=new FXMLLoader(fxmlURL);
        try {
            Parent root = loader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);
        } catch (Exception e) {}
        
    }
    
    @Override
    public void start(Stage stage) throws Exception {
        this.stage=stage;
        changerScene("vue/FXMLSolo.fxml");
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
     * @param args the command line arguments
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
                }
                cg.deplacer(cg.getDirection());
            }
        }
    }
    
}
