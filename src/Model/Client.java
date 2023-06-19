package Model;

import javafx.scene.control.Button;

/**
 *
 * @author eduardo
 */
public class Client {
    private int clienteId;
    private String name = null;
    private String cpf = null;
    private String rg = null;
    private String email = null;
    private boolean enable;
    private Button btnStatus, btnUpdate;
    
    // <editor-fold defaultstate="collapsed" desc="Constructor"> 
    public Client() {
        
    }
    
    public Client(String cpf, String name, String rg, String email, boolean enable) {
        this.cpf = cpf;
        this.name = name;
        this.rg = rg;
        this.email = email;
        this.enable = enable;
    }// </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Gets and Sets">  
    public int getId() {
        return clienteId;
    }
    public void setId(int clienteId) {
        this.clienteId = clienteId;
    }
    public String getName() {
        return name;
    }
    public void setName(String nome) {
        this.name = nome;
    }
    public String getCpf() {
        return cpf;
    }
    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
    public String getRg() {
        return rg;
    }
    public void setRg(String rg) {
        this.rg = rg;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
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
    } public void setBtnStatus(Button btn){
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
}
