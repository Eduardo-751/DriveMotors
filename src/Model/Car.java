package Model;

/**
 *
 * @author eduardo
 */
public class Car {

    private int carId;;
    private int accessoriesId;
    private boolean[] acessoriesValue;
    private Model model;
    private String modelName, brandName;
    private String color;
    private String transmission;
    private String drivetrain;
    private Integer year;
    private String fuel;
    private String plate;
    private String renavam;
    private Integer mileage;
    private Double price;
    private Double engine;
    private String notes;
    private boolean enable;

    // <editor-fold defaultstate="collapsed" desc="Constructor">   
    public Car() {
        
    }
    public Car(Model model, String color, String transmission, String drivetrain, 
            Integer year, String fuel, String plate, String renavam, Integer mileage, 
            Double price, Double engine, String notes, boolean enable) {
        this.model = model;
        this.modelName = model.getName();
        this.brandName = model.getBrand().getName();
        this.color = color;
        this.transmission = transmission;
        this.drivetrain = drivetrain;
        this.year = year;
        this.fuel = fuel;
        this.plate = plate;
        this.renavam = renavam;
        this.mileage = mileage;
        this.price = price;
        this.engine = engine;
        this.notes = notes;
        this.enable = enable;
    }
    public Car(int accessoriesId, Model model, String color, String transmission, String drivetrain, 
            Integer year, String fuel, String plate, String renavam, Integer mileage, 
            Double price, Double engine, String notes, boolean enable) {
        this.accessoriesId = accessoriesId;
        this.model = model;
        this.modelName = model.getName();
        this.brandName = model.getBrand().getName();
        this.color = color;
        this.transmission = transmission;
        this.drivetrain = drivetrain;
        this.year = year;
        this.fuel = fuel;
        this.plate = plate;
        this.renavam = renavam;
        this.mileage = mileage;
        this.price = price;
        this.engine = engine;
        this.notes = notes;
        this.enable = enable;
    }
    public Car(int carId, int accessoriesId, Model model, String color, String transmission, String drivetrain, 
            Integer year, String fuel, String plate, String renavam, Integer mileage, 
            Double price, Double engine, String notes, boolean enable) {
        this.carId = carId;
        this.accessoriesId = accessoriesId;
        this.model = model;
        this.modelName = model.getName();
        this.brandName = model.getBrand().getName();
        this.color = color;
        this.transmission = transmission;
        this.drivetrain = drivetrain;
        this.year = year;
        this.fuel = fuel;
        this.plate = plate;
        this.renavam = renavam;
        this.mileage = mileage;
        this.price = price;
        this.engine = engine;
        this.notes = notes;
        this.enable = enable;
    }// </editor-fold>  

    // <editor-fold defaultstate="collapsed" desc="Gets and Sets">    
    public int getId() {
        return carId;
    }
    public void setId(int id_auto) {
        this.carId = id_auto;
    }
    public Model getModel() {
        return model;
    }
    public void setModel(Model model) {
        this.model = model;
    }
    public String getModelName() {
        return modelName;
    }
    public void setModelName(String carModel) {
        this.modelName = carModel;
    }
    public String getBrandName() {
        return brandName;
    }
    public void setBrandName(String carBrand) {
        this.brandName = carBrand;
    }
    public String getColor() {
        return color;
    }
    public void setColor(String color) {
        this.color = color;
    }
    public String getTransmission() {
        return transmission;
    }
    public void setTransmission(String transmission) {
        this.transmission = transmission;
    }
    public String getDrivetrain() {
        return drivetrain;
    }
    public void setDrivetrain(String drivetrain) {
        this.drivetrain = drivetrain;
    }
    public Integer getYear() {
        return year;
    }
    public void setYear(Integer year) {
        this.year = year;
    }
    public String getFuel() {
        return fuel;
    }
    public void setFuel(String fuel) {
        this.fuel = fuel;
    }
    public String getPlate() {
        return plate;
    }
    public void setPlate(String plate) {
        this.plate = plate;
    }
    public Integer getMileage() {
        return mileage;
    }
    public void setMileage(Integer mileage) {
        this.mileage = mileage;
    }
    public Double getPrice() {
        return price;
    }
    public void setPrice(Double price) {
        this.price = price;
    }
    public Double getEngine() {
        return engine;
    }
    public void setEngine(Double engine) {
        this.engine = engine;
    }
    public String getNotes() {
        return notes;
    }
    public void setNotes(String notes) {
        this.notes = notes;
    }
    public String getRenavam() {
        return renavam;
    }
    public void setRenavam(String renavam) {
        this.renavam = renavam;
    }
    public int getAccessoriesId() {
        return accessoriesId;
    }
    public boolean[] getAccessoriesValue() {
        return acessoriesValue;
    }
    public void setAccessories(int id, boolean[] value){
        this.accessoriesId = id;
        this.acessoriesValue = value;
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
    }// </editor-fold>  
}
