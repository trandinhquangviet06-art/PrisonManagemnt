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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class ViewVisitScheduleController implements Initializable {

    @FXML
    private TableView<DonDuyetThamGap> BangThamGap;
    @FXML
    private TableColumn<DonDuyetThamGap, Integer> lblMaYC;
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
    private ObservableList<DonDuyetThamGap> DanhSachLich = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        lblMaYC.setCellValueFactory(new PropertyValueFactory<>("MaYC"));
        lblNguoiThan.setCellValueFactory(new PropertyValueFactory<>("tenNT"));
        lblQuanHe.setCellValueFactory(new PropertyValueFactory<>("quanHe"));
        lblMaPhamNhan.setCellValueFactory(new PropertyValueFactory<>("maPN"));
        lblTenPhamNhan.setCellValueFactory(new PropertyValueFactory<>("tenPN"));
        lblNgayHen.setCellValueFactory(new PropertyValueFactory<>("ngayHen"));
        lblGioHen.setCellValueFactory(new PropertyValueFactory<>("CaTham"));
        BangThamGap.setItems(DanhSachLich);
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
                       + "where yc.TrangThai = N'Đã Duyệt'";
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
                DanhSachLich.add(new DonDuyetThamGap(MaYC, TenNT, QuanHe, MaPN, TenPN, NgayTham, CaTham));
            }
        } catch (SQLException ex) {
            System.getLogger(ViewVisitScheduleController.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
    }
}
