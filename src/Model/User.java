package Model;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Button;

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
    private boolean enable;
    private Button btnStatus, btnUpdate;

    // <editor-fold defaultstate="collapsed" desc="Constructor">   
    public User() {
        
    }
    public User(String login, String password, String name, boolean enable) {
        this.login = login;
        this.password = password;
        this.name = name;
        this.enable = enable;
    }
    public User(int usuarioId, String login, String password, String name, boolean enable) {
        this.usuarioId = usuarioId;
        this.login = login;
        this.password = password;
        this.name = name;
        this.enable = enable;
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
    }
    public boolean isEnable() {
        return enable;
    }
    public void setEnable(boolean enable) {
        this.enable = enable;
    }
    public String getStatus(){
        if(enable)
            return "Ativo";
        else
            return "Inativo";
    }public void setBtnStatus(Button btn){
        this.btnStatus = btn;
    }
    public Button getBtnStatus(){
        if(enable)
            btnStatus.setText("Desativar");
        else
            btnStatus.setText("Ativar");
        
        return btnStatus;
    }
    public void setBtnUpdate(Button btn){
        this.btnUpdate= btn;
    }
    public Button getBtnUpdate(){
        btnUpdate.setText("Editar");
        return btnUpdate;
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

    public String setCriptografia(String new_pass) {
        MessageDigest md;
        String pass = null;
        try {
            md = MessageDigest.getInstance("MD5");
            pass = new String(hexCodes(md.digest(new_pass.getBytes())));
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        }

        return pass;
    }

}
