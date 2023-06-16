package DAO;

// <editor-fold defaultstate="collapsed" desc="Imports"> 
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
// </editor-fold>
import Model.Client;
/**
 *
 * @author eduardo
 */
public class ClientDAL extends MySQL {
     
    // <editor-fold defaultstate="collapsed" desc="Constructor">   
    public ClientDAL() {
        if (getConn() == null)
            CreateConn();
    }// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Insert">   
    // Método para cadastrar um Client
    public boolean cadastraUsuario(Client client) {

        String statementString = "INSERT INTO client (client_cpf, client_name, client_rg, client_mail) VALUES (?, ?, ?, ?)";

        try {
            try (PreparedStatement sql = getConn().prepareStatement(statementString)) {
                sql.setString(1, client.getCpf());
                sql.setString(2, client.getName());
                sql.setString(3, client.getRg());
                sql.setString(4, client.getEmail());
                
                sql.execute();
            }
            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }// </editor-fold> 

    // <editor-fold defaultstate="collapsed" desc="Select">   
    // Método para retornar um Client pelo ID
    public Client getClient(int id) {
        
        String statementString = "SELECT * FROM client WHERE client_id = ?";
        Client client = null;

        try {
            ResultSet rs;
            try (PreparedStatement sql = getConn().prepareStatement(statementString)) {
                sql.setInt(1, id);
                rs = sql.executeQuery();
                if (rs.next()) {
                    client = new Client(rs.getString("client_cpf"), rs.getString("client_name"), rs.getString("client_rg"), rs.getString("client_mail"), rs.getBoolean("enable"));
                    client.setId(rs.getInt("client_id"));
                }
            }
            rs.close();
            return client;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Método para retornar um Client com uma consulta com Where
    @Override
    public Object getWithWhere(String field, String search) {

        String statementString = "SELECT * FROM client WHERE " + field + " = ?";
        Client client = null;

        try {
            ResultSet rs;
            try (PreparedStatement sql = getConn().prepareStatement(statementString)) {
                sql.setString(1, search);
                rs = sql.executeQuery();
                if (rs.next()) {
                    client = new Client(rs.getString("client_cpf"), rs.getString("client_name"), rs.getString("client_rg"), rs.getString("client_mail"), rs.getBoolean("enable"));
                    client.setId(rs.getInt("client_id"));
                }
            }
            rs.close();
            return client;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Método para retornar todos os Clientes como um arraylist
    public ArrayList<Client> getList() {

        String statementString = "SELECT * FROM client ORDER BY client_id";
        ArrayList<Client> lista = new ArrayList<>();

        try {
            ResultSet rs;
            try (PreparedStatement sql = getConn().prepareStatement(statementString)) {
                rs = sql.executeQuery();
                while (rs.next()) {
                    Client client = this.getClient(rs.getInt("client_id"));
                    lista.add(client);
                }
            }
            rs.close();
            return lista;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }// </editor-fold> 

    // <editor-fold defaultstate="collapsed" desc="Update">   
    // Método para alterar os dados de um Client já cadastrado
    public boolean alteraClient(Client client) {
        String statementString = "UPDATE client SET client_cpf = ?, client_name = ?, client_rg = ?, client_mail = ? WHERE client_id = ?";

        try {
            try (PreparedStatement sql = getConn().prepareStatement(statementString)) {
                sql.setString(1, client.getCpf());
                sql.setString(2, client.getName());
                sql.setString(3, client.getRg());
                sql.setString(4, client.getEmail());
                sql.setInt(5, client.getId());
                
                sql.execute();
            }
            return true;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }// </editor-fold> 

    // <editor-fold defaultstate="collapsed" desc="Delete">   
    // Método para excluir um Client
    public boolean excluiClient(Client client) {
        String statementString;
        if(client.isEnable())
            statementString = "UPDATE client SET enable = false WHERE client_id = ?";
        else
            statementString = "UPDATE client SET enable = true WHERE client_id = ?";
        try {
            try (PreparedStatement sql = getConn().prepareStatement(statementString)) {
                sql.setInt(1, client.getId());
                
                sql.execute();
            }
            return true;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }// </editor-fold> 

}
