/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package p2048;

import java.util.ArrayList;
import p2048.model.Case;
import p2048.model.CubeGrille;

/**
 *
 * @author cleve_000
 */
public class IA {
    private ArrayList<EtatAction> etats = new ArrayList<>();
    private final int COEF_POINTS = 1;
    private final int COEF_COMBI = 50;
    private final int COEF_VIDE = 50;
    /**
     * Les fonctions qui suivent servent à effectuer un déplaement sur un état.
     */
    /**
     * Déplacement vers le haut.
     */
    private CubeGrille haut(CubeGrille e){
        CubeGrille ef = e ;
        ef.setDirection(CubeGrille.DIR_HAUT);
        return(ef);
    }
    
    /**
     * Déplacement vers le bas.
     */
    private CubeGrille bas(CubeGrille e){
        CubeGrille ef = e ;
        ef.setDirection(CubeGrille.DIR_BAS);
        return(ef);
    }
    
    /**
     * Déplacement vers la gauche.
     */
    private CubeGrille gauche(CubeGrille e){
        CubeGrille ef = e ;
        ef.setDirection(CubeGrille.DIR_GAUCHE);
        return(ef);
    }
    
    /**
     * Déplacement vers la droite.
     */
    private CubeGrille droite(CubeGrille e){
        CubeGrille ef = e ;
        ef.setDirection(CubeGrille.DIR_DROITE);
        return(ef);
    }
    
    /**
     * Déplacement vers le dessous.
     */
    private CubeGrille dessous(CubeGrille e){
        CubeGrille ef = e ;
        ef.setDirection(CubeGrille.DIR_DESSOUS);
        return(ef);
    }
    
    /**
     * Déplacement vers le dessus.
     */
    private CubeGrille dessus(CubeGrille e){
        CubeGrille ef = e ;
        ef.setDirection(CubeGrille.DIR_DESSUS);
        return(ef);
    }
    
    /**
     * Retourne la liste des états possibles après différentes actions sur un état donné.
     */
    private void deplacements(CubeGrille e){
        CubeGrille en = this.haut(e);
        if(!en.equals(e))
            etats.add(new EtatAction(en,1));
        en = this.bas(e);
        if(!en.equals(e))
            etats.add(new EtatAction(en,-1));
        en = this.gauche(e);
        if(!en.equals(e))
            etats.add(new EtatAction(en,2));
        en = this.droite(e);
        if(!en.equals(e))
            etats.add(new EtatAction(en,-2));
        en = this.dessus(e);
        if(!en.equals(e))
            etats.add(new EtatAction(en,-3));
        en = this.dessous(e);
        if(!en.equals(e))
            etats.add(new EtatAction(en,3));
    }
    
    /**
     * Retourne une valeur dépendant du nombre de combinaisons possibles.
     */
    private int possibilites(CubeGrille e){
        Case c;
        int nb = 0;
        do {
            c = e.getCases().get(0);
            e.getCases().remove(0);
            nb += combinaisons(c,e);
        } while (!e.getCases().isEmpty());
        return(nb);
    }
    
