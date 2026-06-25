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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 *
 * @author LOQ
 */
public class DuyetThamGap implements Initializable {

    @FXML
    private TableView<DonDuyetThamGap> BangThamGap;
    @FXML
    private TableColumn<DonDuyetThamGap, String> lblNguoiThan;
    @FXML
    private TableColumn<DonDuyetThamGap, String> lblMaPhamNhan;
    @FXML
    private TableColumn<DonDuyetThamGap, String> lblTenPhamNhan;
    @FXML
    private TableColumn<DonDuyetThamGap, String> lblNgayHen;
    @FXML
    private TableColumn<DonDuyetThamGap, String> lblGioHen;
    @FXML
    private TableColumn<DonDuyetThamGap,String> lblQuanHe;
    private ObservableList<DonDuyetThamGap>DanhSachDuyet=FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        lblNguoiThan.setCellValueFactory(new PropertyValueFactory<>("tenNT"));
        lblQuanHe.setCellValueFactory(new PropertyValueFactory<>("quanHe"));
        lblMaPhamNhan.setCellValueFactory(new PropertyValueFactory<>("maPN"));
        lblTenPhamNhan.setCellValueFactory(new PropertyValueFactory<>("tenPN"));
        lblNgayHen.setCellValueFactory(new PropertyValueFactory<>("ngayHen"));
        lblGioHen.setCellValueFactory(new PropertyValueFactory<>("CaTham"));
        BangThamGap.setItems(DanhSachDuyet);
        loadDanhSach();
    }

    private void loadDanhSach() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String MaPN;
        String QuanHe;
        String TenPN;
        String TenNT;
        String NgayTham;
        String CaTham;
        try {
            String sql="select MaPhamNhan,QuanHe,TenPhamNhan,TenNguoiThan,NgayTham,CaTham from YeuCauThamGap";
            Connection conn=DBConnection.getConnection();
            PreparedStatement pst=conn.prepareStatement(sql);
            ResultSet rs=pst.executeQuery();
            while(rs.next()){
                MaPN=rs.getString("MaPhamNhan");
                QuanHe=rs.getString("QuanHe");
                TenPN=rs.getString("TenPhamNhan");
                TenNT=rs.getString("TenNguoiThan");
                NgayTham=rs.getDate("NgayTham").toLocalDate().format(dtf);
                CaTham=rs.getString("CaTham");
                DanhSachDuyet.add(new DonDuyetThamGap(TenNT,QuanHe,MaPN,TenPN,NgayTham,CaTham));
            }
        } catch (SQLException ex) {
            System.getLogger(DuyetThamGap.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
        
    }
    @FXML
    public void chapNhanThamGap(javafx.event.ActionEvent event) throws SQLException {
        DonDuyetThamGap donDuocChon= BangThamGap.getSelectionModel().getSelectedItem();
        if(donDuocChon ==null){
            Alert al=new Alert(Alert.AlertType.WARNING);
            al.setTitle("thong bao");
            al.setContentText("hay chon 1 don");
            al.showAndWait();
        }
        String TrangThai="Đã Duyệt";
        String MaPN=donDuocChon.getMaPN();
        String sql="Update YeuCauThamGap set TrangThai=? where MaPhamNhan=?";
        Connection conn=DBConnection.getConnection();
        PreparedStatement pst=conn.prepareStatement(sql);
        pst.setString(1, TrangThai);
        pst.setString(2, MaPN);
        ResultSet rs=pst.executeQuery();
        
        
    }
    @FXML
    public void tuChoiThamGap(javafx.event.ActionEvent event) {
        // Chỗ này để test trước cho giao diện chạy lên
        System.out.println("Đã bấm nút Từ chối thăm gặp!");
        
        // Gợi ý logic khi nào bạn code thật:
        // 1. Lấy thông tin đơn đang được chọn trong bảng
        // 2. (Tùy chọn) Hiện lên một ô nhỏ hỏi lý do từ chối
        // 3. Chạy câu lệnh SQL UPDATE trạng thái đơn thành 'Từ chối' kèm lý do
        // 4. Gọi lại loadDanhSach() để làm mới bảng
    }

}
