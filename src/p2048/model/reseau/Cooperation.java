/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package p2048.model.reseau;

import p2048.model.CubeGrille;
import p2048.model.PartieMonoGrille;

/**
 * @author Nicolas QUEIGNEC
 */
public class Cooperation extends PartieReseau implements PartieMonoGrille {
    private GrilleReseau grille;
    
    public Cooperation(int id, boolean estJoueur1) {
        super(id, estJoueur1);
        this.grille=new GrilleReseau(3);
    }

    @Override
    public GrilleReseau getGrilleReseauRecu() {
        return this.grille;
    }

    @Override
    public GrilleReseau getGrilleReseauEnvoi() {
        return this.grille;
    }

    @Override
    public CubeGrille getGrille() {
        return grille;
    }
    
    @Override
    public void commencerPartie() {
        new Thread(this.grille).start();
    }

    @Override
    public void sauvegarderClassement() {}
    
}
