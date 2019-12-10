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
    private int id;
    private BooleanProperty joueur1pret;
    private BooleanProperty joueur2pret;
    private boolean estJoueur1;
    private StringProperty nomAutreJoueur;
    private Thread receveur;
    private boolean morte;
    private boolean commencee;
    
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
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Thread getReceveur() {
        return receveur;
    }
    
    public abstract GrilleReseau getGrilleReseauRecu();
    public abstract GrilleReseau getGrilleReseauEnvoi();
    
    public void ajouterJoueur(String nom) {
        if (getNomAutreJoueur()==null)
            nomAutreJoueur.set(nom);
    }
    
    public void enleverJoueur(String nom) {
        if (getNomAutreJoueur().equals(nom)) {
            nomAutreJoueur.set(null);
            if (estJoueur1 && joueur1pret() && !joueur2pret())
                joueur1pret.set(false);
            else if (!estJoueur1 && !joueur1pret() && joueur2pret())
                joueur2pret.set(false);
        }
    }
    
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
    
    public void autreJoueurAJouer(String nom, int direction) {
        if (getNomAutreJoueur().equals(nom)) { 
            getGrilleReseauRecu().setDirection(direction);
            Reseau.getInstance().envoyerMessage(Protocole.ACC_RECEP_A_JOUER);
            System.out.println("p2048.model.reseau.PartieReseau.autreJoueurAJouer()");
        } 
    }
    
    @Override
    public void jouer(int direction) {
        this.getGrilleReseauEnvoi().setDirection(direction);
        Reseau.getInstance().envoyerMessage(Protocole.REQ_JOUER(direction));
        System.out.println("p2048.model.reseau.PartieReseau.jouer()");
    }
    
    @Override
    public void quitter() {
        Reseau.getInstance().envoyerMessage(Protocole.REQ_QUITTER_PARTIE);
        morte=true;
    }

    public boolean estMorte() {
        return morte;
    }

    public String getNomAutreJoueur() {
        return nomAutreJoueur.get();
    }

    public boolean estJoueur1() {
        return estJoueur1;
    }

    public boolean joueur1pret() {
        return joueur1pret.get();
    }

    public boolean joueur2pret() {
        return joueur2pret.get();
    }
    
    public void ajouterListener(ChangeListener listener) {
        joueur1pret.addListener(listener);
        joueur2pret.addListener(listener);
        nomAutreJoueur.addListener(listener);
        getGrilleReseauRecu().ajouterListener(listener);
        if (getGrilleReseauRecu()!=getGrilleReseauEnvoi())
            getGrilleReseauEnvoi().ajouterListener(listener);
    }
    
}
