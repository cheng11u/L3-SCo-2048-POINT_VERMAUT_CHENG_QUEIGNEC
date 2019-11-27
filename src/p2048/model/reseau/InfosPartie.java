/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package p2048.model.reseau;

/**
 *
 * @author Nicolas QUEIGNEC
 */
public class InfosPartie {
    private int id;
    private String nomJoueur;

    public InfosPartie(int id, String nomJoueur) {
        this.id = id;
        this.nomJoueur = nomJoueur;
    }

    public int getId() {
        return id;
    }

    public String getNomJoueur() {
        return nomJoueur;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNomJoueur(String nomJoueur) {
        this.nomJoueur = nomJoueur;
    }
}
