/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serveur;
import java.io.UnsupportedEncodingException;
import java.security.*;
/**
 *
 * @author Luc Cheng
 */
public class Cryptage {
    
    public static String crypter(String chaine){
        String res = "";
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(chaine.getBytes("UTF-8"));
            byte[] bytes = md.digest();
            for (int i=0; i<bytes.length; i++){
                res += String.format("%02X", bytes[i]);
            }
        }
        catch (NoSuchAlgorithmException e){
            e.printStackTrace();
        }
        catch (UnsupportedEncodingException e){
            e.printStackTrace();
        }
        return res;
    }
}
