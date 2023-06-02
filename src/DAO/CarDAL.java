package DAO;

// <editor-fold defaultstate="collapsed" desc="Imports"> 
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
// </editor-fold>
import Model.Car;
import Model.Brand;
import Model.Model;
/**
 *
 * @author eduardo
 */
public class CarDAL extends MySQL {
    
    // <editor-fold defaultstate="collapsed" desc="Constructor">   
    public CarDAL() {
        if (getConn() == null)
            CreateConn();
    }// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Insert">   
    // Método para Cadastrar um automovel
    public boolean InsertCar(Car car) {
        String statementString = "INSERT INTO car (car_renavam, plate_number, car_year, car_mileage, car_engine, car_price,"
                                                + "car_transmission, car_drivetrain, car_fuel, car_notes, model_id, color_id, accessories_id)"
                                                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, (SELECT MAX(accessories_id) FROM accessories))";

        try {
            try (PreparedStatement sql = getConn().prepareStatement(statementString)) {
                sql.setString(1, car.getRenavam());
                sql.setString(2, car.getPlate());
                sql.setInt(3, car.getYear());
                sql.setInt(4, car.getMileage());
                sql.setDouble(5, car.getEngine());
                sql.setDouble(6, car.getPrice());
                sql.setString(7, car.getTransmission());
                sql.setString(8, car.getDrivetrain());
                sql.setString(9, car.getFuel());
                sql.setString(10, car.getNotes());
                sql.setInt(11, car.getModel().getId());
                sql.setString(12, getColor("color_id", "color_name", car.getColor()));
                sql.execute();
            }
            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Select">   
    // Método para retornar um automovel pelo ID
    public Car getCar(int id) {

        String statementString = "SELECT * FROM car WHERE car_id = ?";
        Car car = null;

        try {
            ResultSet rs;
            try (PreparedStatement sql = getConn().prepareStatement(statementString)) {
                sql.setInt(1, id);
                rs = sql.executeQuery();
                if (rs.next()) {
                    car = new Car(rs.getInt("accessories_id"), getModel("model_id", rs.getString("model_id")), getColor("color_name", "color_id", 
                                  rs.getString("color_id")), rs.getString("car_transmission"), rs.getString("car_drivetrain"), rs.getInt("car_year"), 
                                  rs.getString("car_fuel"), rs.getString("plate_number"), rs.getString("car_renavam"), rs.getInt("car_mileage"),
                                  rs.getDouble("car_price"), rs.getDouble("car_engine"), rs.getString("car_notes"));
                    car.setId(rs.getInt("car_id"));
                }
            }
            rs.close();
            return car;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    
    // Método para retornar um automovel passando o atributo para busca
    @Override
    public Object getWithWhere(String field, String search) {

        String statementString = "SELECT * FROM car WHERE "+ field + " = ?";
        Car car = null;

        try {
            ResultSet rs;
            try (PreparedStatement sql = getConn().prepareStatement(statementString)) {
                sql.setString(1, search);
                rs = sql.executeQuery();
                if (rs.next()) {
                    car = new Car(rs.getInt("accessories_id"), getModel("model_id", rs.getString("model_id")), getColor("color_name", "color_id", 
                                  rs.getString("color_id")), rs.getString("car_transmission"), rs.getString("car_drivetrain"), rs.getInt("car_year"), 
                                  rs.getString("car_fuel"), rs.getString("plate_number"), rs.getString("car_renavam"), rs.getInt("car_mileage"),
                                  rs.getDouble("car_price"), rs.getDouble("car_engine"), rs.getString("car_notes"));
                    car.setId(rs.getInt("car_id"));
                }
            }
            rs.close();
            return car;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
   
    // Método para retornar todos os automovel como um arraylist
    public ArrayList<Car> getLista() {

        String statementString = "SELECT * FROM car ORDER BY car_id";
        ArrayList<Car> lista = new ArrayList<>();

        try {
            ResultSet rs;
            try (PreparedStatement sql = getConn().prepareStatement(statementString)) {
                rs = sql.executeQuery();
                while (rs.next()) {
                    Car car = this.getCar(rs.getInt("car_id"));
                    lista.add(car);
                }
            }
            rs.close();
            return lista;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Update">   
    // Método para alterar os dados de um automovel já cadastrado
    public boolean UpdateCar(Car car){

        String statementString = "UPDATE car SET car_renavam = ?, plate_number = ?, car_year = ?, car_mileage = ?, "
                                              + "car_engine = ?, car_price = ?, car_transmission = ?, car_drivetrain = ?, "
                                              + "car_fuel = ?, car_notes = ?, model_id = ?, color_id = ? WHERE car_id = ?";
        
            try (PreparedStatement sql = getConn().prepareStatement(statementString)) {
                sql.setString(1, car.getRenavam());
                sql.setString(2, car.getPlate());
                sql.setInt(3, car.getYear());
                sql.setInt(4, car.getMileage());
                sql.setDouble(5, car.getEngine());
                sql.setDouble(6, car.getPrice());
                sql.setString(7, car.getTransmission());
                sql.setString(8, car.getDrivetrain());
                sql.setString(9, car.getFuel());
                sql.setString(10, car.getNotes());
                sql.setInt(11, car.getModel().getId());
                sql.setString(12, getColor("color_id", "color_name", car.getColor()));
                sql.setInt(13, car.getId());
                
                sql.execute();
                return true;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
    }

    public boolean UpdateCarAccessories(Car car) {
        String statementString = "UPDATE accessories SET alarm = ?, abs_brake = ?, air_conditioning = ?, electric_windows = ?, "
                                                      + "power_steering = ?, alloy_wheels = ?, rear_view = ?, digital_radio = ?, "
                                                      + "keyless_start = ?, parking_assistance = ? WHERE accessories_id = ?";
        
        try (PreparedStatement sql = getConn().prepareStatement(statementString)) {
                sql.setBoolean(1, car.getAccessoriesValue()[0]);
                sql.setBoolean(2, car.getAccessoriesValue()[1]);
                sql.setBoolean(3, car.getAccessoriesValue()[2]);
                sql.setBoolean(4, car.getAccessoriesValue()[3]);
                sql.setBoolean(5, car.getAccessoriesValue()[4]);
                sql.setBoolean(6, car.getAccessoriesValue()[5]);
                sql.setBoolean(7, car.getAccessoriesValue()[6]);
                sql.setBoolean(8, car.getAccessoriesValue()[7]);
                sql.setBoolean(9, car.getAccessoriesValue()[8]);
                sql.setBoolean(10, car.getAccessoriesValue()[9]);
                sql.setInt(11, car.getAccessoriesId());
                
                sql.execute();
            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Delete">   
    // Método para excluir um automovel
    public boolean DeleteCar(Car auto) {
        String statementString = "DELETE FROM car WHERE car_id = ?";
        try {
            try (PreparedStatement sql = getConn().prepareStatement(statementString)) {
                sql.setInt(1, auto.getId());
                
                sql.execute();
            }
            return true;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }// </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Insert Brand, Model and Accessories">   
    // Método para Inserir uma nova Marca de Carro
    public boolean InsertBrand(String name ){
        String statementString = "INSERT INTO brand (brand_name) VALUES (?)";
        PreparedStatement sql;
        try {
            sql = getConn().prepareStatement(statementString);
            sql.setString(1, name);
            sql.execute();
        } catch (SQLException ex) {
            Logger.getLogger(CarDAL.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } 
        return true;
    }
    
    // Método para Inserir um novo modelo de Carro
    public boolean InsertModel(String name, String brand){
        String statementString = "INSERT INTO model (model_name, brand_id, accessories_id) VALUES (?, (SELECT brand_id FROM brand WHERE brand_name = ?), (SELECT MAX(accessories_id) FROM accessories))";
        PreparedStatement sql;
        try {
            sql = getConn().prepareStatement(statementString);
            sql.setString(1, name);
            sql.setString(2, brand);
            sql.execute();
        } catch (SQLException ex) {
            Logger.getLogger(CarDAL.class.getName()).log(Level.SEVERE, null, ex);
        } 
        return true;
    }
    
    // Método para Inserir um novo modelo de Carro
    public boolean InsertAccessories(boolean[] bool){
        String statementString = "INSERT INTO accessories (alarm, abs_brake, air_conditioning, electric_windows, power_steering, " +
                                                          "alloy_wheels, rear_view, digital_radio, keyless_start, parking_assistance) " + 
                                        "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement sql;
        try {
            sql = getConn().prepareStatement(statementString);
            sql.setBoolean(1, bool[0]);
            sql.setBoolean(2, bool[1]);
            sql.setBoolean(3, bool[2]);
            sql.setBoolean(4, bool[3]);
            sql.setBoolean(5, bool[4]);
            sql.setBoolean(6, bool[5]);
            sql.setBoolean(7, bool[6]);
            sql.setBoolean(8, bool[7]);
            sql.setBoolean(9, bool[8]);
            sql.setBoolean(10, bool[9]);
            sql.execute();
        } catch (SQLException ex) {
            Logger.getLogger(CarDAL.class.getName()).log(Level.SEVERE, null, ex);
        } 
        return true;
    }// </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Select Brand, Model and Accessories">   
    // Método para retornar a Marca do Carro
    public Brand getBrand(String field, String search){
        String statementString = "SELECT * From brand Where " + field + " = ?";
        Brand brand = null;
        try {
            ResultSet rs;
            try (PreparedStatement sql = getConn().prepareStatement(statementString)) {
                sql.setString(1, search);
                rs = sql.executeQuery();
                if (rs.next()) {
                    brand = new Brand(rs.getInt("brand_id"), rs.getString("brand_name"));
                }
            }
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(CarDAL.class.getName()).log(Level.SEVERE, null, ex);
        } 
        return brand;
    }
    //Método para retornar o Modelo do Carro
    public Model getModel(String field, String search){
        String statementString = "SELECT * From model Where " + field + " = ?";
        Model model = null;
        try {
            ResultSet rs;
            try (PreparedStatement sql = getConn().prepareStatement(statementString)) {
                sql.setString(1, search);
                rs = sql.executeQuery();
                if (rs.next()) {
                    model = new Model(rs.getInt("model_id"), rs.getInt("accessories_id"), rs.getString("model_name"), getBrand("brand_id", rs.getString("brand_id")));
                }
            } 
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(CarDAL.class.getName()).log(Level.SEVERE, null, ex);
        } 
        return model;
    }
    //Método para retornar os Acessorios do Carro
    public boolean[] getAccessories(String field, String search){
        String statementString = "SELECT * From accessories Where " + field + " = ?";
        boolean [] accessories = null;
        try {
            ResultSet rs;
            try (PreparedStatement sql = getConn().prepareStatement(statementString)) {
                sql.setString(1, search);
                rs = sql.executeQuery();
                if (rs.next()) {
                    accessories = new boolean[] {rs.getBoolean("alarm"), rs.getBoolean("abs_brake"), rs.getBoolean("air_conditioning"), rs.getBoolean("electric_windows"),
                                                 rs.getBoolean("power_steering"), rs.getBoolean("alloy_wheels"), rs.getBoolean("rear_view"), rs.getBoolean("digital_radio"),
                                                 rs.getBoolean("keyless_start"), rs.getBoolean("parking_assistance")};
                }
            }
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(CarDAL.class.getName()).log(Level.SEVERE, null, ex);
        } 
        return accessories;
    }// </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Select Color">   
    // Método para retornar a cor do Carro
    @SuppressWarnings("empty-statement")
    public String getColor(String target, String field, String search){
        String statementString = "SELECT * From exteriorcolor Where " + field + " = ?";;
        String brand = null;
        try {
            ResultSet rs;
            try (PreparedStatement sql = getConn().prepareStatement(statementString)) {
                sql.setString(1, search);
                rs = sql.executeQuery();
                if (rs.next()) {
                    brand = rs.getString(target);
                }
            }
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(CarDAL.class.getName()).log(Level.SEVERE, null, ex);
        } 
        return brand;
    }// </editor-fold>
}
