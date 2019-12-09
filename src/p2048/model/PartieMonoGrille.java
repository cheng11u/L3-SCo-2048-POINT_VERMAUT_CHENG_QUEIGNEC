package p2048.model;

/**
 * Partie de 2048-3D avec une seule grille à afficher.
 * @author Nicolas QUEIGNEC
 */
public interface PartieMonoGrille extends TypePartie {
    /**
     * Grille de la partie.
     * @return 
     *  Grille.
     */
    public CubeGrille getGrille();
}
