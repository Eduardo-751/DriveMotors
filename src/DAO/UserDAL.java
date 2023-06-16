package DAO;

// <editor-fold defaultstate="collapsed" desc="Imports"> 
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
// </editor-fold>
import Model.User;
/**
 *
 * @author eduardo
 */
public class UserDAL extends MySQL{
    
    // <editor-fold defaultstate="collapsed" desc="Constructor">   
    public UserDAL() {
        if (MySQL.getConn() == null)
            MySQL.CreateConn();
    }// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Insert">   
    // Método para cadastrar um usuario
    public boolean InsertUsuario(User user) {
        String statementString = "INSERT INTO user (user_login, user_password, user_name) VALUES (?, ?, ?)";
        
        try {
            try (PreparedStatement sql = getConn().prepareStatement(statementString)) {
                sql.setString(1, user.getLogin());
                sql.setString(2, user.setCriptografia(user.getPassword()));
                sql.setString(3, user.getName());
                
                sql.execute();
            }
            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }// </editor-fold> 

    // <editor-fold defaultstate="collapsed" desc="Select">   
    // Método para retornar um usuario pelo ID
    public User getUsuario(int id) {

        String statementString = "SELECT * FROM user WHERE user_id = ?";
        User user = null;

        try {
            ResultSet rs;
            try (PreparedStatement sql = getConn().prepareStatement(statementString)) {
                sql.setInt(1, id);
                rs = sql.executeQuery();
                if (rs.next()) {
                    user = new User(rs.getString("user_login"), rs.getString("user_password"), rs.getString("user_name"), rs.getBoolean("enable"));
                    user.setId(rs.getInt("user_id"));
                }
            }
            rs.close();
            return user;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Método para retornar um usuario pelo login usado para verificar a senha na tela de Login
    @Override
    public Object getWithWhere(String field, String search) {

        String statementString = "SELECT * FROM user WHERE " + field + " = ?";
        User user = null;

        try {
            ResultSet rs;
            try (PreparedStatement sql = getConn().prepareStatement(statementString)) {
                sql.setString(1, search);
                rs = sql.executeQuery();
                if (rs.next()) {
                    user = new User(rs.getString("user_login"), rs.getString("user_password"), rs.getString("user_name"), rs.getBoolean("enable"));
                    user.setId(rs.getInt("user_id"));
                }
            }
            rs.close();
            return user;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Método para retornar todos os usuario como um arraylist
    public ArrayList<User> getList() {

        String statementString = "SELECT * FROM user ORDER BY user_id";
        ArrayList<User> lista = new ArrayList<>();

        try {
            ResultSet rs;
            try (PreparedStatement sql = getConn().prepareStatement(statementString)) {
                rs = sql.executeQuery();
                while (rs.next()) {
                    User user = this.getUsuario(rs.getInt("user_id"));
                    lista.add(user);
                }
            }
            rs.close();
            return lista;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }// </editor-fold> 

    // <editor-fold defaultstate="collapsed" desc="Update">   
    // Método para alterar os dados de um usuario já cadastrado
    public boolean UpdateUsuario(User user) {
        String statementString = "UPDATE user SET user_login = ?, user_password = ?, user_name = ? WHERE user_id = ?";

        try {
            try (PreparedStatement sql = getConn().prepareStatement(statementString)) {
                sql.setString(1, user.getLogin());
                sql.setString(2, user.getPassword());
                sql.setString(3, user.getName());
                sql.setInt(4, user.getId());
                
                sql.execute();
            }
            return true;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }// </editor-fold> 

    // <editor-fold defaultstate="collapsed" desc="Delete">   
    // Método para excluir um usuario
    public boolean excluiUsuario(User user) {
        String statementString;
        if(user.isEnable())
            statementString = "UPDATE user SET enable = false WHERE user_id = ?";
        else
            statementString = "UPDATE user SET enable = true WHERE user_id = ?";
        try {
            try (PreparedStatement sql = getConn().prepareStatement(statementString)) {
                sql.setInt(1, user.getId());
                
                sql.execute();
            }
            return true;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }// </editor-fold> 

}
