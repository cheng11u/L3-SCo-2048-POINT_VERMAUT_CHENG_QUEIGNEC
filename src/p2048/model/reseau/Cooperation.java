package p2048.model.reseau;

import p2048.model.CubeGrille;
import p2048.model.PartieMonoGrille;

/**
 * Partie de 2048-3D en coopération en réseau.
 * @author Nicolas QUEIGNEC
 */
public class Cooperation extends PartieReseau implements PartieMonoGrille {
    /**
     * Grille de la partie.
     */
    private GrilleReseau grille;
    
    /**
     * Constructeur.
     * @param id
     *  {@link PartieReseau#id}
     * @param estJoueur1 
     *  {@link PartieReseau#estJoueur1}
     */
    public Cooperation(int id, boolean estJoueur1) {
        super(id, estJoueur1);
        this.grille=new GrilleReseau(3);
        this.getReceveur().start();
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
