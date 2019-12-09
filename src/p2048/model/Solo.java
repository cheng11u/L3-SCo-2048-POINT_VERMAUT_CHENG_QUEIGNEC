package p2048.model;
import java.io.*;
import p2048.Parametres;
import p2048.model.reseau.Reseau;
/**
 * Cette classe permet de jouer une partie en solo.
 * @author Luc Cheng
 */
public class Solo implements PartieMonoGrille {
    
    /**
     * Grille correspondant à la partie solo.
     */
    private CubeGrille grille;
    
    /**
     * Thread correspondant au jeu.
     */
    private Thread jeu;
    
    /**
     * Nom du fichier de sauvegarde.
     */
    public static final String NOM_FICHIER = "jeu2048";
    
    /**
     * Constructeur
     */
    public Solo(){
        this.grille = new CubeGrille(3);
    }
    
    @Override
    public void commencerPartie(){
        this.jeu = new Thread(this.grille);
        this.jeu.start();
    }
    
    @Override
    public void quitter(){
        this.grille.arreter();
    }
    
    @Override
    public void sauvegarderClassement() {
        Reseau reseau = Reseau.getInstance();
        String pseudo = Parametres.getInstance().getPseudo();
        String typePartie = "Solo";
        int score = this.grille.getScore();
        reseau.ajouterPartie(pseudo, typePartie, score);
    }
    
    /**
     * Sauvegarde la partie en cours.
     */
    public void sauvegarder() {
        ObjectOutputStream oos = null;
        try {
            oos = new ObjectOutputStream(new FileOutputStream(NOM_FICHIER));
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
    
    /**
     * Charge la grille à partir d'un fichier.
     */
    public void charger(){
        ObjectInputStream ois = null;
        
        try {
            ois = new ObjectInputStream(new FileInputStream(NOM_FICHIER));
            Object obj = ois.readObject();
            if (obj instanceof CubeGrille){
                this.grille = (CubeGrille) obj;
                this.grille.initProperties();
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }
        catch (ClassNotFoundException e){
            e.printStackTrace();
        }
        finally {
            try {
                if (ois != null)
                    ois.close();
            }
            catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    @Override
    public CubeGrille getGrille() {
        return grille;
    }

    @Override
    public void jouer(int direction) {
        grille.setDirection(direction);
    }
}