    /**
     * Retourne le nombre de combinaisons possibles pour une case donnée.
     */
    private int combinaisons(Case c, CubeGrille e){
        int nb = 0, i = 0, j = 0;
        boolean ok = false;
        Case[] tc = (Case[])e.getCases().toArray();
        String[] cas = {"haut","bas","gauche","droite","dessus","dessous"};
        for(String s : cas){
            switch(s){
                case "haut":
                    i = c.getY();
                    while (i>0 && !ok) {
                        i--;
                        for (j = 0; j < tc.length; j++) {
                            if(tc[j].getX()==c.getX() && tc[j].getY()==i && tc[j].getZ()==c.getZ()){
                                ok = true;
                                if(tc[j].getValeur()==c.getValeur()){
                                    nb++;
                                }
                            }
                        }
                    };
                    ok = false;
                    break;
                case "bas":
                    i = c.getY();
                    while (i<e.getTaille() && !ok) {
                        i++;
                        for (j = 0; j < tc.length; j++) {
                            if(tc[j].getX()==c.getX() && tc[j].getY()==i && tc[j].getZ()==c.getZ()){
                                ok = true;
                                if(tc[j].getValeur()==c.getValeur()){
                                    nb++;
                                }
                            }
                        }
                    }
                    ok = false;
                    break;
                case "gauche":
                    i = c.getX();
                    while (i>0 && !ok) {
                        i--;
                        for (j = 0; j < tc.length; j++) {
                            if(tc[j].getX()==i && tc[j].getY()==c.getY() && tc[j].getZ()==c.getZ()){
                                ok = true;
                                if(tc[j].getValeur()==c.getValeur()){
                                    nb++;
                                }
                            }
                        }
                    };
                    ok = false;
                    break;
                case "droite":
                    i = c.getX();
                    while (i<e.getTaille() && !ok) {
                        i++;
                        for (j = 0; j < tc.length; j++) {
                            if(tc[j].getX()==i && tc[j].getY()==c.getY() && tc[j].getZ()==c.getZ()){
                                ok = true;
                                if(tc[j].getValeur()==c.getValeur()){
                                    nb++;
                                }
                            }
                        }
                    }
                    ok = false;
                    break;
                case "dessus":
                    i = c.getZ();
                    while (i<e.getTaille() && !ok) {
                        i++;
                        for (j = 0; j < tc.length; j++) {
                            if(tc[j].getX()==c.getX() && tc[j].getY()==c.getY() && tc[j].getZ()==i){
                                ok = true;
                                if(tc[j].getValeur()==c.getValeur()){
                                    nb++;
                                }
                            }
                        }
                    }
                    ok = false;
                    break;
                case "dessous":
                    i = c.getZ();
                    while (i<e.getTaille() && !ok) {
                        i--;
                        for (j = 0; j < tc.length; j++) {
                            if(tc[j].getX()==c.getX() && tc[j].getY()==c.getY() && tc[j].getZ()==i){
                                ok = true;
                                if(tc[j].getValeur()==c.getValeur()){
                                    nb++;
                                }
                            }
                        }
                    }
                    ok = false;
                    break;
            }
        }
        return(nb);
    }
    
    /**
     * permet de dire si le but fixé (une case avec 2048) est atteint ou non.
     */
    private boolean but(CubeGrille e){
        if(e.getValeurMax()>=2048)
            return(true);
        else
            return(false);
    }
    
    /**
     * Retourne une valeur relatant la proximité avec le but fixé.
     */
    private int objectif(CubeGrille ei, CubeGrille ef){
        int valeur = COEF_POINTS * (ef.getScore() - ei.getScore());
        valeur += COEF_COMBI * this.possibilites(ef);
        valeur += COEF_VIDE * (ef.getTaille()-ef.getCases().size());
        return(valeur);
    }
    
    public int action(CubeGrille e){
        boolean but = false;
        int x = 0;
        EtatAction choix = new EtatAction(e,1);
        this.deplacements(e);
        int i = 0;
        while(i<this.etats.size() && !but){
            but = this.but(this.etats.get(i).getEtat());
            if(but){
                choix = this.etats.get(i);
            } else {
                x = this.objectif(e, this.etats.get(i).getEtat());
                this.etats.get(i).setValeur(this.etats.get(i).getEtat(), x);
            }
            i++;
        }
        if(but){
            this.etats = new ArrayList<>();
            return(choix.getAction());
        } else {
            while(!this.etats.isEmpty()){
                if(this.etats.get(0).getValeur()>choix.getValeur()){
                    choix = this.etats.get(0);
                }
                this.etats.remove(0);
            }
            return(choix.getAction());
        }
    }
        
    public ArrayList resolution(CubeGrille e){
        boolean but = false;
        int act = 0;
        ArrayList<Integer> suiteActions = new ArrayList<>();
        while(!but && !e.getStop()){
            act = this.action(e);
            suiteActions.add(suiteActions.size(), act);
            /*ici il faudra faire en sorte que l'action soit effectuée afin de 
            pouvoir voir si le but a été atteint.*/
            but = this.but(e);
        }
        return(suiteActions);
    }
}