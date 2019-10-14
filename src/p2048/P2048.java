/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package p2048;

import java.net.URL;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import p2048.controleur.SoloControleur;
import p2048.model.Solo;

public class P2048 extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        URL fxmlURL=getClass().getResource("vue/FXMLSolo.fxml");
        FXMLLoader loader=new FXMLLoader(fxmlURL);
        Parent root = loader.load();
        Solo partie=new Solo();
        SoloControleur controleur=loader.getController();
        controleur.ajouterGrille(partie.getGrille());
        Scene scene = new Scene(root);
        partie.getGrille().ajouterListenerCases(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() { 
                        controleur.update();
                    }
                });
              
            }
        });
        partie.commencerPartie();
        stage.setScene(scene);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
