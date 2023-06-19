package Model;

import javafx.scene.control.Button;

/**
 *
 * @author eduardo
 */
public class Brand {
    private int id;
    private String name;
    private boolean enable;
    private Button btnStatus;

    // <editor-fold defaultstate="collapsed" desc="Constructor"> 
    public Brand() {
        
    }
    public Brand(String name, boolean enable) {
        this.name = name;
        this.enable = enable;
    }
    public Brand(int brandId, String name, boolean enable) {
        this.id = brandId;
        this.name = name;
        this.enable = enable;
    }// </editor-fold> 

    // <editor-fold defaultstate="collapsed" desc="Gets and Sets">  
    public int getId() {
        return id;
    }
    public void setId(int brandId) {
        this.id = brandId;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
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
    }// </editor-fold> 
}
