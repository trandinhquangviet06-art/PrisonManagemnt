/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.prison.ptpmud.Controller;
import com.prison.ptpmud.Model.Prisoner;
import DAO.PrisonerDao; 
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
           
            boolean thanhCong = prisonerDao.save(p);
            
            if (thanhCong) {
                hienThongBao("Thành công", "Đã thêm mới phạm nhân vào hệ thống!", Alert.AlertType.INFORMATION);
                isSaved = true; 
                dongCuaSo();   
            } else {
                hienThongBao("Thất bại", "Mã phạm nhân đã tồn tại, vui lòng kiểm tra lại!", Alert.AlertType.WARNING);
            }
        } catch (SQLException e) {
            e.printStackTrace();
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

