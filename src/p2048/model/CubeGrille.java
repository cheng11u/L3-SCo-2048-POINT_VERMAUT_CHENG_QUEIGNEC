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
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

/**
 * @author Nicolas QUEIGNEC
 */
public class CubeGrille implements Runnable, Serializable {
    private final SimpleIntegerProperty valeurMax;
    private final SimpleIntegerProperty score;
    private final SimpleIntegerProperty nbDeplacements;
    private final SimpleIntegerProperty direction;
    private final SimpleIntegerProperty taille;
    private final SimpleBooleanProperty stop;
    public static final int DIR_HAUT=1;
    public static final int DIR_BAS=-1;
    public static final int DIR_GAUCHE=2;
    public static final int DIR_DROITE=-2;
    public static final int DIR_DESSOUS=3;
    public static final int DIR_DESSUS=-3;
    private final SimpleListProperty<Case> cases;

    public CubeGrille(int taille) {
        this.valeurMax=new SimpleIntegerProperty(0);
        this.score=new SimpleIntegerProperty(0);
        this.nbDeplacements=new SimpleIntegerProperty(0);
        this.stop=new SimpleBooleanProperty(false);
        this.taille=new SimpleIntegerProperty(taille);
        this.direction=new SimpleIntegerProperty(0);
        List<Case> cases=new ArrayList<Case>();
        for (int x=0; x<this.taille.get(); x++)
            for (int y=0; y<this.taille.get(); y++)
                for (int z=0; z<this.taille.get(); z++)
                    cases.add(new Case(x, y, z, this));
        this.cases=new SimpleListProperty<Case>(FXCollections.observableArrayList(cases));
    }

    public List<Case> getCases() {
        return cases.get();
    }

    public int getTaille() {
        return taille.get();
    }

    public int getScore() {
        return score.get();
    }

    public int getDirection() {
        return direction.get();
    }

    public int getNbdeplacements() {
        return nbDeplacements.get();
    }

    public boolean getStop() {
        return stop.get();
    }

    public int getValeurMax() {
        return valeurMax.get();
    }
    
    private void setCases(List<Case> cases) {
        this.cases.set(FXCollections.observableArrayList(cases));
    }
    
    private void setTaille(int taille) {
        this.taille.set(taille);
    }
    
    private void setScore(int score) {
        this.score.set(score);
    }
    
    public synchronized void setDirection(int direction) {
        this.direction.set(direction);
        this.notify();
    }
    
    private void setNbDeplacements(int nbDeplacements) {
        this.nbDeplacements.set(nbDeplacements);
    }
    
    private void setStop(boolean stop) {
        this.stop.set(stop);
    }
    
    private void setValeurMax(int valeurMax) {
        this.valeurMax.set(valeurMax);
    }
    
    public void ajouterListenerCases(ChangeListener listener) {
        for (Case c : cases) {
            c.addListener(listener);
        }
    }
    
    public synchronized void arreter() {
        setStop(true);
        this.notify();
    }
    
    public void ajouterAleatoireCase() {
        Random r=new Random();
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
           setNbDeplacements(getNbdeplacements()+1);
           return true;
        }
        return false;
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
        while (!partieTerminee() && !stop.get()) {     
            try {
                synchronized (this) {
                    this.wait();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (!stop.get())
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
