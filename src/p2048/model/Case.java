package p2048.model;

/**
 * @author Nicolas QUEIGNEC
 */
public class Case {
    private int valeur;
    private int x;
    private int y;
    private int z;
    private CubeGrille grille;
    
    public Case(int x, int y, int z, CubeGrille grille) {
        this.x=x;
        this.y=y;
        this.z=z;
        this.valeur=1;
        this.grille=grille;
    }

    public void setValeur(int valeur) {
        this.valeur = valeur;
    }

    public int getValeur() {
        return valeur;
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
    
    @Override
    public int hashCode() {
        return x*9+y*11+z*17;
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
                if (y!=0)
                    for (Case c : grille.getCases())
                      if (c.y==y-1) res=c;
                break;
            case CubeGrille.DIR_BAS:
                if (y!=grille.getTaille()-1)
                    for (Case c : grille.getCases())
                        if (c.y==y+1) res=c;
                break;
            case CubeGrille.DIR_GAUCHE:
                if (x!=0)
                    for (Case c : grille.getCases())
                        if (c.x==x-1) res=c;
                break;
            case CubeGrille.DIR_DROITE:
                if (x!=grille.getTaille()-1)
                    for (Case c : grille.getCases())
                        if (c.x==x+1) res=c;
                break;
            case CubeGrille.DIR_DESSOUS:
                if (z!=0)
                    for (Case c : grille.getCases())
                        if (c.z==z-1) res=c;
                break;
            case CubeGrille.DIR_DESSUS:
                if (z!=grille.getTaille()-1)
                    for (Case c : grille.getCases())
                        if (c.z==z+1) res=c;
                break;
            default:
                break;
        }
        return res;
    }
    
    public boolean estLibre() {
        return valeur==1;
    }
}
