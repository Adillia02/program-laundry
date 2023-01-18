package GUI;


import java.sql.Connection;
import java.sql.DriverManager;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Adel
 */
public class Koneksi {
     private static Connection koneksi;
    
    public static Connection getKoneksi(){
        if (koneksi == null) {
            try {
                String url = "jdbc:mysql://localhost/laundry";
                String username = "root";
                String password = "";
                DriverManager.registerDriver(new com.mysql.jdbc.Driver());
                koneksi = DriverManager.getConnection(url, username, password);
                 System.out.println("Koneksi Berhasil");
            } catch (Exception e) {
                System.out.println(e);
            }
        }
        return koneksi;
    }
}
