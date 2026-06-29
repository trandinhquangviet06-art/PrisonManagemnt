package com.prison.ptpmud.Controller;

import DAO.TaiKhoanDao;
import database.DBConnection;
import com.prison.ptpmud.Main.navigator;

import java.net.URL;
import java.util.ResourceBundle;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class controlLoginUI implements Initializable {

    @FXML
    private TextField accountTXT;
    @FXML
    private PasswordField passTXT;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Hàm khởi tạo mặc định
    }

    @FXML
    public void Login(ActionEvent actionEvent) {
        String user = accountTXT.getText();
        String pass = passTXT.getText();
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        
        try {
            conn = DBConnection.getConnection();
            
            // Nếu không kết nối được DB thì báo lỗi ngay
            if (conn == null) {
                Alert al = new Alert(Alert.AlertType.ERROR);
                al.setTitle("Database Error");
                al.setContentText("Không thể kết nối đến Cơ sở dữ liệu! Hãy kiểm tra lại Server DB.");
                al.showAndWait();
                return;
            }

            TaiKhoanDao dao = new TaiKhoanDao();
            if (dao.KiemTraTaiKhoan(user, pass, "NguoiThan")) {
                navigator.chuyenManHinh(actionEvent, "/view/UI_NguoiThan.fxml", "TAI KHOAN NGUOI THAN", "/decor/style_nguoithan.css");
                
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Login Info");
                alert.setContentText("Login successful");
                alert.showAndWait();
            } 
            else if (dao.KiemTraTaiKhoan(user, pass, "admin")) {
                navigator.chuyenManHinh(actionEvent, "/view/admin_dashboard.fxml", "TAI KHOAN ADMIN", "/decor/admin_dashboard.css");
            } 
            else if (dao.KiemTraTaiKhoan(user, pass, "QuanNguc")) {
                navigator.chuyenManHinh(actionEvent, "/view/guard_dashboard.fxml", "Tai Khoan Nhan Vien", "/decor/guard_dash.css");

                Alert al = new Alert(Alert.AlertType.INFORMATION);
                al.setTitle("Login Info");
                al.setContentText("Login Successful");
                al.showAndWait();
            } 
            else {
                Alert al = new Alert(Alert.AlertType.ERROR);
                al.setTitle("Login Failed");
                al.setContentText("Sai tên đăng nhập hoặc mật khẩu!");
                al.showAndWait();
            }
            
        } catch (Exception e) { 
            // Bắt trọn vẹn mọi lỗi hệ thống
            e.printStackTrace();
            Alert al = new Alert(Alert.AlertType.ERROR);
            al.setTitle("System Error");
            al.setContentText("Lỗi hệ thống: " + e.getMessage() + "\nHãy kiểm tra log ở khung Output.");
            al.showAndWait();
        } finally {
            try {
                if (rs != null) rs.close();
                if (pst != null) pst.close();
                if (conn != null) conn.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}