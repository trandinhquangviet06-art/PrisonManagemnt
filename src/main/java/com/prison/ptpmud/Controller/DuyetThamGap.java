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
import javafx.scene.Scene;
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
    private TableColumn<DonDuyetThamGap, String> lblQuanHe;
    private ObservableList<DonDuyetThamGap> DanhSachDuyet = FXCollections.observableArrayList();

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
        int MaYC;
        String MaPN;
        String QuanHe;
        String TenPN;
        String TenNT;
        String NgayTham;
        String CaTham;
        try {
            String sql = "select yc.MaYC, nt.MaPhamNhan, nt.QuanHe, pn.HoTen as TenPhamNhan, nt.HoTen as TenNguoiThan, yc.NgayTham, yc.CaTham "
                       + "from YeuCauThamGap yc "
                       + "join NguoiThan nt on yc.CCCDNguoiThan = nt.CCCD "
                       + "join PhamNhan pn on nt.MaPhamNhan = pn.MaPhamNhan "
                       + "where yc.TrangThai = N'Chờ Duyệt'";
            Connection conn = DBConnection.getConnection();
            PreparedStatement pst = conn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                MaYC = rs.getInt("MaYC");
                MaPN = rs.getString("MaPhamNhan");
                QuanHe = rs.getString("QuanHe");
                TenPN = rs.getString("TenPhamNhan");
                TenNT = rs.getString("TenNguoiThan");
                NgayTham = rs.getDate("NgayTham").toLocalDate().format(dtf);
                CaTham = rs.getString("CaTham");
                DanhSachDuyet.add(new DonDuyetThamGap(MaYC, TenNT, QuanHe, MaPN, TenPN, NgayTham, CaTham));
            }
        } catch (SQLException ex) {
            System.getLogger(DuyetThamGap.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }

    }

    @FXML
    public void chapNhanThamGap(javafx.event.ActionEvent event) throws SQLException {
        DonDuyetThamGap donDuocChon = BangThamGap.getSelectionModel().getSelectedItem();
        if (donDuocChon == null) {
            Alert al = new Alert(Alert.AlertType.WARNING);
            al.setTitle("thong bao");
            al.setContentText("hay chon 1 don");
            al.showAndWait();
            return;
        }
        String TrangThai = "Đã Duyệt"; 
        int MaYC = donDuocChon.getMaYC();
        String sql = "Update YeuCauThamGap set TrangThai=?, LyDoPhanHoi=? where MaYC=?";
        Connection conn = DBConnection.getConnection();
        PreparedStatement pst = conn.prepareStatement(sql);
        pst.setString(1, TrangThai);
        pst.setString(2, "Đã đồng ý");
        pst.setInt(3, MaYC);
        int KetQua = pst.executeUpdate();
        DanhSachDuyet.clear();
        loadDanhSach();

    }

    @FXML
    public void tuChoiThamGap(javafx.event.ActionEvent event) throws SQLException {
        DonDuyetThamGap donDuocChon = BangThamGap.getSelectionModel().getSelectedItem();
        if (donDuocChon == null) {
            Alert al = new Alert(Alert.AlertType.WARNING);
            al.setTitle("thong bao");
            al.setContentText("hay chon 1 don");
            al.showAndWait();
            return;
        }
        String MaPN = donDuocChon.getMaPN();
        int MaYC = donDuocChon.getMaYC();
        javafx.scene.control.TextInputDialog dialog = new javafx.scene.control.TextInputDialog();
        dialog.setTitle("Ly Do Tu Choi");
        dialog.setHeaderText("Dang tu choi don tham cua PN:" + MaPN);
        dialog.setContentText("Vui long nhap ly do:");
        java.util.Optional<String> reason = dialog.showAndWait();
        if (reason.isPresent()) {
            String lyDo = reason.get();
            String sql_tuchoi = "update YeuCauThamGap set TrangThai=?,LyDoPhanHoi=? where MaYC=?";
            Connection conn = DBConnection.getConnection();
            PreparedStatement pst = conn.prepareStatement(sql_tuchoi);
            pst.setString(1, "Từ chối");
            pst.setString(2, lyDo);
            pst.setInt(3, MaYC);
            int ketqua= pst.executeUpdate();
            DanhSachDuyet.clear();
            loadDanhSach();
            

        }

    }

}
