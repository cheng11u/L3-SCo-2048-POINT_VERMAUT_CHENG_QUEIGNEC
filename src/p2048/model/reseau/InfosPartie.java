package p2048.model.reseau;

/**
 * Stocke les informations d'une partie réseau disponible.
 * @author Nicolas QUEIGNEC
 */
public class InfosPartie {
    /**
     * {@link PartieReseau#id}
     */
    private int id;
    /**
     * Nom du joueur propriétaire de la partie.
     */
    private String nomJoueur;

    /**
     * Constructeur.
     * @param id
     *  {@link #id}
     * @param nomJoueur 
     *  {@link #nomJoueur}
     */
    public InfosPartie(int id, String nomJoueur) {
        this.id = id;
        this.nomJoueur = nomJoueur;
    }

    /**
     * Getter.
     * @return
     *  {@link #id} 
     */
    public int getId() {
        return id;
    }

    /**
     * Getter.
     * @return
     *  {@link #nomJoueur} 
     */
    public String getNomJoueur() {
        return nomJoueur;
    }
}
