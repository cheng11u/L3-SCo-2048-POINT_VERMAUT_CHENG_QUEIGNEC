/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serveur;

import java.util.List;

/**
 *
 * @author Nicolas QUEIGNEC
 */
public class Protocole {
    private static final String SEPARATEUR_PARAM="-";
    private static final String SEPARATEUR_VALEUR_PARAM=":";
    private static final String SEPARATEUR_VALEUR_MULTIPLE=",";
    public static final String REQ_CREER_PARTIE="CrPa";
    public static final String REP_CREER_PARTIE_REUSSI="CrPaSu";
    public static final String REP_CREER_PARTIE_ECHOUE="CrPaFa";
    public static final String REQ_AFFICHER_PARTIES="AfPa";
    /**
     * AfPaSu-Id:Id1-Proprio:J1-Ouvert:Oui-Id:Id2-Proprio:J2-Ouvert:Non
     * Id : identifiants des parties
     * Proprio : noms des créateurs de chaques parties 
     * Ouvert : Oui si la partie est joignable sinon Non
     * @param parties Liste des parties à afficher
     * @return Chaine décrivant la liste des parties
     */
    public static String REP_AFFICHER_PARTIES(List<Partie> parties){
        String res="AfPaSu";
        for (Partie p : parties) {
            res+="-Id:"+p.getId()+"-Proprio:"+p.getClient1()+"-Ouvert:";
            res+=p.estJoignable()?"Oui":"Non";
        }
        return res;
    }
    /**
     * JoPa-Id:1200
     * @param id : identifiant de la partie a rejoindre
     * @return chaine descriptive
     */
    public static String REQ_REJOINDRE_PARTIE(int id){
        return "JoPa-Id:"+id;
    }
    public static final String REP_REJOINDRE_PARTIE_REUSSI="JoPaSu";
    public static final String REP_REJOINDRE_PARTIE_ECHOUE="JoPaFa";
    /**
     * HaJoPa-Nom:Pseudo
     * Nom : nom du joueur ayant rejoint la partie
     * @param client ayant rejoint la partie 
     * @return chaine descriptive
     */
    public static String REP_A_REJOINT_PARTIE(Client client){
        return "HaJoPa-Nom:"+client;
    }
    public static final String REQ_PRET="Re";
    /**
     * IsRe-Nom:Pseudo
     * Nom : nom du joueur étant prêt à commencer la partie 
     * @param client Joueur pret à démarrer 
     * @return chaine descriptive
     */
    public static String REP_PRET(Client client){
        return "IsRe-Nom:"+client;
    }
    /**
     * Pl-Dir:-3
     * Dir : direction voir les directions dans la grille
     * @param direction 
     * @return chaine descriptive
     */
    public static String REQ_JOUER(int direction){
        return "Pl-Dir:"+direction;
    }
    /**
     * HaPl-Nom:Pseudo-Dir:-3
     * Nom : nom du joueur ayant joué
     * Dir : direction voir les directions dans la grille
     * @param client Joueur ayant joué
     * @param direction
     * @return chaine descriptive
     */
    public static String REP_A_JOUER(Client client, int direction){
        return "HaPl-Nom:"+client+"-Dir:"+direction;
    }
    public static final String REQ_QUITTER_PARTIE="QuPa";
    /**
     * HaQuPa-Nom:Pseudo
     * Nom : nom du joueur ayant quitté la^partie
     * @param client Joueur ayant quitté sa partie
     * @return chaine descriptive
     */
    public static String REP_A_QUITTER(Client client) {
        return "HaQuPa-Nom:"+client;
    }
    public static final String REQ_DECONNECTER="Di";
    /**
     * Co-Id:Nom-Mdp:Azerty1234
     * @param id : identifiant pour se connecter
     * @param mdp : mot de passe du compte
     * @return chaine descriptive
     */ 
    public static String REQ_CONNECTER_COMPTE(String id, String mdp){
        return "Co-Id:"+id+"-Mdp:"+mdp;
    }
    public static final String REP_CONNEXION_REUSSIE="CoSu";
    public static final String REP_CONNEXION_ECHOUEE="CoFa";
    public static final String REQ_SAUVEGARDER_SCORE="SaSc";
    public static final String REP_SAUVEGARDE_REUSSIE="SaScSu";
    public static final String REP_SAUVEGARDE_ECHOUEE="SaScFa";
    public static final String REQ_AFFICHER_CLASSEMENT="AfCl";
    public static final String REP_AFFICHER_CLASSEMENT="AfClSu";
}
