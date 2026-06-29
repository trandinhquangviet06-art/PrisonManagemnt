package com.prison.ptpmud.Controller;

import DAO.GuardDao;
import com.prison.ptpmud.Model.Guard;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.StringConverter;

public class CreateGuardAccountController {

    @FXML
    private ComboBox<Guard> cbQuanNguc;

    @FXML
    private TextField txtTenDangNhap;

    @FXML
    private PasswordField txtMatKhau;

    private GuardDao guardDao = new GuardDao();

    @FXML
    public void initialize() {
        try {
            List<Guard> guards = guardDao.getGuardsWithoutAccount();
            cbQuanNguc.setItems(FXCollections.observableArrayList(guards));
            
            cbQuanNguc.setConverter(new StringConverter<Guard>() {
                @Override
                public String toString(Guard guard) {
                    if (guard == null) return "";
                    return guard.getMaQN() + " - " + guard.getHoTen();
                }

                @Override
                public Guard fromString(String string) {
                    return null;
                }
            });
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleCreateAccount(ActionEvent event) {
        Guard selectedGuard = cbQuanNguc.getValue();
        String username = txtTenDangNhap.getText().trim();
        String password = txtMatKhau.getText().trim();

        if (selectedGuard == null || username.isEmpty() || password.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Cảnh báo", "Vui lòng nhập đầy đủ thông tin!");
            return;
        }

        try {
            if (guardDao.insertAccount(username, password)) {
                showAlert(Alert.AlertType.INFORMATION, "Thành công", "Đã cấp tài khoản thành công cho " + selectedGuard.getHoTen());
                handleClear(null);
            } else {
                showAlert(Alert.AlertType.ERROR, "Lỗi", "Không thể tạo tài khoản.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Lỗi Database", "Lỗi: " + e.getMessage());
        }
    }

    @FXML
    private void handleClear(ActionEvent event) {
        cbQuanNguc.getSelectionModel().clearSelection();
        txtTenDangNhap.clear();
        txtMatKhau.clear();
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
