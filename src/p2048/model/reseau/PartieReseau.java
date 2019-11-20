/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package p2048.model.reseau;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import jdk.net.Sockets;
import p2048.model.CubeGrille;
import p2048.model.TypePartie;

/**
 * @author Nicolas QUEIGNEC
 */
public abstract class PartieReseau implements TypePartie {
    private int id;

    public PartieReseau() {
        
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Socket getSocket() {
        return socket;
    }
    
    public abstract CubeGrille getGrilleReseauRecu();
}
