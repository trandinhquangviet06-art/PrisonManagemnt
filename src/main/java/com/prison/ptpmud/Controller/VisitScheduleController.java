/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.prison.ptpmud.Controller;

import database.DBConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 *
 * @author Admin
 */
public class VisitScheduleController implements Initializable{
    @FXML private TableView<DonDuyetThamGap> tbLichTham;
    @FXML private TableColumn<DonDuyetThamGap, String> colNguoiThan;
    @FXML private TableColumn<DonDuyetThamGap, String> colQuanHe;
    @FXML private TableColumn<DonDuyetThamGap, String> colMaPN;
    @FXML private TableColumn<DonDuyetThamGap, String> colTenPN;
    @FXML private TableColumn<DonDuyetThamGap, String> colNgayTham;
    @FXML private TableColumn<DonDuyetThamGap, String> colCaTham;
    @FXML private DatePicker dpNgay;
    @FXML private ComboBox<String> cbCa;
    @FXML private Label lblTongSo;
 
    private ObservableList<DonDuyetThamGap> danhSach = FXCollections.observableArrayList();
 
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        colNguoiThan.setCellValueFactory(new PropertyValueFactory<>("tenNT"));
        colQuanHe.setCellValueFactory(new PropertyValueFactory<>("quanHe"));
        colMaPN.setCellValueFactory(new PropertyValueFactory<>("maPN"));
        colTenPN.setCellValueFactory(new PropertyValueFactory<>("tenPN"));
        colNgayTham.setCellValueFactory(new PropertyValueFactory<>("ngayHen"));
        colCaTham.setCellValueFactory(new PropertyValueFactory<>("CaTham"));
 
        tbLichTham.setItems(danhSach);
 
       
        cbCa.getItems().addAll("Tất cả", "Ca Sáng", "Ca Chiều");
        cbCa.setValue("Tất cả");
 
      
        loadLichDaDuyet(null, null);
    }
 
    
    private void loadLichDaDuyet(String ngayLoc, String caLoc) {
        danhSach.clear();
 
        try {
            Connection conn = DBConnection.getConnection();
 
            StringBuilder sql = new StringBuilder(
                "SELECT yc.MaYC, nt.HoTen as TenNguoiThan, nt.QuanHe, nt.MaPhamNhan, pn.HoTen as TenPhamNhan, yc.NgayTham, yc.CaTham " +
                "FROM YeuCauThamGap yc " +
                "JOIN NguoiThan nt ON yc.CCCDNguoiThan = nt.CCCD " +
                "JOIN PhamNhan pn ON nt.MaPhamNhan = pn.MaPhamNhan " +
                "WHERE yc.TrangThai = N'Đã Duyệt'"
            );
 
         
            if (ngayLoc != null && !ngayLoc.isEmpty()) {
                sql.append(" AND CONVERT(varchar, NgayTham, 103) = ?");
            }
 
            
            if (caLoc != null && !caLoc.equals("Tất cả")) {

                   sql.append(" AND CaTham LIKE N'%' + ? + '%'");
            }
 
            sql.append(" ORDER BY NgayTham ASC");
 
            PreparedStatement pst = conn.prepareStatement(sql.toString());
 
            int index = 1;
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
 
            if (ngayLoc != null && !ngayLoc.isEmpty()) {
                pst.setString(index++, ngayLoc);
            }
            if (caLoc != null && !caLoc.equals("Tất cả")) {

                   pst.setString(index++, caLoc);
            }
 
            ResultSet rs = pst.executeQuery();
 
            while (rs.next()) {
                int maYC      = rs.getInt("MaYC");
                String tenNT   = rs.getString("TenNguoiThan");
                String quanHe  = rs.getString("QuanHe");
                String maPN    = rs.getString("MaPhamNhan");
                String tenPN   = rs.getString("TenPhamNhan");
                String ngay    = rs.getDate("NgayTham").toLocalDate().format(dtf);
                String ca      = rs.getString("CaTham");
 
                danhSach.add(new DonDuyetThamGap(maYC, tenNT, quanHe, maPN, tenPN, ngay, ca));
            }
 
            lblTongSo.setText(String.valueOf(danhSach.size()));
 
        } catch (SQLException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Lỗi");
            alert.setContentText("Không thể tải dữ liệu lịch thăm gặp!");
            alert.showAndWait();
        }
    }
 
    @FXML
    private void TimKiem() {
        String ngayLoc = null;
        String caLoc = cbCa.getValue();
 
        
        if (dpNgay.getValue() != null) {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            ngayLoc = dpNgay.getValue().format(dtf);
        }
 
        loadLichDaDuyet(ngayLoc, caLoc);
    }
 
    @FXML
    private void LamMoi() {
        dpNgay.setValue(null);
        cbCa.setValue("Tất cả");
        loadLichDaDuyet(null, null);
    }
}
