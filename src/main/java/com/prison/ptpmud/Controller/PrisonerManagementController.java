/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.prison.ptpmud.Controller;
import DAO.PrisonerDao;
import com.prison.ptpmud.Main.navigator;
import com.prison.ptpmud.Model.Prisoner;
import java.sql.SQLException;
import java.util.Optional;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
/**
 *
 * @author Admin
 */
public class PrisonerManagementController {
     @FXML
    private TextField txtTimkiem;

    @FXML
    private TableView<Prisoner> tbPhamNhan;

    @FXML
    private TableColumn<Prisoner, String> maphamnhan;

    @FXML
    private TableColumn<Prisoner, String> hoten;

    @FXML
    private TableColumn<Prisoner, String> ngaysinh;

    @FXML
    private TableColumn<Prisoner, String> toidanh;

    @FXML
    private TableColumn<Prisoner, String> khugiam;

    @FXML
    private TableColumn<Prisoner, String> trangthai;

    private PrisonerDao prisonerDao = new PrisonerDao();
    private ActionEvent event;

    @FXML
    public void initialize() {
        maphamnhan.setCellValueFactory(new PropertyValueFactory<>("maPhamNhan"));
        hoten.setCellValueFactory(new PropertyValueFactory<>("hoTen"));
        ngaysinh.setCellValueFactory(new PropertyValueFactory<>("ngaySinh"));
        toidanh.setCellValueFactory(new PropertyValueFactory<>("toiDanh"));
        khugiam.setCellValueFactory(new PropertyValueFactory<>("khuGiamGiu"));
        trangthai.setCellValueFactory(new PropertyValueFactory<>("trangThai"));

        loadData();
    }

    private void loadData() {
        try {
            ObservableList<Prisoner> list =
                    FXCollections.observableArrayList(prisonerDao.findAll());

            tbPhamNhan.setItems(list);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void timKiem() {
        try {
            String keyword = txtTimkiem.getText();

            ObservableList<Prisoner> list =
                    FXCollections.observableArrayList(
                            prisonerDao.searchByName(keyword)
                    );

            tbPhamNhan.setItems(list);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void lamMoi() {
        txtTimkiem.clear();
        loadData();
    }
    @FXML
private void themPN() {
    try {
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/add_prisoner.fxml"));
        Parent root = loader.load();

        
        Stage stage = new Stage();
        stage.setTitle("Thêm phạm nhân mới");
        stage.setScene(new Scene(root));
        
      
        stage.initModality(javafx.stage.Modality.APPLICATION_MODAL); 
        stage.showAndWait(); 

       
        AddPrisonerController controller = loader.getController();
        if (controller.isSaved()) {
            loadData(); 
        }

    } catch (Exception e) {
        System.out.println("Lỗi: Không tìm thấy hoặc không mở được file add_prisoner.fxml");
        e.printStackTrace();
    }
}
  @FXML
private void suaPN() {
    
    
    Prisoner selected = tbPhamNhan.getSelectionModel().getSelectedItem();

    if (selected == null) {
        System.out.println("Vui lòng chọn phạm nhân cần sửa!");
        return;
    }

    
    TextInputDialog dialogHoTen =
            new TextInputDialog(selected.getHoTen());
    dialogHoTen.setTitle("Sửa họ tên");
    dialogHoTen.setHeaderText("Nhập họ tên mới");
    Optional<String> hoTen = dialogHoTen.showAndWait();

    if (!hoTen.isPresent()) return;

    
    TextInputDialog dialogNgaySinh =
            new TextInputDialog(selected.getNgaySinh());
    dialogNgaySinh.setTitle("Sửa ngày sinh");
    dialogNgaySinh.setHeaderText("Nhập ngày sinh mới (yyyy-MM-dd)");
    Optional<String> ngaySinh = dialogNgaySinh.showAndWait();

    if (!ngaySinh.isPresent()) return;

    
    TextInputDialog dialogToiDanh =
            new TextInputDialog(selected.getToiDanh());
    dialogToiDanh.setTitle("Sửa tội danh");
    dialogToiDanh.setHeaderText("Nhập tội danh mới");
    Optional<String> toiDanh = dialogToiDanh.showAndWait();

    if (!toiDanh.isPresent()) return;

    
    TextInputDialog dialogKhuGiam =
            new TextInputDialog(selected.getKhuGiamGiu());
    dialogKhuGiam.setTitle("Sửa khu giam");
    dialogKhuGiam.setHeaderText("Nhập khu giam mới");
    Optional<String> khuGiam = dialogKhuGiam.showAndWait();

    if (!khuGiam.isPresent()) return;

    
    TextInputDialog dialogTrangThai =
            new TextInputDialog(selected.getTrangThai());
    dialogTrangThai.setTitle("Sửa trạng thái");
    dialogTrangThai.setHeaderText("Nhập trạng thái mới");
    Optional<String> trangThai = dialogTrangThai.showAndWait();

    if (!trangThai.isPresent()) return;

   
    selected.setHoTen(hoTen.get());
    selected.setNgaySinh(ngaySinh.get());
    selected.setToiDanh(toiDanh.get());
    selected.setKhuGiamGiu(khuGiam.get());
    selected.setTrangThai(trangThai.get());

    try {
        boolean success = prisonerDao.update(selected);

        if (success) {
            System.out.println("Cập nhật thành công!");
            loadData();
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }
}
@FXML
private void xoaPN() {
    
    Prisoner selected = tbPhamNhan.getSelectionModel().getSelectedItem();
    
    if (selected == null) {
        javafx.scene.control.Alert al = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.WARNING);
        al.setTitle("Thông báo");
        al.setHeaderText(null);
        al.setContentText("Vui lòng chọn phạm nhân cần xóa trên bảng!");
        al.showAndWait();
        return;
    }
    
    javafx.scene.control.Alert confirmAlert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.CONFIRMATION);
    confirmAlert.setTitle("Xác nhận xóa");
    confirmAlert.setHeaderText(null);
    confirmAlert.setContentText("Bạn có chắc chắn muốn xóa phạm nhân " + selected.getHoTen() + " (" + selected.getMaPhamNhan() + ") không?");
    Optional<javafx.scene.control.ButtonType> result = confirmAlert.showAndWait();
    
    if (result.isPresent() && result.get() == javafx.scene.control.ButtonType.OK) {
        try {
            boolean success = prisonerDao.delete(selected.getMaPhamNhan());
            if (success) {
                javafx.scene.control.Alert al = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.INFORMATION);
                al.setTitle("Thành công");
                al.setHeaderText(null);
                al.setContentText("Đã xóa thành công phạm nhân khỏi hệ thống.");
                al.showAndWait();
                loadData(); 
            } else {
                javafx.scene.control.Alert al = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.ERROR);
                al.setTitle("Thất bại");
                al.setHeaderText(null);
                al.setContentText("Xóa thất bại!");
                al.showAndWait();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            javafx.scene.control.Alert al = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.ERROR);
            al.setTitle("Lỗi");
            al.setHeaderText(null);
            al.setContentText("Không thể xóa phạm nhân này vì đang có dữ liệu liên quan (người thân, yêu cầu thăm gặp...).\nVui lòng xóa các dữ liệu liên quan trước.");
            al.showAndWait();
        }
    }
}
@FXML
private void quayLai(ActionEvent event) {
    try {
        navigator.chuyenManHinh(
                event,
                "/view/guard_dashboard.fxml",
                "Guard Dashboard",
                "/decor/style_nguoithan.css"
        );
    } catch (Exception e) {
        e.printStackTrace();
    }
}
}
