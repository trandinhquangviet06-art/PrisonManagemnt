/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.prison.ptpmud.Controller;

import com.prison.ptpmud.Main.navigator;
import com.prison.ptpmud.Main.userNT_session;
import com.sun.javafx.text.GlyphLayout;
import database.DBConnection;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 *
 * @author LOQ
 */
public class controller_nguoiThan implements Initializable{
    @FXML private Label txtTenNguoiThan,txtQuanHe, txtCCCD;
    @FXML private Label txtTenPhamNhan, txtMaTu,txtKhuGiam;
    @FXML private DatePicker dpNgayTham;
    @FXML private ComboBox<String> cbCaTham;
    @FXML private TextArea txtGhiChu;
    // phan khai bao table 
    @FXML private TableView<DonXinTham> BangYeuCau;
    @FXML private TableColumn<DonXinTham,String> colMaYC;
    @FXML private TableColumn<DonXinTham,String> colNgayXinTham;
    @FXML private TableColumn<DonXinTham,String> colCaTham;
    @FXML private TableColumn<DonXinTham,String> colNgayTao;
    @FXML private TableColumn<DonXinTham,String> colGhiChu;
    @FXML private TableColumn<DonXinTham,String> colTrangThai;
    private ObservableList<DonXinTham> danhSachDon=FXCollections.observableArrayList();
    private static final String cccd_NT=userNT_session.getCurrentCCCD();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cbCaTham.setItems(FXCollections.observableArrayList("Ca Sáng (08:00 - 11:00)", "Ca Chiều (14:00 - 17:00)"));
        colMaYC.setCellValueFactory(new PropertyValueFactory<>("MaYC"));
        colNgayXinTham.setCellValueFactory(new PropertyValueFactory<>("NgayXinTham"));
        colCaTham.setCellValueFactory(new PropertyValueFactory<>("CaTham"));
        colNgayTao.setCellValueFactory(new PropertyValueFactory<>("ngayTao"));
        colTrangThai.setCellValueFactory(new PropertyValueFactory<>("TrangThai"));
        colGhiChu.setCellValueFactory(new PropertyValueFactory<>("LyDoPhanHoi"));
        BangYeuCau.setItems(danhSachDon);
        loadThongTinHoSo();
        loadLichSuYeuCau();
        
        
        
    }

    private void loadThongTinHoSo() {
        String sql = "Select nt.HoTen as TenNT, nt.QuanHe, nt.CCCD, " +
                     "pn.HoTen as TenPN, pn.MaPhamNhan, pn.KhuGiamGiu " +
                     "from NguoiThan nt join PhamNhan pn on nt.MaPhamNhan=pn.MaPhamNhan " +
                     "where nt.CCCD=? ";
        
        
        try {
            Connection conn=DBConnection.getConnection();
            PreparedStatement pst=conn.prepareStatement(sql);
            pst.setString(1, cccd_NT);
            ResultSet rs=pst.executeQuery();
            if(rs.next()){
                txtTenPhamNhan.setText(rs.getString("TenPN"));


                txtMaTu.setText(rs.getString("KhuGiamGiu"));

                txtTenNguoiThan.setText(rs.getString("TenNT"));
                txtQuanHe.setText("Quan He: "+ rs.getString("QuanHe"));
                txtCCCD.setText("CCCD: "+ rs.getString("CCCD"));
                txtMaTu.setText("Ma tu nhan"+ rs.getString("MaPhamNhan"));
                txtKhuGiam.setText("khu giam giu"+ rs.getString("KhuGiamGiu"));
                
                
            }
            
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("Loi database khong tai duoc ho so" +ex.getMessage());
        }
        
        
    }
    @FXML
    public void guiYeuCau(ActionEvent event){
        LocalDate ngayTham=dpNgayTham.getValue();
        String caTham=cbCaTham.getValue();
        String ghiChu=txtGhiChu.getText();
        if(ngayTham==null || caTham==null){
            System.out.println("khong duoc de ngay tham hoac ca tham trong");
            return;
        }
        if(ngayTham.isBefore(LocalDate.now())){
            System.out.println("Ngay tham phai lon hon hoac bang hien tai");
            return;
        }
        String sql="insert into YeuCauThamGap (CCCDNguoiThan,NgayTham,CaTham,GhiChu,NgayTao,TrangThai) values(?,?,?,?,getDate(),N'Chờ Duyệt')";
        try {
            Connection conn=DBConnection.getConnection();
            PreparedStatement pst=conn.prepareStatement(sql);
            pst.setString(1, cccd_NT);
            pst.setDate(2, Date.valueOf(ngayTham));
            pst.setString(3, caTham);
            pst.setString(4, ghiChu);
            int affectedRow=pst.executeUpdate();
            if(affectedRow>0){
                hienThiThongBao("system infomation","Da gui thong bao den quan nguc");
                loadLichSuYeuCau();
                dpNgayTham.setValue(null);
                cbCaTham.setValue(null);
                txtGhiChu.clear();
            }
            
        } catch (SQLException ex) {
            System.getLogger(controller_nguoiThan.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
            hienThiThongBao("loi", "gui yeu cau khong thanh cong");
        }
        
    }
    public void exit(ActionEvent event) throws IOException{
        userNT_session.setCurrentCCCD(null);
        navigator.chuyenManHinh(event,"/view/UI.fxml" , "Login System", "/decor/login_style.css");
    }

    private void loadLichSuYeuCau() {
       String maYC;
       String ngayTham;
       String caTham;
       String ngayTao;
       String trangThai;
       String lyDoPhanHoi;
       String sql="select MaYC,NgayTham,CaTham,NgayTao,TrangThai,LyDoPhanHoi from YeuCauThamGap "+

               "where CCCDNguoiThan=?";

            
        DateTimeFormatter dtf=DateTimeFormatter.ofPattern("dd/MM/yyyy");
        try {
            Connection con=DBConnection.getConnection();
            PreparedStatement pst=con.prepareStatement(sql);
            pst.setString(1, cccd_NT);
            ResultSet rs=pst.executeQuery();
            while(rs.next()){
                maYC=String.valueOf(rs.getInt("MaYC"));
                ngayTham=rs.getDate("NgayTham").toLocalDate().format(dtf);
                caTham=rs.getString("CaTham");
                ngayTao=rs.getDate("NgayTao").toLocalDate().format(dtf);
                trangThai=rs.getString("TrangThai");
                lyDoPhanHoi=rs.getString("LyDoPhanHoi");
                if(lyDoPhanHoi==null) lyDoPhanHoi="Đang chờ cán bộ xử lý";
                danhSachDon.add(new DonXinTham(maYC,ngayTham,caTham,ngayTao,trangThai,lyDoPhanHoi));
            }
            
        } catch (SQLException ex) {
            System.getLogger(controller_nguoiThan.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
       
    }

    private void hienThiThongBao(String tieuDe,String content) {
        Alert al=new Alert(Alert.AlertType.INFORMATION);
        al.setHeaderText(null);
        al.setTitle(tieuDe);
        al.setContentText(content);
        al.showAndWait();
    }
    
    
}
