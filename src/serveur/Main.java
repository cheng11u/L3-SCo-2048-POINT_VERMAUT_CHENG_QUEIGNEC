
package serveur;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Nicolas QUEIGNEC
 */
public class Main {
    private static ServerSocket serveur;
    private final static int port=355;
    public static List<Client> clients;
    public static Map<Integer, Partie> parties;
    
    public static void main(String[] args) {
        System.out.println("Démarrage...");
        clients=new ArrayList<Client>();
        parties=new HashMap<Integer, Partie>();
        try {
            serveur=new ServerSocket(port);
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
