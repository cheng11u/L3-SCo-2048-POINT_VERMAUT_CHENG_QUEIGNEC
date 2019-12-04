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
    /**
     * L'état dont va s'occuper l'IA
     */
    private CubeGrille etat;
    
    /**
     * Liste des état sauvegardés et des actions qui leur sont associés.
     */
    private ArrayList<EtatAction> etats = new ArrayList<>();
        
    /**
     * Multiplicateur attribué au nombre de points gagnés
     */
    private final int COEF_POINTS = 1;
    
    /**
     * Multiplicateur attribué au nombre de combinaisons possibles
     */
    private final int COEF_COMBI = 50;
    
    /**
     * Multiplicateur attribué au nombre de cases vides
     */
    private final int COEF_VIDE = 50;
    
    public IA(CubeGrille e){
        this.etat = e;
    }
    
    /**
     * Déplacement vers le haut.
     * @param e Un CubeGrille
     * @return retourne le nouveau CubeGrille après déplacement
     */
    private CubeGrille haut(CubeGrille e){
        CubeGrille ef = e ;
        ef.setDirection(CubeGrille.DIR_HAUT);
        ef.deplacer(ef.getDirection());
        return(ef);
    }
    
    /**
     * Déplacement vers le bas.
     * @param e Un CubeGrille
     * @return retourne le nouveau CubeGrille après déplacement
     */
    private CubeGrille bas(CubeGrille e){
        CubeGrille ef = e ;
        ef.setDirection(CubeGrille.DIR_BAS);
        ef.deplacer(ef.getDirection());
        return(ef);
    }
    
    /**
     * Déplacement vers la gauche.
     * @param e Un CubeGrille
     * @return retourne le nouveau CubeGrille après déplacement
     */
    private CubeGrille gauche(CubeGrille e){
        CubeGrille ef = e ;
        ef.setDirection(CubeGrille.DIR_GAUCHE);
        ef.deplacer(ef.getDirection());
        return(ef);
    }
    
    /**
     * Déplacement vers la droite.
     * @param e Un CubeGrille
     * @return retourne le nouveau CubeGrille après déplacement
     */
    private CubeGrille droite(CubeGrille e){
        CubeGrille ef = e ;
        ef.setDirection(CubeGrille.DIR_DROITE);
        ef.deplacer(ef.getDirection());
        return(ef);
    }
    
    /**
     * Déplacement vers le dessous.
     * @param e Un CubeGrille
     * @return retourne le nouveau CubeGrille après déplacement
     */
    private CubeGrille dessous(CubeGrille e){
        CubeGrille ef = e ;
        ef.setDirection(CubeGrille.DIR_DESSOUS);
        ef.deplacer(ef.getDirection());
        return(ef);
    }
    
    /**
     * Déplacement vers le dessus.
     * @param e Un CubeGrille
     * @return retourne le nouveau CubeGrille après déplacement
     */
    private CubeGrille dessus(CubeGrille e){
        CubeGrille ef = e ;
        ef.setDirection(CubeGrille.DIR_DESSUS);
        ef.deplacer(ef.getDirection());
        return(ef);
    }
    
    /**
     * Modifie la liste des états atteignables à partir d'un état donné et d'une action.
     * @param e l'état dont il est question
     * @return 
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
     * Donne le nombre de combinaisons possibles pour un état donné.
     * @param e un CubeGrille
     * @return retourne un entier naturel.
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
     * Donne le nombre de combinaisons possibles pour une case donnée.
     * @param c la Case à vérifier
     * @param e le CubeGrille dont il est question 
     * @return retourne le nombre de combinaisons obtenu
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
     * Permet de dire si la valeur la plus haute de la partie a au moins atteint 2048.
     * @param e un CubeGrille
     * @return retourne oui si ce but est atteint, non sinon.
     */
    private boolean but(CubeGrille e){
        if(e.getValeurMax()>=2048)
            return(true);
        else
            return(false);
    }
    
    /**
     * Retourne une valeur relatant la proximité avec le but fixé après une action. 
     * Plus elle est élevée, plus l'action effectuée est intéressante.
     * @param ei le CubeGrille de départ.
     * @param ef le CubeGrille après une action.
     * @return retourne la valeur de proximité à l'objectif. 
     */
    private int objectif(CubeGrille ei, CubeGrille ef){
        int valeur = COEF_POINTS * (ef.getScore() - ei.getScore());
        valeur += COEF_COMBI * this.possibilites(ef);
        valeur += COEF_VIDE * (ef.getTaille()-ef.getCases().size());
        return(valeur);
    }
    
    /**
     * Prend en paramètre un état et retourne un entier correspondant à la 
     * meilleure action à effectuer.
     * @param e le CubeGrille
     * @return re
     */
    public int action(){
        boolean but = false;
        int x = 0;
        EtatAction choix = new EtatAction(this.etat,1);
        this.deplacements(this.etat);
        int i = 0;
        while(i<this.etats.size() && !but){
            but = this.but(this.etats.get(i).getEtat());
            if(but){
                choix = this.etats.get(i);
            } else {
                x = this.objectif(this.etat, this.etats.get(i).getEtat());
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
}