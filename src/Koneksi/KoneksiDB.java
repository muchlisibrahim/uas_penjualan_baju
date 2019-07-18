/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Koneksi;

/**
 *
 * @author SHIFT Comp
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class KoneksiDB {
     //deklarasi variabel KoneksiDB bernama conn
    private static Connection conn;
    
    //method KoneksiDB bernama getKoneksi()
    public static Connection getKoneksi(){
        //isian untuk server
        String  host = "jdbc:mysql://localhost/penjualan_baju",
                user = "root",
                pass = "";
        
        //ujilah KoneksiDB dengan pernyataan try catch
        try{
            conn = (Connection) DriverManager.getConnection(host, user, pass);
        }catch(SQLException err){
            JOptionPane.showMessageDialog(null, err.getMessage());
        }
        return conn;
    }
    public Connection con;
}
