package p2048.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;

/**
 * Grille à 3 dimensions. 
 * @author Nicolas QUEIGNEC
 */
public class CubeGrille implements Runnable, Serializable, Cloneable {
    /**
     * Valeur maximale des cases.
     */
    private int valeurMax;
    /**
     * Score du joueur.
     */
    private int score;
    /**
     * Nombre de déplacements utilisés par le joueur.
     */
    private int nbDeplacements;
    /**
     * Direction dans laquelle on doit déplacer les cases.
     * @see #DIR_BAS
     * @see #DIR_HAUT 
     * @see #DIR_GAUCHE 
     * @see #DIR_DROITE 
     * @see #DIR_DESSOUS
     * @see #DIR_DESSUS 
     */
    private int direction;
    /**
     * Taille de la grille.
     */
    private final int taille;
    /**
     * Indique si le joueur a quitté la partie.
     */
    private boolean stop;
    /**
     * Property égale à stop pour pouvoir mettre à jour l'interface graphique lorsque la valeur change.
     */
    private transient SimpleBooleanProperty stopProperty;
    /**
     * Indique que les cases douvent être déplacées vers le haut.
     */
    public static final int DIR_HAUT=1;
    /**
     * Indique que les cases douvent être déplacées vers le bas.
     */
    public static final int DIR_BAS=-1;
    /**
     * Indique que les cases douvent être déplacées vers la gauche.
     */
    public static final int DIR_GAUCHE=2;
    /**
     * Indique que les cases douvent être déplacées vers la droite.
     */
    public static final int DIR_DROITE=-2;
    /**
     * Indique que les cases douvent être déplacées vers l'étage inférieur.
     */
    public static final int DIR_DESSOUS=3;
    /**
     * Indique que les cases douvent être déplacées vers l'étage supérieur.
     */
    public static final int DIR_DESSUS=-3;
    /**
     * Liste des cases de la grille.
     */
    private final List<Case> cases;
    /**
     * Property égale à cases pour pouvoir mettre à jour l'interface graphique lorsque la valeur change.
     */
    private transient SimpleListProperty<Case> casesProperty;

    /**
     * Constructeur
     * @param taille
     *  {@link #taille}
     */
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

    @Override
    public CubeGrille clone() {
        CubeGrille res=new CubeGrille(this.taille);
        res.score=this.score;
        res.nbDeplacements=this.nbDeplacements;
        res.valeurMax=this.valeurMax;
        res.stop=this.stop;
        res.stopProperty.set(res.stop);
        res.direction=this.direction;
        for (int i=0; i<res.cases.size(); i++)
            res.cases.get(i).setValeur(this.cases.get(i).getValeur());
        return res;
    }
    
    
    /**
     * Initialise les propriétés, utile pour la désérialisation.
     */
    public void initProperties() {
        if (this.stopProperty==null)
            this.stopProperty=new SimpleBooleanProperty(this.stop);
        if (this.casesProperty==null)
            this.casesProperty=new SimpleListProperty<Case>(FXCollections.observableList(this.cases));
        for (Case c : cases)
            c.initProperty();
    }
    
    /**
     * Getter.
     * @return 
     *  {@link #cases}
     */
    public List<Case> getCases() {
        return cases;
    }
    
    /**
     * Getter.
     * @return 
     *  {@link #taille}
     */
    public int getTaille() {
        return taille;
    }
    
    /**
     * Getter.
     * @return 
     *  {@link #score} 
     */
    public int getScore() {
        return score;
    }
    
    /**
     * Getter.
     * @return 
     *  {@link #direction}
     */
    public int getDirection() {
        return direction;
    }

    /**
     * Getter.
     * @return 
     *  {@link #nbDeplacements}
     */
    public int getNbDeplacements() {
        return nbDeplacements;
    }
    
    /**
     * Getter.
     * @return 
     *  {@link #stop}
     */
    public boolean getStop() {
        return stop;
    }
    
    /**
     * Getter.
     * @return 
     *  {@link #valeurMax}
     */
    public int getValeurMax() {
        return valeurMax;
    }
    
