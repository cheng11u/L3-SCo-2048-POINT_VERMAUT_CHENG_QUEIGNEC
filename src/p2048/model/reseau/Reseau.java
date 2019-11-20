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
import java.util.HashMap;

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
}
