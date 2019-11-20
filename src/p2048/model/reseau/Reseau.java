/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package p2048.model.reseau;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import p2048.model.CubeGrille;
import serveur.Partie;
import serveur.Protocole;

/**
 * @author Nicolas QUEIGNEC
 */
public class Reseau {     
    private Socket socket;
    private PrintWriter envoyeut;
    private BufferedReader receveur;
    private static Reseau instance;
    
    private Reseau(){
        try {
            BufferedReader lectureConf=new BufferedReader(new FileReader(new File("2048.conf")));
            HashMap<String,String> params=new HashMap<String,String>();
            String ligne=null;
            while ((ligne=lectureConf.readLine())!=null) {                
                params.put(ligne.split("=")[0], ligne.split("=")[1]);
            }
            this.socket=new Socket(params.get("IP"), Integer.parseInt(params.get(("PORT"))));
            this.envoyeut=new PrintWriter(this.socket.getOutputStream());
            this.receveur=new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    public static synchronized Reseau getInstance() {
        if (instance==null || instance.socket.isClosed())
            instance=new Reseau();
        return instance;
    }
    
    public void envoyerMessage(String message) {
        this.envoyeut.println(message);
        this.envoyeut.flush();
    }
    
    public String recevoirMessage() throws IOException {
        return this.receveur.readLine();
    }
    
    public void deconnecter() {
        try {     
            this.envoyeut.close();
            this.receveur.close();     
            this.socket.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    public PartieReseau creerPartieCoop() {
        envoyerMessage(Protocole.REQ_CREER_PARTIE(Partie.TYPE_PARTIE_COOP));
        try {
            String recu=recevoirMessage();
            if (recu.split("-")[0]==Protocole.REP_CREER_PARTIE_REUSSI)
                return new Cooperation(Integer.parseInt(Protocole.getParams(recu).get("Id")));
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            return null;
        }
    }
}
