package Model;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 *
 * @author eduardo
 */
public class User {

    private int usuarioId;
    private String login = null;
    private String password = null;
    private String name = null;
    private String profile = null;

    // <editor-fold defaultstate="collapsed" desc="Constructor">   
    public User() {
        
    }
    public User(String login, String password, String name) {
        this.login = login;
        this.password = password;
        this.name = name;
    }
    public User(int usuarioId, String login, String password, String name) {
        this.usuarioId = usuarioId;
        this.login = login;
        this.password = password;
        this.name = name;
    }// </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Gets and Sets">    
    public int getId() {
        return usuarioId;
    }
    public void setId(int usuarioId) {
        this.usuarioId = usuarioId;
    }
    public String getLogin() {
        return this.login;
    }
    public void setLogin(String newValue) {
        this.login = newValue;
    }
    public String getPassword() {
        return this.password;
    }
    public void setPassword(String newValue) {
        this.password = newValue;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String newValue) {
        this.name = newValue;
    }
    public String getProfile() {
        return this.profile;
    }
    public void setProfile(String newValue) {
        this.profile = newValue;
    }// </editor-fold>   
    
    public static char[] hexCodes(byte[] text) {
        char[] hexOutput = new char[text.length * 2];
        String hexString;

        for (int i = 0; i < text.length; i++) {
            hexString = "00" + Integer.toHexString(text[i]);
            hexString.toUpperCase().getChars(hexString.length() - 2,
                    hexString.length(), hexOutput, i * 2);
        }
        return hexOutput;
    }

    public String setCriptografia(String new_pass) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        String pass = new String(hexCodes(md.digest(new_pass.getBytes())));

        return pass;
    }

}
