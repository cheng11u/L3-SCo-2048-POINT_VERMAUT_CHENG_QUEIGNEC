package p2048;

import static java.lang.System.exit;
import java.util.Arrays;
import p2048.model.CubeGrille;

/**
 *
 * @author cleve_000
 */
public class EtatAction {
    private CubeGrille etat;
    private int action;
    private int valeur;
    
    public EtatAction(CubeGrille e, int a){
        this.etat = e;
        this.valeur = 0;
        if(a!=0 && a<=3 && a>=-3){
            this.action = a;
        } else {
            //si la commande n'est pas connue, on lui donne une valeur par défaut
            this.action = 1; 
        }
    }
    
    public CubeGrille getEtat(){
        return(this.etat);
    }
    
    public int getAction(){
        return(this.action);
    }
    
    public int getValeur(){
        return(this.valeur);
    }
    
    public void setValeur(CubeGrille e, int v){
        if(e.equals(this.etat)){
            this.valeur = v;
        }
    }
}
