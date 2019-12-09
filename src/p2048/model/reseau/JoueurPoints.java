package p2048.model.reseau;

/**
 * Stock le score d'un joueur.
 * @author Luc Cheng
 */
public class JoueurPoints {
    /**
     * Pseudo du joueur.
     */
    private String pseudo;
    /**
     * Score.
     */
    private int score;
    
    /**
     * Constructeur.
     * @param pseudo
     *  {@link #pseudo}
     * @param score 
     *  {@link #score}
     */
    public JoueurPoints(String pseudo, int score){
        this.pseudo = pseudo;
        this.score = score;
    }
    
    /**
     * Getter.
     * @return 
     *  {@link #pseudo}
     */
    public String getPseudo(){
        return this.pseudo;
    }

    /**
     * Setter.
     * @param pseudo 
     *  {@link #pseudo}
     */
    public void setPseudo(String pseudo){
        this.pseudo = pseudo;
    }
    
    /**
     * Getter.
     * @return
     *  {@link #score}
     */
    public int getScore(){
        return this.score;
    }
    
    /**
     * Setter.
     * @param score 
     *  {@link #score}
     */
    public void setScore(int score){
        this.score = score;
    }
}
