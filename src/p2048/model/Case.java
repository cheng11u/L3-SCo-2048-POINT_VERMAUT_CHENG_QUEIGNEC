package p2048.model;

import java.io.Serializable;
import javafx.beans.property.*;
import javafx.beans.value.ChangeListener;

/**
 * @author Nicolas QUEIGNEC
 */
public class Case implements Serializable {
    private final IntegerProperty valeur;
    private final IntegerProperty x;
    private final IntegerProperty y;
    private final IntegerProperty z;
    private ObjectProperty<CubeGrille> grille;
    
    public Case(int x, int y, int z, CubeGrille grille) {
        this.x = new SimpleIntegerProperty(x);
        this.y = new SimpleIntegerProperty(y);
        this.z = new SimpleIntegerProperty(z);
        this.valeur = new SimpleIntegerProperty(1);
        this.grille = new SimpleObjectProperty<>(grille);
    }

    public int getValeur() {
        return valeur.get();
    }
    
    public void setValeur(int valeur) {
        this.valeur.set(valeur);
    }

    public int getX() {
        return x.get();
    }
    
    private void setX(int x) {
        this.x.set(x);
    }
    
    public int getY() {
        return y.get();
    }
    
    private void setY(int y) {
        this.y.set(y);
    }
    
    public int getZ() {
        return z.get();
    }
    
    private void setZ(int z) {
        this.z.set(z);
    }

    public CubeGrille getGrille() {
        return grille.get();
    }
    
    private void setGrille(CubeGrille grille) {
        this.grille.set(grille);
    }
    
    public void addListener(ChangeListener listener) {
        valeur.addListener(listener);
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
                    for (Case c : grille.get().getCases())
                      if (c.getY()==getY()-1 && c.getZ()==getZ() && c.getX()==getX()) res=c;
                break;
            case CubeGrille.DIR_BAS:
                if (getY()!=grille.get().getTaille()-1)
                    for (Case c : grille.get().getCases())
                        if (c.getY()==getY()+1 && c.getZ()==getZ() && c.getX()==getX()) res=c;
                break;
            case CubeGrille.DIR_GAUCHE:
                if (getX()!=0)
                    for (Case c : grille.get().getCases())
                        if (c.getX()==getX()-1 && c.getZ()==getZ() && c.getY()==getY()) res=c;
                break;
            case CubeGrille.DIR_DROITE:
                if (getX()!=grille.get().getTaille()-1)
                    for (Case c : grille.get().getCases())
                        if (c.getX()==getX()+1 && c.getZ()==getZ() && c.getY()==getY()) res=c;
                break;
            case CubeGrille.DIR_DESSOUS:
                if (getZ()!=0)
                    for (Case c : grille.get().getCases())
                        if (c.getZ()==getZ()-1 && c.getY()==getY() && c.getX()==getX()) res=c;
                break;
            case CubeGrille.DIR_DESSUS:
                if (getZ()!=grille.get().getTaille()-1)
                    for (Case c : grille.get().getCases())
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
            int val = this.valeur.get();
            int nbChiffres = ("" + val).length();
            for (int i=0; i<nbChiffres; i++){
                res += " ";
            }
            res += val;
            return res;
        }
    }
}
