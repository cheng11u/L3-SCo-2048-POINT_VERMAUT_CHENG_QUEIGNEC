package p2048.model.reseau;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import p2048.model.TypePartie;
import serveur.Protocole;

/**
 * Partie en réseau.
 * @author Nicolas QUEIGNEC
 */
public abstract class PartieReseau implements TypePartie {
    /**
     * Identifiant de la partie.
     */
    private int id;
    /**
     * Pour savoir si le joueur 1 est prêt à commencer la partie.
     */
    private BooleanProperty joueur1pret;
    /**
     * Pour savoir si le joueur 2 est prêt à commencer la partie.
     */
    private BooleanProperty joueur2pret;
    /**
     * Pour savoir si le client actuel est le joueur 1..
     */
    private boolean estJoueur1;
    /**
     * Nom de l'autre joueur qui est dans la partie.
     */
    private StringProperty nomAutreJoueur;
    /**
     * Thread recevant les messages réseaux et agissant sur la partie.
     */
    private Thread receveur;
    /**
     * Pour savoir si la partie a été quittée par le joueur.
     */
    private boolean morte;
    /**
     * Pour savoir si la partie a démarrée.
     */
    private boolean commencee;
    
    /**
     * Constructeur.
     * @param id
     *  {@link #id}
     * @param estJoueur1 
     *  {@link #estJoueur1}
     */
    public PartieReseau(int id, boolean estJoueur1) {
        this.id=id;
        this.estJoueur1=estJoueur1;
        this.joueur1pret=new SimpleBooleanProperty(false);
        this.joueur2pret=new SimpleBooleanProperty(false);
        this.receveur=new Thread(new ReceveurServeur(this));
        this.nomAutreJoueur=new SimpleStringProperty(null);
        this.morte=false;
        this.commencee=false;
    }
    
    /**
     * Permet de dire que le joueur est prêt à commencer la partie.
     */
    public void pret() {
        if (getNomAutreJoueur()!=null) { 
            if (estJoueur1) {
                if (!joueur1pret())
                    joueur1pret.set(true);
            } else {
                if (!joueur2pret())
                    joueur2pret.set(true);
            }                
            Reseau.getInstance().envoyerMessage(Protocole.REQ_PRET);
            if (joueur1pret() && joueur2pret() && !commencee){
                commencerPartie();
                commencee=true;
            }
        } 
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
     * Setter.
     * @param id 
     *  {@link #id}
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Getter.
     * @return
     *  {@link #receveur}
     */
    public Thread getReceveur() {
        return receveur;
    }
    
    /**
     * Getter.
     * @return
     *  Grille sur laquelle l'autre joueur joue. 
     */
    public abstract GrilleReseau getGrilleReseauRecu();
    
    /**
     * Getter.
     * @return
     *  Grille sur laquelle le joueur joue. 
     */
    public abstract GrilleReseau getGrilleReseauEnvoi();
    
    /**
     * Ajoute un joueur a la partie si la place est libre.
     * @param nom 
     *  Nom du joueur à ajouter.
     */
    public void ajouterJoueur(String nom) {
        if (getNomAutreJoueur()==null)
            nomAutreJoueur.set(nom);
    }
    
    /**
     * Retire l'autre joueur s'il a quitté la partie.
     * @param nom 
     *  Nom du joueur.
     */    
    public void enleverJoueur(String nom) {
        if (getNomAutreJoueur().equals(nom)) {
            nomAutreJoueur.set(null);
            if (estJoueur1 && joueur1pret() && !joueur2pret())
                joueur1pret.set(false);
            else if (!estJoueur1 && !joueur1pret() && joueur2pret())
                joueur2pret.set(false);
        }
    }
    
    /**
     * Met l'autre joueur en position prêt.
     * @param nom 
     *  Nom du joueur.
     */    
    public void autreJoueurPret(String nom) {
        if (getNomAutreJoueur().equals(nom)) {
            if (estJoueur1)
                joueur2pret.set(true);
            else
                joueur1pret.set(true);
            if (joueur1pret() && joueur2pret() && !commencee) {
                commencerPartie();
                commencee=true;
            }
        }
    }
    
    /**
     * Pour que l'autre joueur puisse jouer.
     * @param nom
     *  Nom du joueur.
     * @param direction 
     *  Direction dans laquelle l'autre joueur a joué.
     */
    public void autreJoueurAJouer(String nom, int direction) {
        if (getNomAutreJoueur().equals(nom)) { 
            getGrilleReseauRecu().setDirection(direction);
            Reseau.getInstance().envoyerMessage(Protocole.ACC_RECEP_A_JOUER);
        } 
    }
    
    @Override
    public void jouer(int direction) {
        this.getGrilleReseauEnvoi().setDirection(direction);
        Reseau.getInstance().envoyerMessage(Protocole.REQ_JOUER(direction));
    }
    
    @Override
    public void quitter() {
        Reseau.getInstance().envoyerMessage(Protocole.REQ_QUITTER_PARTIE);
        morte=true;
    }

    /**
     * Getter.
     * @return
     *  {@link #morte}
     */
    public boolean estMorte() {
        return morte;
    }

    /**
     * Getter.
     * @return
     *  {@link #nomAutreJoueur}
     */
    public String getNomAutreJoueur() {
        return nomAutreJoueur.get();
    }

    /**
     * Getter.
     * @return
     *  {@link #estJoueur1}
     */
    public boolean estJoueur1() {
        return estJoueur1;
    }

    /**
     * Getter.
     * @return
     *  {@link #joueur1pret}
     */
    public boolean joueur1pret() {
        return joueur1pret.get();
    }

    /**
     * Getter.
     * @return
     *  {@link #joueur2pret}
     */
    public boolean joueur2pret() {
        return joueur2pret.get();
    }
    
    /**
     * Ajoute un listener pour pouvoir afficher la case en temps réel.
     * @param listener 
     *  Listener mettant à jour l'interface graphique.
     */
    public void ajouterListener(ChangeListener listener) {
        joueur1pret.addListener(listener);
        joueur2pret.addListener(listener);
        nomAutreJoueur.addListener(listener);
        getGrilleReseauRecu().ajouterListener(listener);
        if (getGrilleReseauRecu()!=getGrilleReseauEnvoi())
            getGrilleReseauEnvoi().ajouterListener(listener);
    }
    
}
