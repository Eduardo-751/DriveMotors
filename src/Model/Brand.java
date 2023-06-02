package Model;

/**
 *
 * @author eduardo
 */
public class Brand {
    private int brandId;
    private String name;

    // <editor-fold defaultstate="collapsed" desc="Constructor"> 
    public Brand() {
        
    }
    public Brand(String name) {
        this.name = name;
    }
    public Brand(int brandId, String name) {
        this.brandId = brandId;
        this.name = name;
    }// </editor-fold> 

    // <editor-fold defaultstate="collapsed" desc="Gets and Sets">  
    public int getId() {
        return brandId;
    }
    public void setId(int brandId) {
        this.brandId = brandId;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }// </editor-fold> 
}
