package p2048.model;

/**
 * Partie de 2048-3D.
 * @author Nicolas QUEIGNEC
 */
public interface TypePartie {
    /**
     * Commence une partie
     */
    public void commencerPartie();
    
    /**
     * Sauvegarde le classement du joueur dans la base de données
     */
    public void sauvegarderClassement();
    
    /**
     * Jouer dans la direction choisi.
     * @param direction 
     *  Direction voulu.
     */
    public void jouer(int direction);
    
    /**
     * Quitte la partie.
     */
    public void quitter();
}