    /**
     * Setter.
     * @param score 
     *  {@link #score}
     */
    private void setScore(int score) {
        this.score=score;
    }
    
    /**
     * Setter. Permet d'avancer dans la partie.
     * @param direction 
     *  {@link #direction}
     */
    public synchronized void setDirection(int direction) {
        this.direction=direction;
        this.notify();
    }
    
    /**
     * Setter.
     * @param nbDeplacements 
     *  {@link #nbDeplacements}
     */
    private void setNbDeplacements(int nbDeplacements) {
        this.nbDeplacements=nbDeplacements;
    }
    
    /**
     * Setter.
     * @param stop 
     *  {@link #stop}
     */
    private void setStop(boolean stop) {
        this.stop=stop;
        this.stopProperty.set(this.stop);
    }
    
    /**
     * Setter.
     * @param valeurMax 
     *  {@link #valeurMax}
     */
    private void setValeurMax(int valeurMax) {
        this.valeurMax=valeurMax;
    }
    
    /**
     * Ajoute un listener pour pouvoir afficher la case en temps réel.
     * @param listener 
     *  Listener mettant à jour l'interface graphique.
     */
    public void ajouterListener(ChangeListener listener) {
        for (Case c : cases) {
            c.addListener(listener);
        }
        stopProperty.addListener(listener);
    }
    
    /**
     * Change l'état de la partie pour indiquer qu'elle est terminée.
     */
    public synchronized void arreter() {
        setStop(true);
        this.notify();
    }
    
    /**
     * Ajoute une case à un emplacement libre choisi aléatoirement et lui attribue
     * une valeur de 2 (avec une probabilité de 0.66) ou de 4 (avec une probabilité
     * de 0.34).
     */
    public void ajouterAleatoireCase() {
        Random r=new Random();
        List<Case> cases = this.getCases();
        int indexCase=r.nextInt(cases.size());
        while (!cases.get(indexCase).estLibre())
            indexCase=r.nextInt(cases.size());
        cases.get(indexCase).setValeur(r.nextDouble()<0.66?2:4);
    }
    
    /**
     * Renvoi toute les cases à la surface du côté choisi.
     * @param direction
     *  {@link #direction} - Côté du cube.
     * @return 
     *  Cases à la surface.
     */
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
    
    /**
     * Permet de déplacer les cases de manière récursive.
     * @param direction
     *  {@link #direction} - Direction voulu.
     * @param actuelle
     *  Case en cours de traitement
     * @param i
     *  Indice de la case dans sa rangée du tableau {@link #getCasesExtremites(int)}.
     * @param nbPassage
     *  Nombre de traitement de la case actuelle.
     * @param nbFusion
     *  Nombre de fusion de case ayant eu lieu sur la rangée.
     * @return 
     * <code>true</code> si le déplacement a été possible et effectué sinon <code>false</code>.
     */
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
                    if (actuelle.getValeur()>getValeurMax())
                        setValeurMax(actuelle.getValeur());
                    nbFusion++;
                    aDeplace=true;
                }
            }
            return deplacerRecursif(direction, suivante, i, nbPassage, nbFusion)||aDeplace;
        } 
        return aDeplace;
    }
    
    /**
     * Permet de faire un déplacement dans la direction choisi.
     * @param direction
     *  {@link #direction} - Direction voulu.
     * @return 
     *  <code>true</code> si le déplacement a été possible et effectué sinon <code>false</code>.
     */
    public boolean deplacer(int direction) {
        if (deplacerRecursif(direction, null, 0, 0, 0)) {
           setNbDeplacements(getNbDeplacements()+1);
           return true;
        }
        return false;
    }
    
    /**
     * Indique si la partie est terminée.
     * @return 
     *  <code>true</code> si la grille est terminée sinon <code>false</code>.
     */
    public boolean partieTerminee() {
        if (getValeurMax()==2048)
            return true;
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
    
    /**
     * Donne la grille à deux dimensions correspondant à l'étage passé en paramètres.
     * @param etage 
     *  Numéro de l'étage.
     * @return 
     *  Valeurs des cases appartenant à l'étage.
     */
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
        if (getNbDeplacements()==0) 
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
