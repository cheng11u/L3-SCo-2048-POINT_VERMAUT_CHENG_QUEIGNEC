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
     * Thread correspondant au jeu
     */
    private Thread jeu;
    
    /**
     * Constructeur
     * @param grille grille de jeu
     */
    public Solo(CubeGrille grille){
        this.grille = grille;
        this.jeu = new Thread(this.grille);
    }
    
    /**
     * Démarre une partie
     */
    public void commencerPartie(){
        this.jeu.start();
    }
    
    public void quitterPartie(){
        this.grille.arreter();
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
            oos.flush();
        }
        catch (IOException e){
            e.printStackTrace();
        }
        finally {
            try {
                if (oos != null){
                    oos.flush();
                    oos.close();
                }
            }
            catch (IOException e){
                e.printStackTrace();
            }
        }
    }
}
