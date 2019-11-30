package p2048.model;

/**
 *
 * @author Nicolas QUEIGNEC
 */
public interface TypePartie {
    /**
     * Commence une partie
     */
    public void commencerPartie();
    
    /**
     * Sauvegarde la classement du joueur dans la base de données
     */
    public void sauvegarderClassement();
    
    public void jouer(int direction);
    
    public void quitter();
}
