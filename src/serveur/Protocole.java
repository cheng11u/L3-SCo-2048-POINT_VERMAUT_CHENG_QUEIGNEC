/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serveur;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Nicolas QUEIGNEC
 */
public class Protocole {
    public static final String SEPARATEUR_VALEUR_MULTIPLE=",";
    public static final String SEPARATEUR_PARAM="-";
    public static final String SEPARATEUR_VALEUR_PARAM=":";
    public static final String REQ_CREER_PARTIE="CrPa";
    public static String REQ_CREER_PARTIE(int type){
        return REQ_CREER_PARTIE+"-Type:"+type;
    }
    public static final String REP_CREER_PARTIE_REUSSI="CrPaSu";
    public static String REP_CREER_PARTIE_REUSSI(int id){
        return "CrPaSu-Id:"+id;
    }
    public static final String REP_CREER_PARTIE_ECHOUE="CrPaFa";
    public static final String REQ_AFFICHER_PARTIES="AfPa";
    public static String REQ_AFFICHER_PARTIES(int type){
        return "AfPa-Type:"+type;
    }
    public static final String REP_AFFICHER_PARTIES="AfPaSu";
    /**
     * AfPaSu-Ids:Id1,Id2,Id3-Proprios:J1,J2,J3-Ouvert:Oui,Non,Oui
     * Id : identifiants des parties
     * Proprio : noms des créateurs de chaques parties 
     * Ouvert : Oui si la partie est joignable sinon Non
     * @param parties Liste des parties à afficher
     * @return Chaine décrivant la liste des parties
     */
    public static String REP_AFFICHER_PARTIES(List<Partie> parties){
        String res=REP_AFFICHER_PARTIES;
        String ids="-Ids:";
        String proprios="-Proprios:";
        String ouvert="-Ouvert:";
        for (Partie p : parties) {
            ids+=p.getId()+",";
            proprios+=p.getClient1()+",";
            ouvert+=(p.estJoignable()?"Oui":"Non")+",";
        }
        ids=ids.substring(0, ids.lastIndexOf(","));
        proprios=proprios.substring(0, ids.lastIndexOf(","));
        ouvert=ouvert.substring(0, ids.lastIndexOf(","));
        res+=ids+proprios+ouvert;
        return res;
    }
    public static final String REQ_REJOINDRE_PARTIE="JoPa";
    /**
     * JoPa-Id:1200
     * @param id : identifiant de la partie a rejoindre
     * @return chaine descriptive
     */
    public static String REQ_REJOINDRE_PARTIE(int id){
        return REQ_REJOINDRE_PARTIE+"-Id:"+id;
    }
    public static final String REP_REJOINDRE_PARTIE_REUSSI="JoPaSu";
    public static final String REP_REJOINDRE_PARTIE_ECHOUE="JoPaFa";
    public static final String REP_A_REJOINT_PARTIE="HaJoPa";
    /**
     * HaJoPa-Nom:Pseudo
     * Nom : nom du joueur ayant rejoint la partie
     * @param client ayant rejoint la partie 
     * @return chaine descriptive
     */
    public static String REP_A_REJOINT_PARTIE(Client client){
        return REP_A_REJOINT_PARTIE+"-Nom:"+client;
    }
    public static final String REQ_PRET="Re";
    public static final String REP_PRET="IsRe";
    /**
     * IsRe-Nom:Pseudo
     * Nom : nom du joueur étant prêt à commencer la partie 
     * @param client Joueur pret à démarrer 
     * @return chaine descriptive
     */
    public static String REP_PRET(Client client){
        return REP_PRET+"-Nom:"+client;
    }
    public static final String REQ_JOUER="Pl";
    /**
     * Pl-Dir:-3
     * Dir : direction voir les directions dans la grille
     * @param direction 
     * @return chaine descriptive
     */
    public static String REQ_JOUER(int direction){
        return REQ_JOUER+"-Dir:"+direction;
    }
    public static final String REP_A_JOUER="HaPl";
    /**
     * HaPl-Nom:Pseudo-Dir:-3
     * Nom : nom du joueur ayant joué
     * Dir : direction voir les directions dans la grille
     * @param client Joueur ayant joué
     * @param direction
     * @return chaine descriptive
     */
    public static String REP_A_JOUER(Client client, int direction){
        return REP_A_JOUER+"-Nom:"+client+"-Dir:"+direction;
    }
    public static final String REP_CREER_CASE="CrCa";
    public static String REP_CREER_CASE(int index, int val){
        return REP_CREER_CASE+"-Index:"+index+"-Val:"+val;
    }
    public static final String ACC_RECEP_A_JOUER="ReHaPl";
    public static final String REQ_QUITTER_PARTIE="QuPa";
    public static final String REP_A_QUITTER="HaQuPa";
    /**
     * HaQuPa-Nom:Pseudo
     * Nom : nom du joueur ayant quitté la^partie
     * @param client Joueur ayant quitté sa partie
     * @return chaine descriptive
     */
    public static String REP_A_QUITTER(Client client) {
        return REP_A_QUITTER+"-Nom:"+client;
    }
    public static final String REQ_DECONNECTER="Di";
    public static final String REQ_CONNECTER_COMPTE="Co";
    /**
     * Co-Id:Nom-Mdp:Azerty1234
     * @param id : identifiant pour se connecter
     * @param mdp : mot de passe du compte
     * @return chaine descriptive
     */ 
    public static String REQ_CONNECTER_COMPTE(String id, String mdp){
        return REQ_CONNECTER_COMPTE+"-Id:"+id+"-Mdp:"+mdp;
    }
    public static final String REP_CONNEXION_REUSSIE="CoSu";
    public static final String REP_CONNEXION_ECHOUEE="CoFa";
    public static final String REQ_SAUVEGARDER_SCORE="SaSc";
    public static final String REP_SAUVEGARDE_REUSSIE="SaScSu";
    public static final String REP_SAUVEGARDE_ECHOUEE="SaScFa";
    public static final String REQ_AFFICHER_CLASSEMENT="AfCl";
    public static final String REP_AFFICHER_CLASSEMENT="AfClSu";
    
    public static Map<String,String> getParams(String cmd) {
        HashMap<String, String> res=new HashMap<String,String>();
        String[] params=cmd.split(SEPARATEUR_PARAM);
        for (int i=1;i<params.length; i++) {
            String[] paramVal=params[i].split(SEPARATEUR_VALEUR_PARAM);
            res.put(paramVal[0], paramVal[1]);
        }
        return res;
    }
}
