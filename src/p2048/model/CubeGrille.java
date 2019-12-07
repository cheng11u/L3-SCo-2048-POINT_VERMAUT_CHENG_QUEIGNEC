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
    /**
     * Valeur maximale des cases
     */
    private int valeurMax;
    /**
     * Score du joueur
     */
    private int score;
    /**
     * Nombre de déplacements utilisés par le joueur
     */
    private int nbDeplacements;
    /**
     * Direction du déplacement des cases
     */
    private int direction;
    /**
     * Taille des grilles
     */
    private final int taille;
    /**
     * Indique si la partie est terminée
     */
    private boolean stop;
    private transient SimpleBooleanProperty stopProperty;
    
    /**
     * Indique que les cases douvent être déplacées vers le haut
     */
    public static final int DIR_HAUT=1;
    /**
     * Indique que les cases douvent être déplacées vers le bas
     */
    public static final int DIR_BAS=-1;
    /**
     * Indique que les cases douvent être déplacées vers la gauchet
     */
    public static final int DIR_GAUCHE=2;
    /**
     * Indique que les cases douvent être déplacées vers la droite
     */
    public static final int DIR_DROITE=-2;
    /**
     * Indique que les cases douvent être déplacées vers l'étage inférieur
     */
    public static final int DIR_DESSOUS=3;
    /**
     * Indique que les cases douvent être déplacées vers l'étage supérieur
     */
    public static final int DIR_DESSUS=-3;
    /**
     * Liste des cases de la grille
     */
    private final List<Case> cases;
    private transient SimpleListProperty<Case> casesProperty;

    /**
     * Constructeur
     * @param taille dimension de chaque grille 
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
    
    /**
     * Initialise les propriétés
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
     * Donne les cases de la grille
     * @return cases
     */
    public List<Case> getCases() {
        return cases;
    }
    
    /**
     * Retourne la taille de la grille
     * @return taille
     */
    public int getTaille() {
        return taille;
    }
    
    /**
     * Retourne le score du joueur
     * @return score
     */
    public int getScore() {
        return score;
    }
    
    /**
     * Retourne la direction, sous la forme d'un entier, dans laquelle les
     * cases vont se déplacer
     * @return direction
     */
    public int getDirection() {
        return direction;
    }

    /**
     * Retourne le nombre de déplacements exécutés par le joueur
     * @return nombre de déplacements
     */
    public int getNbDeplacements() {
        return nbDeplacements;
    }
    
    
    public boolean getStop() {
        return stop;
    }
    
    /**
     * Retourne la valeur de la case ayant le plus grand nombre
     * @return valeur maximale
     */
    public int getValeurMax() {
        return valeurMax;
    }
    
    /**
     * Modifie le score du joueur
     * @param score nouveau score
     */
    private void setScore(int score) {
        this.score=score;
    }
    
    /**
     * Modifie la direction des cases
     * @param direction nouvelle direction
     */
    public synchronized void setDirection(int direction) {
        this.direction=direction;
        this.notify();
    }
    
    /**
     * Modifie le nombre de déplacements
     * @param nbDeplacements nouveau nombre de déplacement
     */
    private void setNbDeplacements(int nbDeplacements) {
        this.nbDeplacements=nbDeplacements;
    }
    
    /**
     * Modifie l'attribut indiquant si la partie est terminée
     * @param stop nouvelle valeur
     */
    private void setStop(boolean stop) {
        this.stop=stop;
        this.stopProperty.set(this.stop);
    }
    
    /**
     * Modifie la valeur maximale
     * @param valeurMax nouvelle valeur
     */
    private void setValeurMax(int valeurMax) {
        this.valeurMax=valeurMax;
    }
    
    public void ajouterListener(ChangeListener listener) {
        for (Case c : cases) {
            c.addListener(listener);
        }
        stopProperty.addListener(listener);
    }
    
    /**
     * Change l'état de la partie pour indiquer qu'elle est terminée
     */
    public synchronized void arreter() {
        setStop(true);
        this.notify();
    }
    
    /**
     * Ajoute une case à un emplacement libre choisi aléatoirement et lui attribue
     * une valeur de 2 (avec une probabilité de 0.66) ou de 4 (avec une probabilité 
     * de 0.34)
     */
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
    
    
    public boolean deplacer(int direction) {
        if (deplacerRecursif(direction, null, 0, 0, 0)) {
           setNbDeplacements(getNbDeplacements()+1);
           return true;
        }
        return false;
    }
    
    /**
     * Indique si la partie est terminée
     * @return vrai si la partie est terminée, faux sinon
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
     * Donne la grille à deux dimensions correspondant à l'étage passé en paramètres
     * @param etage numéro de l'étage
     * @return valeurs des cases appartenant à l'étage
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
                System.out.println("p2048.model.CubeGrille.run() wait jouer1");
                synchronized (this) {
                    this.wait();
                }
                System.out.println("p2048.model.CubeGrille.run() wait jouer2");
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
