package Model;

/**
 *
 * @author eduardo
 */
public class Model {
    private int modelId;
    private String name;
    private Brand brand;
    private int accessoriesId;

    // <editor-fold defaultstate="collapsed" desc="Constructor"> 
    public Model() {

    }
    public Model(String name, Brand brand) {
        this.name = name;
        this.brand = brand;
    }
    public Model(int modelId, int accessoriesId, String name, Brand brand) {
        this.modelId = modelId;
        this.accessoriesId = accessoriesId;
        this.name = name;
        this.brand = brand;
    }// </editor-fold> 

    // <editor-fold defaultstate="collapsed" desc="Gets and Sets">  
    public int getId() {
        return modelId;
    }
    public void setId(int modelId) {
        this.modelId = modelId;
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
    }// </editor-fold> 
}
