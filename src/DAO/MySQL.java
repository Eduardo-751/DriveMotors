package DAO;

// <editor-fold defaultstate="collapsed" desc="Imports"> 
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
// </editor-fold>
public class MySQL {

    private static Connection conn = null;
    private final static String servidor = "localhost:3306";
    private final static String nomeDoBanco = "DriveMotors";
    private final static String usuario = "root";
    private final static String senha = "Edu@751463";
    
    // <editor-fold defaultstate="collapsed" desc="Constructor"> 
    public static Connection getConn() {
        return conn;
    }
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Start and Close Connection"> 
    public static void CreateConn() {
        try {
            conn = DriverManager.getConnection("jdbc:mysql://" + servidor + "/" + nomeDoBanco, usuario, senha);
            if (conn != null) {
                System.out.println("Conexão efetuada com sucesso! " + "ID: " + conn);
            }

        } catch (SQLException e) {
            System.out.println("Conexão não realizada - ERRO: " + e.getMessage());
        }
    }

    public boolean CloseConn() {
        try {
            conn.close();
            return true;
        } catch (SQLException e) {
            System.out.println("Erro ao fechar conexao " + e.getMessage());
            return false;
        }
    }
    // </editor-fold>
    
    public Object getWithWhere(String field, String result) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    
}
