package p2048.model;

import java.io.Serializable;
import javafx.beans.property.*;
import javafx.beans.value.ChangeListener;

/**
 * @author Nicolas QUEIGNEC
 */
public class Case implements Serializable {
    private int valeur;
    private transient SimpleIntegerProperty valeurProperty;
    private final int x;
    private final int y;
    private final int z;
    private final CubeGrille grille;
    
    public Case(int x, int y, int z, CubeGrille grille) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.valeur=1;
        this.valeurProperty=new SimpleIntegerProperty(1);
        this.grille = grille;
    }
    
    public void initProperty() {
       this.valeurProperty=new SimpleIntegerProperty(this.valeur);
    }
    
    public int getValeur() {
        return valeur;
    }
    
    public void setValeur(int valeur) {
        this.valeur=valeur;
        this.valeurProperty.set(this.valeur);
    }

    public int getX() {
        return x;
    }
    
    public int getY() {
        return y;
    }
 
    public int getZ() {
        return z;
    }
    
    public CubeGrille getGrille() {
        return grille;
    }
    
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
    
    public boolean valeurEgale(Case c) {
        return getValeur()==c.getValeur();
    }
    
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
