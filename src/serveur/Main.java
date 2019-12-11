
package serveur;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Classe principale du serveur.
 * @author Nicolas QUEIGNEC
 */
public class Main {
    /**
     * ServerSocket en attente des connexions des client.
     */
    private static ServerSocket serveur;
    /**
     * Port utilisé.
     */
    private final static int port=355;
    /**
     * Liste des clients connectés au serveur.
     */
    public static List<Client> clients;
    /**
     * Liste des parties en cours.
     */
    public static Map<Integer, Partie> parties;
    
    /**
     * Main.
     * @param args 
     *  Argument en ligne de commande.
     */
    public static void main(String[] args) {
        System.out.println("Démarrage...");
        clients=new ArrayList<Client>();
        parties=new HashMap<Integer, Partie>();
        try {
            serveur=new ServerSocket(port);
            System.out.println("Démarrage réussi !");
            while (true) {                
                clients.add(new Client(serveur.accept()));
                new Thread(clients.get(clients.size()-1)).start();
            }
        } catch (IOException ex) {
            System.err.println("Erreur lors du lancement. Fermeture...");
            System.exit(1);
        }
    }
}
