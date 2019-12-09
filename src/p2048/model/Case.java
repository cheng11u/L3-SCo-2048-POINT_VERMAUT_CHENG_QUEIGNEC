package p2048.model;

import java.io.Serializable;
import javafx.beans.property.*;
import javafx.beans.value.ChangeListener;

/**
 * Représente une case de la grille du 2048-3D.
 * @author Nicolas QUEIGNEC
 */
public class Case implements Serializable {
    /**
     * Valeur actuelle de la case. 1 représente une case vide.
     */
    private int valeur;
    /**
     * Property égale à la valeur pour pouvoir mettre à jour l'interface graphique lorsque la valeur change.
     */
    private transient SimpleIntegerProperty valeurProperty;
    /**
     * Coordonnée x.
     */
    private final int x;
    /**
     * Coordonnée y.
     */
    private final int y;
    /** 
     * Coordonnée z.
     */
    private final int z;
    /**
     * Grille à laquelle appartient la case.
     */
    private final CubeGrille grille;
    
    /**
     * Constructeur.
     * @param x
     *  {@link #x}
     * @param y
     *  {@link #y}
     * @param z
     *  {@link #z}
     * @param grille
     *  {@link #grille} 
     */
    public Case(int x, int y, int z, CubeGrille grille) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.valeur=1;
        this.valeurProperty=new SimpleIntegerProperty(1);
        this.grille = grille;
    }
    
    /**
     * Initialise la Property. Utilie lors de la désérialisation.
     */
    public void initProperty() {
       this.valeurProperty=new SimpleIntegerProperty(this.valeur);
    }
    
    /**
     * Getter.
     * @return
     *  {@link #valeur} 
     */
    public int getValeur() {
        return valeur;
    }
    
    /**
     * Setter.
     * @param valeur 
     *  {@link #valeur}
     */
    public void setValeur(int valeur) {
        this.valeur=valeur;
        this.valeurProperty.set(this.valeur);
    }

    /**
     * Getter.
     * @return
     *  {@link #x}
     */
    public int getX() {
        return x;
    }
    
    /**
     * Getter.
     * @return
     *  {@link #y}
     */
    public int getY() {
        return y;
    }
 
    /**
     * Getter.
     * @return
     *  {@link #z}
     */
    public int getZ() {
        return z;
    }
    
    /**
     * Getter.
     * @return
     *  {@link #grille}
     */
    public CubeGrille getGrille() {
        return grille;
    }
    
    /**
     * Ajoute un listener pour pouvoir afficher la case en temps réel.
     * @param listener 
     *  Listener mettant à jour l'interface graphique.
     */
    public void addListener(ChangeListener listener) {
        valeurProperty.addListener(listener);
    }
    
    @Override
    public int hashCode() {
        return 2048*2048*this.getX() + 2048*this.getY() + this.getZ();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Case other = (Case) obj;
        if (this.getX() != other.getX()) {
            return false;
        }
        if (this.getY() != other.getY()) {
            return false;
        }
        if (this.getZ() != other.getZ()) {
            return false;
        }
        return true;
    }
    
    /**
     * Permet de savoir si 2 cases ont la même valeur.
     * @param c
     *  Autre case.
     * @return 
     *  <code>true</code> si les 2 cases ont la même valeur sinon <code>false</code>.
     */
    public boolean valeurEgale(Case c) {
        return getValeur()==c.getValeur();
    }
    
    /**
     * Donne la case d'à côté en allant dans la direction souhaitée.
     * @see CubeGrille#DIR_BAS
     * @see CubeGrille#DIR_HAUT 
     * @see CubeGrille#DIR_GAUCHE 
     * @see CubeGrille#DIR_DROITE 
     * @see CubeGrille#DIR_DESSOUS
     * @see CubeGrille#DIR_DESSUS 
     * @param direction
     *  Direction par rapport à la case.
     * @return 
     *  Case voisine.
     */
    public Case getVoisin(int direction) {
        Case res=null;
        switch (direction) {
            case CubeGrille.DIR_HAUT:
                if (this.getY()!=0)
                    for (Case c : grille.getCases())
                      if (c.getY()==getY()-1 && c.getZ()==getZ() && c.getX()==getX()) res=c;
                break;
            case CubeGrille.DIR_BAS:
                if (getY()!=grille.getTaille()-1)
                    for (Case c : grille.getCases())
                        if (c.getY()==getY()+1 && c.getZ()==getZ() && c.getX()==getX()) res=c;
                break;
            case CubeGrille.DIR_GAUCHE:
                if (getX()!=0)
                    for (Case c : grille.getCases())
                        if (c.getX()==getX()-1 && c.getZ()==getZ() && c.getY()==getY()) res=c;
                break;
            case CubeGrille.DIR_DROITE:
                if (getX()!=grille.getTaille()-1)
                    for (Case c : grille.getCases())
                        if (c.getX()==getX()+1 && c.getZ()==getZ() && c.getY()==getY()) res=c;
                break;
            case CubeGrille.DIR_DESSOUS:
                if (getZ()!=0)
                    for (Case c : grille.getCases())
                        if (c.getZ()==getZ()-1 && c.getY()==getY() && c.getX()==getX()) res=c;
                break;
            case CubeGrille.DIR_DESSUS:
                if (getZ()!=grille.getTaille()-1)
                    for (Case c : grille.getCases())
                        if (c.getZ()==getZ()+1 && c.getY()==getY() && c.getX()==getX()) res=c;
                break;
            default:
                break;
        }
        return res;
    }
    
    /**
     * Permet de savoir si la case est libre+
     * @return 
     *  <code>true</code> si la casea est libre sinon <code>false</code>.
     */
    public boolean estLibre() {
        return getValeur()==1;
    }
    
    public String toString(){
        if (this.estLibre())
            return "    ";
        else {
            String res = "";
            int val = this.valeur;
            int nbChiffres = ("" + val).length();
            for (int i=0; i<nbChiffres; i++){
                res += " ";
            }
            res += val;
            return res;
        }
    }
}
