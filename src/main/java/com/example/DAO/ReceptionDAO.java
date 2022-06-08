package com.example.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.example.MyConnection;
import com.example.Models.Reception;
import com.example.Models.ReceptionLine;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ReceptionDAO {
    // get all ref for products
    public static ObservableList<String> getAllRef() {
        ObservableList<String> res = FXCollections.observableArrayList();

        String req = "SELECT ref FROM products";
        try {
            Connection cnx = MyConnection.conn;
            PreparedStatement pstmt = cnx.prepareStatement(req);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next())
                res.add(rs.getString(1));

            System.out.println(res);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return res;
    }

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
}
