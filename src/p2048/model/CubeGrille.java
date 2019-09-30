package p2048.model;

import java.time.Year;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

/**
 * @author Nicolas QUEIGNEC
 */
public class CubeGrille {
    private int valeurMax;
    private int score;
    private int nbdeplacements;
    private int direction;
    private int taille;
    public static final int DIR_HAUT=0;
    public static final int DIR_BAS=1;
    public static final int DIR_GAUCHE=2;
    public static final int DIR_DROITE=3;
    public static final int DIR_DESSOUS=4;
    public static final int DIR_DESSUS=5;
    private List<Case> cases;

    public CubeGrille(int taille) {
        this.valeurMax=0;
        this.score=0;
        this.nbdeplacements=0;
        this.cases=new ArrayList<Case>();
        this.taille=taille;
        for (int x=1; x<=this.taille; x++)
            for (int y=1; y<=this.taille; y++)
                for (int z=1; z<=this.taille; z++)
                    this.cases.add(new Case(x, y, z, this));
    }

    public List<Case> getCases() {
        return cases;
    }

    public int getTaille() {
        return taille;
    }
    
    public void ajouterAleatoireCase() {
        Random r=new Random();
        int indexCase=r.nextInt(cases.size());
        while (!cases.get(indexCase).estLibre())
            indexCase=r.nextInt(cases.size());
        cases.get(indexCase).setValeur(r.nextDouble()<0.66?2:4);
    }
    
    public Case[] getCasesExtremites(int direction) {
        Case[] res=new Case[taille*taille];
        int i=0;
        Iterator<Case> it=cases.iterator();
        while (it.hasNext() && i<res.length) {
            Case c=it.next();
            switch (direction) {
                case DIR_HAUT:
                    if (c.getY()==1) {
                        res[i]=c;
                        i++;
                    }
                    break;
                case DIR_BAS:
                    if (c.getY()==taille) {
                        res[i]=c;
                        i++;
                    }
                    break;
                case DIR_GAUCHE:
                    if (c.getX()==1) {
                        res[i]=c;
                        i++;
                    }
                    break;
                case DIR_DROITE:
                    if (c.getX()==taille) {
                        res[i]=c;
                        i++;
                    }
                    break;
                case DIR_DESSOUS:
                    if (c.getZ()==1) {
                        res[i]=c;
                        i++;
                    }
                    break;
                case DIR_DESSUS:
                    if (c.getZ()==taille) {
                        res[i]=c;
                        i++;
                    }
                    break;
                default:
                    break;
            }
        }
        return res;
    }
}
