package Main;

import DAO.MySQL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;

public class ComboBoxDataInsert {

    private Connection conexao = null;

    public ComboBoxDataInsert() {
        if (MySQL.getConn() == null) {
            MySQL.CreateConn();
        }
        this.conexao = MySQL.getConn();
    }
    
    public void setStatus(ComboBox cb, String table, String collum) {
        String statementString = "SELECT DISTINCT " + collum + " FROM " + table + " ORDER BY " + collum;
        ObservableList<String> observableList = FXCollections.observableArrayList();
        try {
            ResultSet rs;
            try (PreparedStatement sql = conexao.prepareStatement(statementString)) {
                rs = sql.executeQuery();
                while (rs.next()) {
                    observableList.add(rs.getString(1).toUpperCase());
                }
            }
            rs.close();
            cb.setItems(observableList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void setStatusWhiteWhere(ComboBox cb, String table, String field, String search , String collum) {
        String statementString = "SELECT DISTINCT " + collum + " FROM " + table + " Inner Join brand On model.brand_id = brand.brand_id  Where " + field + " = ?";
        ObservableList<String> observableList = FXCollections.observableArrayList();
        try {
            ResultSet rs;
            try (PreparedStatement sql = conexao.prepareStatement(statementString)) {
                sql.setString(1, search);
                rs = sql.executeQuery();
                while (rs.next()) {
                    observableList.add(rs.getString(1).toUpperCase());
                }
            }
            rs.close();
            cb.setItems(observableList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
