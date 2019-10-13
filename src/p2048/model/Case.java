package p2048.model;

import java.io.Serializable;
import javafx.beans.property.*;

/**
 * @author Nicolas QUEIGNEC
 */
public class Case implements Serializable {
    private IntegerProperty valeur;
    private IntegerProperty x;
    private IntegerProperty y;
    private IntegerProperty z;
    private ObjectProperty<CubeGrille> grille;
    
    public Case(int x, int y, int z, CubeGrille grille) {
        this.x = new SimpleIntegerProperty(x);
        this.y = new SimpleIntegerProperty(y);
        this.z = new SimpleIntegerProperty(z);
        this.valeur = new SimpleIntegerProperty(1);
        this.grille = new SimpleObjectProperty<>(grille);
    }

    public void setValeur(IntegerProperty valeur) {
        this.valeur = valeur;
    }

    public IntegerProperty getValeur() {
        return valeur;
    }

    public IntegerProperty getX() {
        return x;
    }

    public IntegerProperty getY() {
        return y;
    }

    public IntegerProperty getZ() {
        return z;
    }
    
    @Override
    public int hashCode() {
        return 2048*2048*this.x.get() + 2048*this.y.get() + this.z.get();
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
        if (this.x != other.x) {
            return false;
        }
        if (this.y != other.y) {
            return false;
        }
        if (this.z != other.z) {
            return false;
        }
        return true;
    }
    
    public boolean valeurEgale(Case c) {
        return valeur==c.valeur;
    }
    
    public Case getVoisin(int direction) {
        Case res=null;
        switch (direction) {
            case CubeGrille.DIR_HAUT:
                if (this.y.get()!=0)
                    for (Case c : grille.get().getCases())
                      if (c.y.get()==y.get()-1 && c.z.get()==z.get() && c.x.get()==x.get()) res=c;
                break;
            case CubeGrille.DIR_BAS:
                if (y.get()!=grille.get().getTaille()-1)
                    for (Case c : grille.get().getCases())
                        if (c.y.get()==y.get()+1 && c.z.get()==z.get() && c.x.get()==x.get()) res=c;
                break;
            case CubeGrille.DIR_GAUCHE:
                if (x.get()!=0)
                    for (Case c : grille.get().getCases())
                        if (c.x.get()==x.get()-1 && c.z.get()==z.get() && c.y.get()==y.get()) res=c;
                break;
            case CubeGrille.DIR_DROITE:
                if (x.get()!=grille.get().getTaille()-1)
                    for (Case c : grille.get().getCases())
                        if (c.x.get()==x.get()+1 && c.z.get()==z.get() && c.y.get()==y.get()) res=c;
                break;
            case CubeGrille.DIR_DESSOUS:
                if (z.get()!=0)
                    for (Case c : grille.get().getCases())
                        if (c.z.get()==z.get()-1 && c.y.get()==y.get() && c.x.get()==x.get()) res=c;
                break;
            case CubeGrille.DIR_DESSUS:
                if (z.get()!=grille.get().getTaille()-1)
                    for (Case c : grille.get().getCases())
                        if (c.z.get()==z.get()+1 && c.y==y && c.x==x) res=c;
                break;
            default:
                break;
        }
        return res;
    }
    
    public boolean estLibre() {
        return valeur.get()==1;
    }
}
