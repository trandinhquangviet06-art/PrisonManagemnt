/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.prison.ptpmud.Controller;
import com.prison.ptpmud.Model.Prisoner;
import DAO.PrisonerDao; 
import database.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.sql.SQLException;
/**
 *
 * @author Admin
 */
public class AddPrisonerController {
   @FXML private TextField mapn;
    @FXML private TextField hoten;
    @FXML private TextField ngaysinh;
    @FXML private TextField toidanh;
    @FXML private TextField khugiamgiu;
    @FXML private TextField trangthai;

    private PrisonerDao prisonerDao = new PrisonerDao();
    private boolean isSaved = false;

   
    @FXML
    private void luu() {
       
        String ma = mapn.getText().trim();
        String hoTen = hoten.getText().trim();
        String ngaySinh = ngaysinh.getText().trim();
        String toiDanh = toidanh.getText().trim();
        String khuGiam = khugiamgiu.getText().trim();
        String trangThai =trangthai.getText().trim();

        
        if (ma.isEmpty() || hoTen.isEmpty()) {
            hienThongBao("Lỗi nhập liệu", "Mã phạm nhân và Họ tên không được để trống!", Alert.AlertType.ERROR);
            return;
        }

       
        Prisoner p = new Prisoner(ma, hoTen, ngaySinh, toiDanh, khuGiam, trangThai, "");

       
             try {
        Connection conn = DBConnection.getConnection();
        System.out.println(">>> Conn: " + conn);

        String sql = "INSERT INTO PhamNhan (MaPhamNhan, HoTen, NgaySinh, ToiDanh, KhuGiamGiu, TrangThai) VALUES (?, ?, ?, ?, ?, ?)";
        PreparedStatement pst = conn.prepareStatement(sql);
        pst.setString(1, ma);
        pst.setString(2, hoTen);
        pst.setString(3, ngaySinh);
        pst.setString(4, toiDanh);
        pst.setString(5, khuGiam);
        pst.setString(6, trangThai);

        int result = pst.executeUpdate();
        if (result > 0) {
            hienThongBao("Thành công", "Đã thêm mới phạm nhân!", Alert.AlertType.INFORMATION);
            isSaved = true;
            dongCuaSo();
        } else {
            hienThongBao("Thất bại", "Mã phạm nhân đã tồn tại, vui lòng kiểm tra lại!", Alert.AlertType.WARNING);
        }

    } catch (SQLException e) {
        e.printStackTrace();
        System.out.println(">>> SQL Error: " + e.getMessage());
        hienThongBao("Lỗi hệ thống", "Không thể kết nối đến Cơ sở dữ liệu!", Alert.AlertType.ERROR);
    }
}
   
    @FXML
    private void huy() {
        dongCuaSo(); 
    }

    
    private void dongCuaSo() {
        Stage stage = (Stage) mapn.getScene().getWindow();
        stage.close();
    }

    
    public boolean isSaved() {
        return isSaved;
    }

    
    private void hienThongBao(String tieuDe, String noiDung, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(tieuDe);
        alert.setHeaderText(null);
        alert.setContentText(noiDung);
        alert.showAndWait();
    }
    }

