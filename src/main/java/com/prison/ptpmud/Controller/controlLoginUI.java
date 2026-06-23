/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.prison.ptpmud.Controller;

import DAO.TaiKhoanDao;
import com.prison.ptpmud.Main.navigator;
import database.DBConnection;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 *
 * @author LOQ
 */
public class controlLoginUI {

    @FXML
    private TextField accountTXT;
    @FXML
    private PasswordField passTXT;

    public void Login(ActionEvent actionEvent) throws SQLException, IOException {
        String user = accountTXT.getText();
        String pass = passTXT.getText();
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            conn = DBConnection.getConnection();
            if (conn != null) {
                TaiKhoanDao dao=new TaiKhoanDao();
                if (dao.KiemTraTaiKhoan(user, pass,"NguoiThan" )) {
                    navigator.chuyenManHinh(actionEvent, "/view/UI_NguoiThan.fxml", "TAI KHOAN NGUOI THAN","/decor/style_nguoithan.css");
                    
                   
                    
                    Alert alert=new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Login Info");
                    alert.setContentText("Login successfull");
                    alert.showAndWait();
                   
                    
                }
                else if(dao.KiemTraTaiKhoan(user, pass, "admin")){
                  navigator.chuyenManHinh(actionEvent, "nhap dia chi admin tai day", "TAI KHOAN ADMIN", "dia chi file css trang tri UI admin");
                  Alert al=new Alert(Alert.AlertType.INFORMATION);
                  al.setTitle("Login Info");
                  al.setContentText("Login Successfull");
                  al.showAndWait();
                }
                else if(dao.KiemTraTaiKhoan(user, pass, "QuanNguc")){
                    navigator.chuyenManHinh(actionEvent, "/view/guard_dashboard.fxml", "Tai Khoan Nhan Vien", "/decor/guard_dash.css");
                    Alert al=new Alert(Alert.AlertType.INFORMATION);
                    al.setTitle("Login Info");
                    al.setContentText("Login Successfull");
                    al.showAndWait();
                }
                else {
                    Alert al = new Alert(Alert.AlertType.ERROR);
                    al.setContentText("Login unsuccessfull");
                    al.showAndWait();
                }

            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (pst != null) {
                pst.close();
            }
            if (conn != null) {
                conn.close();
            }
        }

    }

}
