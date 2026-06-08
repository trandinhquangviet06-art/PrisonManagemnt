/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package database;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author LOQ
 */
public class DBConnection {

    public static Connection getConnection() throws SQLException {
        String server = "localhost";
        String dbName = "QlyNguoiThan";
        String user = "sa";
        String pass = "vietvipaz0000";
        String url = "jdbc:sqlserver://" + server + ":1433;"
                + "databaseName=" + dbName
                + ";encrypt=true;trustServerCertificate=true;";
        return DriverManager.getConnection(url, user, pass);
    }
}
