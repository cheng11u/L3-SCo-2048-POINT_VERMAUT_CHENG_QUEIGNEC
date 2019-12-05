/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package p2048.model.reseau;

import java.io.IOException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import serveur.Protocole;

/**
 *
 * @author Nicolas QUEIGNEC
 */
public class ReceveurServeur implements Runnable{
    private PartieReseau partie;
    public ReceveurServeur(PartieReseau partie) {
        this.partie=partie;
    }

    @Override
    public void run() {
        while (!partie.getGrilleReseauRecu().partieTerminee() && Reseau.estConnecter() && !partie.estMorte()) {            
            try {
                String message = Reseau.getInstance().recevoirMessage();
                String cmd=message.split(Protocole.SEPARATEUR_PARAM)[0];
                Map<String,String> params=Protocole.getParams(message);
                switch (cmd) {
                    case Protocole.REP_A_REJOINT_PARTIE:
                        partie.ajouterJoueur(params.get("Nom"));
                        break;
                    case Protocole.REP_A_QUITTER:
                        partie.enleverJoueur(params.get("Nom"));
                        break;
                    case Protocole.REP_PRET:
                        partie.autreJoueurPret(params.get("Nom"));
                        break;
                    case Protocole.REP_A_JOUER:
                        partie.autreJoueurAJouer(params.get("Nom"), Integer.parseInt(params.get("Dir")));
                        break;                      
                    case Protocole.REP_CREER_CASE:
                        partie.getGrilleReseauRecu().creerCase(Integer.parseInt(params.get("Index")), Integer.parseInt(params.get("Val")));
                        break;
                }
            } catch (IOException ex) {
                Logger.getLogger(ReceveurServeur.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
    }
}
