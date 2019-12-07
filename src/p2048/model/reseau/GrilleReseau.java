/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package p2048.model.reseau;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;
import p2048.model.Case;
import p2048.model.CubeGrille;
import serveur.Protocole;

/**
 *
 * @author Nicolas QUEIGNEC
 */
public class GrilleReseau extends CubeGrille {
    public GrilleReseau(int taille) {
        super(3);
    }
    
    @Override
    public synchronized void ajouterAleatoireCase() {
        Reseau.getInstance().envoyerMessage(Protocole.REQ_CREER_CASE);
        try {
            this.wait();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }
    
    public synchronized void creerCase(int index, int val){
        System.out.println("p2048.model.reseau.GrilleReseau.creerCase()");
        List<Case> cases=getCases();
        if (cases.get(index).estLibre())
            cases.get(index).setValeur(val);
        else {
            int i=index==0?26:index-1;
            boolean fait=false;
            while (i!=index+1 && !fait) {
                if (cases.get(i).estLibre()) {
                   cases.get(i).setValeur(val);
                   fait=true;
                }
                i=i==0?26:i-1;
            }
        }
        this.notify();
    }
}