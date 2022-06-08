package com.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MyConnection {
    public static Connection conn = null;

    public MyConnection() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/glechler?allowPublicKeyRetrieval=TRUE&useSSL=FALSE";
        try {
            conn = DriverManager.getConnection(url, "root", "12mehdi34");
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e1) {
            e1.printStackTrace();
            // conn.close();
        }
    }
}
