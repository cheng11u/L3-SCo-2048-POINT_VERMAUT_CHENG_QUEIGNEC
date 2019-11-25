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
    private String action;
    private int valeur;
    private final String[] ACTIONS_POSSIBLES = {"DIR_HAUT","DIR_BAS","DIR_GAUCHE","DIR_DROITE","DIR_DESSUS","DIR_DESSOUS"};
    
    public EtatAction(CubeGrille e, String a){
        this.etat = e;
        this.valeur = 0;
        if(Arrays.toString(this.ACTIONS_POSSIBLES).contains(a)){
            this.action = a;
        } else {
            //si la commande n'est pas connue, on lui donne une valeur par défaut
            this.action = this.ACTIONS_POSSIBLES[0]; 
        }
    }
    
    public CubeGrille getEtat(){
        return(this.etat);
    }
    
    public String getAction(){
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
