package Model;

import javafx.scene.control.Button;

/**
 *
 * @author eduardo
 */
public class Model {
    private int id;
    private String name;
    private Brand brand;
    private int accessoriesId;
    private boolean enable;
    private Button btnStatus;

    // <editor-fold defaultstate="collapsed" desc="Constructor"> 
    public Model() {

    }
    public Model(String name, Brand brand, boolean enable) {
        this.name = name;
        this.brand = brand;
        this.enable = enable;
    }
    public Model(int modelId, int accessoriesId, String name, Brand brand, boolean enable) {
        this.id = modelId;
        this.accessoriesId = accessoriesId;
        this.name = name;
        this.brand = brand;
        this.enable = enable;
    }// </editor-fold> 

    // <editor-fold defaultstate="collapsed" desc="Gets and Sets">  
    public int getId() {
        return id;
    }
    public void setId(int modelId) {
        this.id = modelId;
    }
    public int getAccessoriesId() {
        return accessoriesId;
    }
    public void setAccessoriesId(int accessoriesId){
        this.accessoriesId = accessoriesId;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Brand getBrand() {
        return brand;
    }
    public void setBrand(Brand brand) {
        this.brand = brand;
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
