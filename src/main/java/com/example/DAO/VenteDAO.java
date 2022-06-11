package com.example.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.example.MyConnection;
import com.example.Models.Vente;
import com.example.Models.VenteLine;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class VenteDAO {

    public static ObservableList<Vente> getAll() {
        ObservableList<Vente> res = FXCollections.observableArrayList();
        String req = "SELECT * FROM ventes";
        try {
            Connection cnx = MyConnection.conn;
            PreparedStatement pstmt = cnx.prepareStatement(req);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next())
                res.add(new Vente(rs.getString(1), rs.getDate(2)));

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return res;
    }

    public static Boolean addVente(Vente v) {
        Boolean res = false;
        String req = "INSERT INTO ventes (ref) VALUES (?)";
        try {
            Connection cnx = MyConnection.conn;
            PreparedStatement pstmt = cnx.prepareStatement(req);
            pstmt.setString(1, v.getRef());
            pstmt.executeUpdate();
            res = true;
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

        return res;
    }

    public static void AddVenteProduct(String ref, VenteLine line) {
        String req = "INSERT INTO ventes_products (ref_product,ref_vente, quantity) VALUES (?, ?, ?)";
        try {
            Connection cnx = MyConnection.conn;
            PreparedStatement pstmt = cnx.prepareStatement(req);
            pstmt.setString(1, line.getRef().getValue());
            pstmt.setString(2, ref);
            pstmt.setInt(3,
                    line.getQuantity().getText().equals("") ? 0 : Integer.parseInt(line.getQuantity().getText()));
            pstmt.executeUpdate();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    public static ObservableList<Vente> search(String query) {
        ObservableList<Vente> res = FXCollections.observableArrayList();
        String req = "SELECT * FROM ventes WHERE ref LIKE ? ";
        try {
            Connection cnx = MyConnection.conn;
            PreparedStatement pstmt = cnx.prepareStatement(req);
            pstmt.setString(1, "%" + query + "%");
            ResultSet rs = pstmt.executeQuery();
            while (rs.next())
                res.add(new Vente(rs.getString(1), rs.getDate(2)));

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

}
