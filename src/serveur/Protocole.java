/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serveur;

/**
 *
 * @author Nicolas QUEIGNEC
 */
public class Protocole {
    public static final String SEPARATEUR_PARAM="-";
    public static final String SEPARATEUR_VALEUR_PARAM=":";
    public static final String SEPARATEUR_VALEUR_MULTIPLE=",";
    public static final String REQ_CREER_PARTIE="CrPa";
    public static final String REP_CREER_PARTIE_REUSSI="CrPaSu";
    public static final String REP_CREER_PARTIE_ECHOUE="CrPaFa";
    public static final String REQ_AFFICHER_PARTIES="AfPa";
    /**
     * AfPaSu-Ids:Id1,Id2,Id3-Proprios:J1,J2,J3-Ouvert:Oui,Non,Non
     * Ids : identifiants des parties
     * Proprios : noms des créateurs de chaques parties 
     * Ouvert : Oui si la partie est joignable sinon Non
     */
    public static final String REP_AFFICHER_PARTIES="AfPaSu";
    /**
     * JoPa-Id:1200
     * Id : identifiant de la partie a rejoindre
     */
    public static final String REQ_REJOINDRE_PARTIE="JoPa";
    public static final String REP_REJOINDRE_PARTIE_REUSSI="JoPaSu";
    public static final String REP_REJOINDRE_PARTIE_ECHOUE="JoPaFa";
    /**
     * HaJoPa-Nom:Pseudo
     * Nom : nom du joueur ayant rejoint la partie
     */
    public static final String REP_A_REJOINT_PARTIE="HaJoPa";
    public static final String REQ_PRET="Re";
    /**
     * IsRe-Nom:Pseudo
     * Nom : nom du joueur étant prêt à commencer la partie 
     */
    public static final String REP_PRET="IsRe";
    /**
     * Pl-Dir:-3
     * Dir : direction voir les directions dans la grille
     */
    public static final String REQ_JOUER="Pl";
    /**
     * HaPl-Nom:Pseudo-Dir:-3
     * Nom : nom du joueur ayant joué
     * Dir : direction voir les directions dans la grille
     */
    public static final String REP_A_JOUER="HaPl";
    public static final String REQ_QUITTER_PARTIE="QuPa";
    /**
     * HaQuPa-Nom:Pseudo
     * Nom : nom du joueur ayant quitté la^partie
     */
    public static final String REP_A_QUITTER="HaQuPa";
    public static final String REQ_DECONNECTER="Di";
    /**
     * Co-Id:Nom-Mdp:Azerty1234
     * Id : identifiant pour se connecter
     * Mdp : mot de passe du compte
     */ 
    public static final String REQ_CONNECTER_COMPTE="Co";
    public static final String REP_CONNEXION_REUSSIE="CoSu";
    public static final String REP_CONNEXION_ECHOUEE="CoFa";
    public static final String REQ_SAUVEGARDER_SCORE="SaSc";
    public static final String REP_SAUVEGARDE_REUSSIE="SaScSu";
    public static final String REP_SAUVEGARDE_ECHOUEE="SaScFa";
    public static final String REQ_AFFICHER_CLASSEMENT="AfCl";
    public static final String REP_AFFICHER_CLASSEMENT="AfClSu";
}
