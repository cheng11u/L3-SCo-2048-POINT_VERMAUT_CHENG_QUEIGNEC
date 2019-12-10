/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serveur;
import java.sql.*;
import java.io.*;
import java.util.Properties;
/**
 * Cette classe gère la connexion à la base de données.
 * @author Luc Cheng
 */
public class ConnexionBDD {
    /**
     * Nom d'utilisateur
     */
    private static String username = "root";
    /**
     * Mot de passe
     */
    private static String password = "root";
    /**
     * Nom du serveur pour la base de données
     */
    private static String serverName = "localhost";
    /**
     * Port utilisé pour la connexion
     */
    private static int port = 3306;
    /**
     * Nom de la base de données
     */
    private static String dbName = "jeu2048";
    /**
     * Chemin vers le fichier de configuration
     */
    private static String fichierConf = "src/serveur/conf/db.conf";
    /**
     * Connexion courante
     */
    private static Connection instance = null;
    
    /**
     * Constructeur
     */
    private ConnexionBDD(){
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(fichierConf));
            String ligne = br.readLine();
            while (ligne != null){
                String[] champs = ligne.split("=");
                if (champs.length == 2){
                    switch (champs[0]){
                        case "username":
                            username = champs[1];
                            break;
                        case "password":
                            password = champs[1];
                            break;
                        case "serverName":
                            serverName = champs[1];
                            break;
                        case "port":
                            port = Integer.parseInt(champs[1]);
                            break;
                        case "dbName":
                            dbName = champs[1];
                            break;
                    }
                }
                ligne = br.readLine();
            }
            String url = "jdbc:mysql://" + serverName + ":";
            url += port + "/" + dbName;
            
            Properties prop = new Properties();
            prop.put("user", username);
            prop.put("password", password);
            
            if (instance != null)
                instance = null;
            
            instance = DriverManager.getConnection(url, prop);
            
        }
        catch (IOException e){
            e.printStackTrace();
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        finally {
            try {
                if (br != null)
                    br.close();
            }
            catch (IOException ioe){
            }
        }
    }
    
    /**
     * Retourne la connexion courante après l'avoir initialisée si elle n'a pas été initialisée.
     * @return connexion courante
     */
    public static synchronized Connection getConnection(){
        if (instance == null)
            new ConnexionBDD();
        return instance;
    }
    
    
    
}
