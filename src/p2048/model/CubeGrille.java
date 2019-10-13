package p2048.model;

import java.io.Serializable;
import java.time.Year;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

/**
 * @author Nicolas QUEIGNEC
 */
public class CubeGrille implements Runnable, Serializable {
    private int valeurMax;
    private int score;
    private int nbdeplacements;
    private int direction;
    private int taille;
    private boolean stop;
    public static final int DIR_HAUT=1;
    public static final int DIR_BAS=-1;
    public static final int DIR_GAUCHE=2;
    public static final int DIR_DROITE=-2;
    public static final int DIR_DESSOUS=3;
    public static final int DIR_DESSUS=-3;
    private List<Case> cases;

    public CubeGrille(int taille) {
        this.valeurMax=0;
        this.score=0;
        this.nbdeplacements=0;
        this.cases=new ArrayList<Case>();
        this.stop=false;
        this.taille=taille;
        for (int x=0; x<this.taille; x++)
            for (int y=0; y<this.taille; y++)
                for (int z=0; z<this.taille; z++)
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
        System.out.println(cases.get(indexCase).getX()+","+cases.get(indexCase).getY()+","+cases.get(indexCase).getZ()+"="+cases.get(indexCase).getValeur());
    }
    
    public Case[] getCasesExtremites(int direction) {
        Case[] res=new Case[taille*taille];
        int i=0;
        Iterator<Case> it=cases.iterator();
        while (it.hasNext() && i<res.length) {
            Case c=it.next();
            switch (direction) {
                case DIR_HAUT:
                    if (c.getY()==0) {
                        res[i]=c;
                        i++;
                    }
                    break;
                case DIR_BAS:
                    if (c.getY()==taille-1) {
                        res[i]=c;
                        i++;
                    }
                    break;
                case DIR_GAUCHE:
                    if (c.getX()==0) {
                        res[i]=c;
                        i++;
                    }
                    break;
                case DIR_DROITE:
                    if (c.getX()==taille-1) {
                        res[i]=c;
                        i++;
                    }
                    break;
                case DIR_DESSOUS:
                    if (c.getZ()==0) {
                        res[i]=c;
                        i++;
                    }
                    break;
                case DIR_DESSUS:
                    if (c.getZ()==taille-1) {
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
    
    private void deplacerRecursif(Case[] rangee, int direction, int compteur) {
        Case[] rangeeSuiv=new Case[rangee.length];
        if (rangee[0]!=null && rangee[0].getVoisin(-direction)!=null) {
            int i=0;
            for (Case c : rangee) {
                if (c.getValeur()==c.getVoisin(-direction).getValeur() || c.estLibre()) {
                    int nouvelleValeur=c.getValeur()*c.getVoisin(-direction).getValeur();
                    if (!c.estLibre() && !c.getVoisin(-direction).estLibre()) {
                        score+=nouvelleValeur;
                        if (valeurMax<nouvelleValeur)
                            valeurMax=nouvelleValeur;
                    }
                    c.setValeur(nouvelleValeur);
                    c.getVoisin(-direction).setValeur(1);
                }
                rangeeSuiv[i]=c.getVoisin(-direction);
                i++;
            }
            deplacerRecursif(rangeeSuiv, direction, compteur);
        } else if (compteur<taille-1) {
            deplacerRecursif(rangeeSuiv, direction, compteur+1);
        }
    }
    
    public void deplacer(int direction) {
        deplacerRecursif(getCasesExtremites(direction), direction, 0);
        nbdeplacements++;
    }
    
    public boolean partieTerminee() {
        boolean termine=true;
        List<Case> casesVerifiees=new ArrayList<Case>();
        List<Integer> directions=new ArrayList<Integer>();
        directions.add(DIR_HAUT);
        directions.add(DIR_HAUT);
        directions.add(DIR_GAUCHE);
        directions.add(DIR_DROITE);
        directions.add(DIR_DESSUS);
        directions.add(DIR_DESSOUS);
        Iterator<Case> it=cases.iterator();
        while (it.hasNext() && termine) {
            Case c=it.next();
            if (c.estLibre())
                termine=false;
            else {
                for (int direction : directions) {
                    Case voisin=c.getVoisin(direction);
                    if (voisin!=null && voisin.valeurEgale(c))
                        termine=false;
                }
            }
        }
        return termine;
    }
    
    public int[][] getGrilleEtage(int etage){
        if (etage>=0 && etage<taille) {
            int[][] res=new int[taille][taille];
            for (Case c : cases) 
                if (c.getZ()==etage)
                    res[c.getX()][c.getY()]=c.getValeur();
            return res;
        }
        return null;        
    }

    public synchronized void setDirection(int direction) {
        this.direction = direction;
        this.notify();
    }
    
    public synchronized void arreter() {
        this.stop=true;
        this.notify();
    }
    
    @Override
    public void run() {
        while (!partieTerminee() && !stop) {     
            ajouterAleatoireCase();
            try {
                synchronized (this) {
                    this.wait();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (!stop)
                deplacer(direction);
        }
    }
}
