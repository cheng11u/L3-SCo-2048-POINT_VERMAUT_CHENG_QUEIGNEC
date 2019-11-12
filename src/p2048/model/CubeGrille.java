package p2048.model;

import java.io.Serializable;
import java.time.Year;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;

/**
 * @author Nicolas QUEIGNEC
 */
public class CubeGrille implements Runnable, Serializable {
    private int valeurMax;
    private int score;
    private int nbDeplacements;
    private int direction;
    private final int taille;
    private boolean stop;
    private transient SimpleBooleanProperty stopProperty;
    public static final int DIR_HAUT=1;
    public static final int DIR_BAS=-1;
    public static final int DIR_GAUCHE=2;
    public static final int DIR_DROITE=-2;
    public static final int DIR_DESSOUS=3;
    public static final int DIR_DESSUS=-3;
    private final List<Case> cases;
    private transient SimpleListProperty<Case> casesProperty;

    public CubeGrille(int taille) {
        this.valeurMax=0;
        this.score=0;
        this.nbDeplacements=0;
        this.stop=false;
        this.stopProperty=new SimpleBooleanProperty(this.stop);
        this.taille=taille;
        this.direction=0;
        this.cases=new ArrayList<Case>();
        for (int x=0; x<this.taille; x++)
            for (int y=0; y<this.taille; y++)
                for (int z=0; z<this.taille; z++)
                    cases.add(new Case(x, y, z, this));
        this.casesProperty=new SimpleListProperty<Case>(FXCollections.observableList(this.cases));
    }
    
    public void initProperties() {
        if (this.stopProperty==null)
            this.stopProperty=new SimpleBooleanProperty(this.stop);
        if (this.casesProperty==null)
            this.casesProperty=new SimpleListProperty<Case>(FXCollections.observableList(this.cases));
        for (Case c : cases)
            c.initProperty();
    }

    public List<Case> getCases() {
        return cases;
    }

    public int getTaille() {
        return taille;
    }

    public int getScore() {
        return score;
    }

    public int getDirection() {
        return direction;
    }

    public int getNbDeplacements() {
        return nbDeplacements;
    }

    public boolean getStop() {
        return stop;
    }

    public int getValeurMax() {
        return valeurMax;
    }
    
    private void setScore(int score) {
        this.score=score;
    }
    
    public synchronized void setDirection(int direction) {
        this.direction=direction;
        this.notify();
    }
    
    private void setNbDeplacements(int nbDeplacements) {
        this.nbDeplacements=nbDeplacements;
    }
    
    private void setStop(boolean stop) {
        this.stop=stop;
        this.stopProperty.set(this.stop);
    }
    
    private void setValeurMax(int valeurMax) {
        this.valeurMax=valeurMax;
    }
    
    public void ajouterListener(ChangeListener listener) {
        for (Case c : cases) {
            c.addListener(listener);
        }
        stopProperty.addListener(listener);
    }
    
    public synchronized void arreter() {
        setStop(true);
        this.notify();
    }
    
    public void ajouterAleatoireCase() {
        Random r=new Random();
        List<Case> cases = this.getCases();
        int indexCase=r.nextInt(cases.size());
        while (!cases.get(indexCase).estLibre())
            indexCase=r.nextInt(cases.size());
        cases.get(indexCase).setValeur(r.nextDouble()<0.66?2:4);
    }
    
    public Case[] getCasesExtremites(int direction) {
        Case[] res=new Case[getTaille()*getTaille()];
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
                    if (c.getY()==getTaille()-1) {
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
                    if (c.getX()==getTaille()-1) {
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
                    if (c.getZ()==getTaille()-1) {
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
    
    private boolean deplacerRecursif(int direction, Case actuelle, int i, int nbPassage, int nbFusion) {
        Case[] rangee=getCasesExtremites(direction);
        boolean aDeplace=false;
        if (actuelle==null && i==0)
            actuelle=rangee[i];
        Case suivante=null;
        if (actuelle!=null)
            suivante=actuelle.getVoisin(-direction);
        if (i<rangee.length) {
            if (suivante==null) {
                if (nbPassage==getTaille()-1) {
                    i++;
                    if (i<rangee.length) {
                        suivante=rangee[i];
                        nbPassage=0; 
                        nbFusion=0;
                    }
                } else {
                    nbPassage++;
                    suivante=rangee[i];
                }
            }
            else {
               if (actuelle.estLibre()) {
                    actuelle.setValeur(suivante.getValeur());
                    suivante.setValeur(1);
                    if (!actuelle.estLibre())
                        aDeplace=true;
                } else if (actuelle.getValeur()==suivante.getValeur() && nbFusion<(int)(getTaille()/2)) {
                    actuelle.setValeur(actuelle.getValeur()*2);
                    suivante.setValeur(1);
                    setScore(getScore()+actuelle.getValeur());
                    nbFusion++;
                    aDeplace=true;
                }
            }
            return deplacerRecursif(direction, suivante, i, nbPassage, nbFusion)||aDeplace;
        } 
        return aDeplace;
    }
    
    public boolean deplacer(int direction) {
        if (deplacerRecursif(direction, null, 0, 0, 0)) {
           setNbDeplacements(getNbDeplacements()+1);
           return true;
        }
        return false;
    }
    
    public boolean partieTerminee() {
        boolean termine=true;
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
        if (etage>=0 && etage<getTaille()) {
            int[][] res=new int[getTaille()][getTaille()];
            for (Case c : cases) 
                if (c.getZ()==etage)
                    res[c.getX()][c.getY()]=c.getValeur();
            return res;
        }
        return null;        
    }
    
    @Override
    public void run() {
        ajouterAleatoireCase();
        while (!partieTerminee() && !stop) {     
            try {
                synchronized (this) {
                    this.wait();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (!stop)
                if (deplacer(getDirection()))
                    ajouterAleatoireCase();
        }
    }
    
    public String toString(){
        String ligne = " ---- ---- ----  ---- ---- ----  ---- ---- ----\n";
        
        String res = ligne;
        for (int y=0; y<3; y++){
            for (int z=0; z<3; z++){
                res += "|";
                for (int x=0; x<3; x++){
                    int indice = 9*x + 3*y + z;
                    String val = Integer.toString(this.cases.get(indice).getValeur());
                    while (val.length() < 4)
                        val = " " + val;
                    if (val.equals("   1"))
                        val = "    ";
                    res += val + "|";
                }
            }
            res += "\n" + ligne;
        }
        res += "\n";
        return res;
    }
}
