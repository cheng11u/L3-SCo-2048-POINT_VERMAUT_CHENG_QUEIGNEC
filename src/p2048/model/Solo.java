/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package p2048.model;
import java.io.*;
import javax.swing.JFileChooser;
/**
 *
 * @author Luc Cheng
 */
public class Solo implements TypePartie {
    
    /**
     * Grille correspondant à la partie solo
     */
    private CubeGrille grille;
    
    /**
     * Constructeur
     * @param grille grille de jeu
     */
    public Solo(CubeGrille grille){
        this.grille = grille;
    }
    
    /**
     * Démarre une partie
     */
    public void commencerPartie(){
        Thread t = new Thread(this.grille);
        t.start();
    }
    
    public void sauvegarderClassement() {
        
    }
    
    /**
     * Sauvegarde la partie en cours
     * @param fichier fichier dans lequel la partie est sauvegardée
     * @throws IOException en cas d'erreur de flux
     */
    public void sauvegarder(File fichier) {
        ObjectOutputStream oos = null;
        try {
            oos = new ObjectOutputStream(new FileOutputStream(fichier));
            oos.writeObject(this.grille);
        }
        catch (IOException e){
            e.printStackTrace();
        }
        finally {
            try {
                if (oos != null)
                    oos.close();
            }
            catch (IOException e){
                e.printStackTrace();
            }
        }
    }
}
