package com.prison.ptpmud.Controller;

import DAO.GuardDao;
import com.prison.ptpmud.Model.Guard;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import java.io.File;
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
    private String currentImagePath = "";

    @FXML
    public void initialize() {
        colId.setCellValueFactory(new PropertyValueFactory<>("maQN"));
        colName.setCellValueFactory(new PropertyValueFactory<>("hoTen"));
        colDob.setCellValueFactory(new PropertyValueFactory<>("ngaySinh"));
        colGender.setCellValueFactory(new PropertyValueFactory<>("gioiTinh"));
        colPhone.setCellValueFactory(new PropertyValueFactory<>("soDienThoai"));

        tvGuards.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                txtId.setText(newSelection.getMaQN());
                txtName.setText(newSelection.getHoTen());
                if (newSelection.getNgaySinh() != null) {
                    txtDob.setText(new SimpleDateFormat("yyyy-MM-dd").format(newSelection.getNgaySinh()));
                } else {
                    txtDob.clear();
                }
                txtGender.setText(newSelection.getGioiTinh());
                txtPhone.setText(newSelection.getSoDienThoai());

                currentImagePath = newSelection.getHinhAnh() != null ? newSelection.getHinhAnh() : "";
                if (!currentImagePath.isEmpty()) {
                    try {
                        imgAvatar.setImage(new Image(new File(currentImagePath).toURI().toString()));
                    } catch (Exception e) {
                        imgAvatar.setImage(null);
                    }
                } else {
                    imgAvatar.setImage(null);
                }
            }
        });

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
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Chọn ảnh hồ sơ quản ngục");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif")
        );
        File selectedFile = fileChooser.showOpenDialog(txtId.getScene().getWindow());
        if (selectedFile != null) {
            currentImagePath = selectedFile.getAbsolutePath();
            imgAvatar.setImage(new Image(selectedFile.toURI().toString()));
        }
    }

    @FXML
    private void handleAdd(ActionEvent event) {
        String id = txtId.getText().trim();
        String name = txtName.getText().trim();
        String dobStr = txtDob.getText().trim();
        String gender = txtGender.getText().trim();
        String phone = txtPhone.getText().trim();

        if (id.isEmpty() || name.isEmpty() || dobStr.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Cảnh báo", "Vui lòng nhập Mã, Họ tên và Ngày sinh!");
            return;
        }

        try {
            Date dob = new SimpleDateFormat("yyyy-MM-dd").parse(dobStr);
            Guard guard = new Guard(id, name, dob, gender, phone, currentImagePath);
            if (guardDao.insertGuard(guard)) {
                showAlert(Alert.AlertType.INFORMATION, "Thành công", "Đã thêm quản ngục thành công!");
                loadData();
                handleClear(event);
            } else {
                showAlert(Alert.AlertType.ERROR, "Lỗi", "Thêm thất bại!");
            }
        } catch (ParseException e) {
            showAlert(Alert.AlertType.ERROR, "Lỗi định dạng", "Ngày sinh phải có định dạng YYYY-MM-DD");
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Lỗi CSDL", "Lỗi: " + e.getMessage());
        }
    }

    @FXML
    private void handleUpdate(ActionEvent event) {
        Guard selected = tvGuards.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert(Alert.AlertType.WARNING, "Cảnh báo", "Vui lòng chọn quản ngục cần cập nhật!");
            return;
        }

        String name = txtName.getText().trim();
        String dobStr = txtDob.getText().trim();
        String gender = txtGender.getText().trim();
        String phone = txtPhone.getText().trim();

        if (name.isEmpty() || dobStr.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Cảnh báo", "Vui lòng nhập Họ tên và Ngày sinh!");
            return;
        }

        try {
            Date dob = new SimpleDateFormat("yyyy-MM-dd").parse(dobStr);
            Guard guard = new Guard(selected.getMaQN(), name, dob, gender, phone, currentImagePath);
            
            boolean isUpdated = guardDao.updateGuard(guard);
            if (isUpdated) {
                showAlert(Alert.AlertType.INFORMATION, "Thành công", "Đã cập nhật quản ngục thành công!");
                loadData();
            } else {
                showAlert(Alert.AlertType.ERROR, "Lỗi", "Cập nhật thất bại!");
            }
        } catch (ParseException e) {
            showAlert(Alert.AlertType.ERROR, "Lỗi định dạng", "Ngày sinh phải có định dạng YYYY-MM-DD");
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Lỗi CSDL", "Lỗi: " + e.getMessage());
        }
    }

    @FXML
    private void handleClear(ActionEvent event) {
        txtId.clear();
        txtName.clear();
        txtDob.clear();
        txtGender.clear();
        txtPhone.clear();
        imgAvatar.setImage(null);
        currentImagePath = "";
        tvGuards.getSelectionModel().clearSelection();
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
