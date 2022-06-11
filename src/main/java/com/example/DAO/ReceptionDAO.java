package com.example.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.example.MyConnection;
import com.example.Models.Reception;
import com.example.Models.ReceptionLine;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;

public class ReceptionDAO {
    // get all ref for products

    public static Boolean addReception(Reception reception) {
        Boolean res = false;
        String req = "INSERT INTO receptions (ref) VALUES (?)";
        try {
            Connection cnx = MyConnection.conn;
            PreparedStatement pstmt = cnx.prepareStatement(req);
            pstmt.setString(1, reception.getRef());
            // pstmt.setDate(2, reception.getDate());
            pstmt.executeUpdate();
            res = true;
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return res;

    }

    public static boolean addReceptionProduct(String refRecep, ReceptionLine line) {
        Boolean res = false;
        String req = "INSERT INTO receptions_products (ref_product,ref_reception, quantity) VALUES (?, ?, ?)";
        try {
            Connection cnx = MyConnection.conn;
            PreparedStatement pstmt = cnx.prepareStatement(req);
            pstmt.setString(1, line.getRef().getValue());
            pstmt.setString(2, refRecep);
            pstmt.setInt(3,
                    line.getQuantity().getText().equals("") ? 0 : Integer.parseInt(line.getQuantity().getText()));
            // pstmt.setInt(3, line.getPrice().getText().equals("") ? 0 :
            // Integer.parseInt(line.getPrice().getText()));
            pstmt.executeUpdate();
            res = true;
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return res;
    }

    public static ObservableList<Reception> getAll() {
        ObservableList<Reception> res = FXCollections.observableArrayList();
        String req = "SELECT * FROM receptions";
        try {
            Connection cnx = MyConnection.conn;
            PreparedStatement pstmt = cnx.prepareStatement(req);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Reception r = new Reception(rs.getString(1), rs.getDate(2));
                res.add(r);
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return res;
    }

    public static ObservableList<Reception> search(String query) {
        ObservableList<Reception> res = FXCollections.observableArrayList();
        String req = "SELECT * FROM receptions WHERE ref LIKE ?";
        try {
            Connection cnx = MyConnection.conn;
            PreparedStatement pstmt = cnx.prepareStatement(req);
            pstmt.setString(1, "%" + query + "%");
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Reception r = new Reception(rs.getString(1), rs.getDate(2));
                res.add(r);
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return res;
    }

    public static ObservableList<ReceptionLine> getLinesByRef(String ref) {
        ObservableList<ReceptionLine> res = FXCollections.observableArrayList();
        String req = "SELECT * FROM receptions_products WHERE ref_reception = ?";
        try {
            Connection cnx = MyConnection.conn;
            PreparedStatement pstmt = cnx.prepareStatement(req);
            pstmt.setString(1, ref);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                ReceptionLine line = new ReceptionLine(rs.getString(1), rs.getInt(2), rs.getFloat(3));
                res.add(line);
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return res;
    }

    public static void updateRef(String oldRef, String text) {
        String req = "UPDATE receptions SET ref = ? WHERE ref = ?";
        try {
            Connection cnx = MyConnection.conn;
            PreparedStatement pstmt = cnx.prepareStatement(req);
            pstmt.setString(1, text);
            pstmt.setString(2, oldRef);
            pstmt.executeUpdate();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    public static void updateLine(String ref_reception, String ref_product, ReceptionLine line) {
        String req = "UPDATE receptions_products SET ref_product = ?, quantity = ? WHERE ref_reception = ? AND ref_product = ?";
        try {
            Connection cnx = MyConnection.conn;
            PreparedStatement pstmt = cnx.prepareStatement(req);
            pstmt.setString(1, line.getRef().getValue());
            pstmt.setInt(2,
                    Integer.parseInt(line.getQuantity().getText()));
            pstmt.setString(3, ref_reception);
            pstmt.setString(4, ref_product);
            pstmt.executeUpdate();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

    }
}
