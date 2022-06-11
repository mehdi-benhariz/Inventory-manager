package com.example.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.example.MyConnection;
import com.example.DTO.ProductDTO;
import com.example.Models.Product;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ProductDAO {
    // get all products
    public static ObservableList<ProductDTO> getAll() {
        ObservableList<ProductDTO> res = FXCollections.observableArrayList();

        String req = "SELECT * FROM products";
        try {
            Connection cnx = MyConnection.conn;
            PreparedStatement pstmt = cnx.prepareStatement(req);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Product p = new Product(rs.getString("ref"), rs.getString("name"), rs.getInt("quantity"),
                        rs.getFloat("price"), rs.getByte("TVA"), rs.getByte("marge"));
                res.add(new ProductDTO(p));
            }
            System.out.println(res);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            // TODO: handle exception
        }
        return res;
    }

    public static ObservableList<ProductDTO> getStock() {
        ObservableList<ProductDTO> res = FXCollections.observableArrayList();

        String req = "SELECT * FROM products where quantity > 0";
        try {
            Connection cnx = MyConnection.conn;
            PreparedStatement pstmt = cnx.prepareStatement(req);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Product p = new Product(rs.getString("ref"), rs.getString("name"), rs.getInt("quantity"),
                        rs.getFloat("price"), rs.getByte("TVA"), rs.getByte("marge"), rs.getString("family"));
                res.add(new ProductDTO(p));
            }
            System.out.println(res);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            // TODO: handle exception
        }
        return res;
    }

    public static ObservableList<ProductDTO> search(String query) {
        ObservableList<ProductDTO> res = FXCollections.observableArrayList();
        String req = "SELECT * FROM products WHERE ref LIKE ? OR name LIKE ?";
        try {
            Connection cnx = MyConnection.conn;
            PreparedStatement pstmt = cnx.prepareStatement(req);
            pstmt.setString(1, "%" + query + "%");
            pstmt.setString(2, "%" + query + "%");
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Product p = new Product(rs.getString("ref"), rs.getString("name"),
                        rs.getInt("quantity"),
                        rs.getFloat("price"), rs.getByte("TVA"), rs.getByte("marge"), rs.getString("family"));
                res.add(new ProductDTO(p));
            }
            System.out.println(res);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return res;
    }

    public Product getProduct() {
        return null;
    }

    public static Boolean removeProduct(String ref) {
        Boolean res = false;
        String req = "DELETE FROM products WHERE ref = ?";
        try {
            Connection cnx = MyConnection.conn;
            PreparedStatement pstmt = cnx.prepareStatement(req);
            pstmt.setString(1, ref);
            pstmt.executeUpdate();
            res = true;
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return res;
    }

    public static Boolean updateProduct(Product currentProduct, String ref) {
        Boolean res = false;
        // set family

        String req = "UPDATE products SET ref=?, name = ?, quantity = ?,family=?, price = ?, TVA = ?, marge = ? WHERE ref = ?";
        try {
            Connection cnx = MyConnection.conn;
            PreparedStatement pstmt = cnx.prepareStatement(req);
            pstmt.setString(1, currentProduct.getRef());
            pstmt.setString(2, currentProduct.getName());
            pstmt.setInt(3, currentProduct.getQuantity());
            pstmt.setString(4, currentProduct.getFamily());
            pstmt.setFloat(5, currentProduct.getPrice());
            pstmt.setByte(6, currentProduct.getTVA());
            pstmt.setByte(7, currentProduct.getMarge());
            pstmt.setString(8, ref);
            pstmt.executeUpdate();
            res = true;
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return res;
    }

    public static void updateQuantity(String ref, int quantity) {
        String req = "UPDATE products SET quantity = ? WHERE ref = ?";
        try {
            Connection cnx = MyConnection.conn;
            PreparedStatement pstmt = cnx.prepareStatement(req);
            pstmt.setInt(1, quantity);
            pstmt.setString(2, ref);
            pstmt.executeUpdate();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    public static Boolean addProduct(Product p) {
        Boolean res = false;
        String req = "INSERT INTO products (ref, name, quantity, price, TVA, marge,family) VALUES (?, ?, ?, ?, ?, ?,?)";
        try {
            Connection cnx = MyConnection.conn;
            PreparedStatement pstmt = cnx.prepareStatement(req);
            pstmt.setString(1, p.getRef());
            pstmt.setString(2, p.getName());
            pstmt.setInt(3, p.getQuantity());
            pstmt.setFloat(4, p.getPrice());
            pstmt.setByte(5, p.getTVA());
            pstmt.setByte(6, p.getMarge());
            pstmt.setString(7, p.getFamily());
            pstmt.executeUpdate();
            res = true;
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return res;
    }

}
