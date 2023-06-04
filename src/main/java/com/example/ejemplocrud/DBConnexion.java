package com.example.ejemplocrud;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnexion {

    public static Connection getCon(){
        Connection conn;
        try {
            // conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/library","root","2703");
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/horariosprofesores";
            String username = "root";
            String password = "";

            conn = DriverManager.getConnection(url, username, password);
            return conn;
        }
        catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
