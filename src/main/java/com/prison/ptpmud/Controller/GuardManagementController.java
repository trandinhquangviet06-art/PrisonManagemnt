package com.prison.ptpmud.Controller;

import DAO.GuardDao;
import com.prison.ptpmud.Model.Guard;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class GuardManagementController {

    @FXML
    private TableView<Guard> tvGuards;

    @FXML
    private TableColumn<Guard, String> colId;

    @FXML
    private TableColumn<Guard, String> colName;

    @FXML
    private TableColumn<Guard, Date> colDob;

    @FXML
    private TableColumn<Guard, String> colGender;

    @FXML
    private TableColumn<Guard, String> colPhone;

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtDob;

    @FXML
    private TextField txtGender;

    @FXML
    private TextField txtPhone;

    @FXML
    private ImageView imgAvatar;

    private GuardDao guardDao = new GuardDao();

    @FXML
    public void initialize() {
        colId.setCellValueFactory(new PropertyValueFactory<>("maQN"));
        colName.setCellValueFactory(new PropertyValueFactory<>("hoTen"));
        colDob.setCellValueFactory(new PropertyValueFactory<>("ngaySinh"));
        colGender.setCellValueFactory(new PropertyValueFactory<>("gioiTinh"));
        colPhone.setCellValueFactory(new PropertyValueFactory<>("soDienThoai"));

        loadData();
    }

    public void loadData() {
        try {
            ObservableList<Guard> list = FXCollections.observableArrayList(guardDao.findAll());
            tvGuards.setItems(list);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleChooseImage(ActionEvent event) {
        // Implement logic to pick image
    }

    @FXML
    private void handleAdd(ActionEvent event) {
        // Implement logic to add a new guard
    }

    @FXML
    private void handleUpdate(ActionEvent event) {
        // Implement logic to update selected guard
    }

    @FXML
    private void handleClear(ActionEvent event) {
        txtId.clear();
        txtName.clear();
        txtDob.clear();
        txtGender.clear();
        txtPhone.clear();
    }

    @FXML
    private void handleDelete(ActionEvent event) {
        Guard selectedGuard = tvGuards.getSelectionModel().getSelectedItem();
        if (selectedGuard == null) {
            showAlert(Alert.AlertType.WARNING, "Cảnh báo", "Vui lòng chọn quản ngục cần xóa!");
            return;
        }

        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Xác nhận xóa");
        confirm.setHeaderText("Bạn có chắc chắn muốn xóa quản ngục: " + selectedGuard.getHoTen() + "?");
        confirm.setContentText("Lưu ý: Bạn cũng cần xóa tài khoản đăng nhập của quản ngục này (Tính năng mở rộng).");
        
        if (confirm.showAndWait().get() == ButtonType.OK) {
            try {
                if (guardDao.deleteGuard(selectedGuard.getMaQN())) {
                    showAlert(Alert.AlertType.INFORMATION, "Thành công", "Đã xóa quản ngục thành công.");
                    loadData();
                } else {
                    showAlert(Alert.AlertType.ERROR, "Lỗi", "Xóa thất bại.");
                }
            } catch (SQLException e) {
                e.printStackTrace();
                showAlert(Alert.AlertType.ERROR, "Lỗi DB", "Không thể xóa: " + e.getMessage());
            }
        }
    }

    @FXML
    private void handleOpenCreateAccount(ActionEvent event) {
        try {
            Parent view = FXMLLoader.load(getClass().getResource("/view/create_guard_account.fxml"));
            Scene scene = new Scene(view);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(scene);
            window.setTitle("Cấp Tài Khoản Quản Ngục");
            window.show();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Lỗi", "Không thể mở form cấp tài khoản.");
        }
    }

    @FXML
    private void handleBack(ActionEvent event) {
        try {
            Parent view = FXMLLoader.load(getClass().getResource("/view/admin_dashboard.fxml"));
            Scene scene = new Scene(view);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(scene);
            window.setTitle("Hệ Thống Quản Lý Cục Trưởng");
            window.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
