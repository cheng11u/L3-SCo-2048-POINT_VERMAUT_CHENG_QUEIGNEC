/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serveur;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 * Cette classe contient les fonctions permettant d'interagir avec la base de données
 * @author Luc Cheng
 */
public class RequetesBDD {
    
    /**
     * Ajoute un joueur dans la base de données.
     * @param pseudo pseudo du joueur
     * @param nom nom du joueur
     * @param prenom prénom du joueur
     * @param mdp mot de passe du joueur
     * @return <code>true</code> si le joueur a pu être ajouté, <code>false</code> sinon
     */
    public static boolean creerJoueur(String pseudo, String nom, String prenom, String mdp){
        boolean res;
        PreparedStatement ps = null;
        try {
            Connection connect = ConnexionBDD.getConnection();
            ps = connect.prepareStatement("INSERT INTO joueur VALUES (?, ?, ?, ?)");
            ps.setString(1, pseudo);
            ps.setString(2, nom);
            ps.setString(3, prenom);
            ps.setString(4, mdp);
            int nbTuples = ps.executeUpdate();
            
            res = (nbTuples == 1);
        }
        catch (Exception e){
            e.printStackTrace();
            res = false;
        }
        finally {
            try {
                if (ps != null)
                    ps.close();
                }
            catch (Exception e){
                e.printStackTrace();
            }
        }
        return res;
    }
    /**
     * Donne le classement des joueurs dans l'odre décroissant des scores maximaux
     * de chaque joueur.
     * @return classement
     */
    public static ArrayList<Jouer> donnerClassement(){
        ArrayList<Jouer> res = new ArrayList<Jouer>();
        try {
            Connection connect = ConnexionBDD.getConnection();
            String requete = "SELECT jouer.idPartie, typePartie, jouer.pseudo, MAX(score) "
                           + "FROM partie, jouer "
                           + "WHERE partie.idPartie = jouer.idPartie "
                           + "GROUP BY jouer.pseudo "
                           + "ORDER BY MAX(score) DESC";
            
            PreparedStatement ps = connect.prepareStatement(requete);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                int id = rs.getInt(1);
                String type = rs.getString(2);
                String pseudo = rs.getString(3);
                int score = rs.getInt(4);
                PartieBDD partie = new PartieBDD(id, type);
                Joueur joueur = new Joueur(pseudo, "", "", "");
                res.add(new Jouer(partie, joueur, score));
            }
            rs.close();
            ps.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return res;
    }
    
    /**
     * Ajoute une partie dans la base de données.
     * @param typePartie type de la partie
     * @return <code>true</code> si la partie a pu être ajoutée, <code>false</code> sinon
     */
    public static boolean insererPartie(String typePartie){
        String sqlInsererPartie = "INSERT INTO partie (idPartie, typePartie) SELECT MAX(idPartie)+1, ? FROM partie";
        PreparedStatement reqInsererPartie = null;
        boolean res = true;
        try {
            reqInsererPartie = ConnexionBDD.getConnection().prepareStatement(sqlInsererPartie);
            reqInsererPartie.setString(1, typePartie);
            res = (reqInsererPartie.executeUpdate() > 0);
            
        } catch (SQLException ex) {
            ex.printStackTrace();
            res = false;
        } finally {
            try {
                if (reqInsererPartie != null)
                    reqInsererPartie.close();
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
        return res;        
    }
    
    /**
     * Ajoute un tuple dans la table jouer. Comme cette fonction est appelée juste après 
     * la fonction insérant une partie, on prend en compte la partie qui vient d'être insérée
     * @param pseudo pseudo du joueur
     * @param score score du joueur
     * @return <code>true</code> si le tuple a été inséré, <code>false</code> sinon
     */
    public static boolean insererJouer(String pseudo, int score){
        String sql = "INSERT INTO jouer (pseudo, idPartie, score) SELECT ?, MAX(idPartie), ? FROM partie";
        PreparedStatement ps = null;
        boolean res;
        try {
            ps = ConnexionBDD.getConnection().prepareStatement(sql);
            ps.setString(1, pseudo);
            ps.setInt(2, score);
            res = ps.executeUpdate() > 0;
        }
        catch (SQLException e){
            res = false;
            e.printStackTrace();
        }
        finally {
            try {
                if (ps != null)
                    ps.close();
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
        return res;
    }
    
    /**
     * Donne le mot de passe d'un joueur à partir de son pseudo.
     * @param pseudo pseudo du joueur
     * @return joueur
     */
    public static Joueur donnerJoueur(String pseudo) {
        String sql = "SELECT pseudo, mdp FROM joueur WHERE pseudo = ?";
        PreparedStatement ps = null;
        ResultSet rs = null;
        Joueur res = null;
        try {
            ps = ConnexionBDD.getConnection().prepareStatement(sql);
            ps.setString(1, pseudo);
            rs = ps.executeQuery();
            if (rs.next()){
                String pseudoJoueur = rs.getString("pseudo");
                String mdpJoueur = rs.getString("mdp");
                res = new Joueur(pseudoJoueur, "", "", mdpJoueur);                
            }
            
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        finally {
            try {
                if (ps != null)
                    ps.close();
            }
            catch (Exception e){
                e.printStackTrace();
            }
            finally {
                try {
                    if (rs != null)
                        rs.close();
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
        return res;
    }
}
