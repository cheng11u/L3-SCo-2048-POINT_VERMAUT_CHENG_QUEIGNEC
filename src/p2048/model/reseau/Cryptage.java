package p2048.model.reseau;
import java.io.UnsupportedEncodingException;
import java.security.*;
/**
 * Classe pour crypter les mots de passe.
 * @author Luc Cheng
 */
public class Cryptage {
    /**
     * Crypte une chaîne en SHA-256.
     * @param chaine
     *  Chaine à crypter.
     * @return 
     *  Chaine cryptée.
     */
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
