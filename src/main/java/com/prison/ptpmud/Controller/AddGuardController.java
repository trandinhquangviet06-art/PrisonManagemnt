package com.prison.ptpmud.Controller;

import DAO.GuardDao;
import com.prison.ptpmud.Model.Guard;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AddGuardController {

    @FXML
    private TextField txtMaQN;

    @FXML
    private TextField txtHoTen;

    @FXML
    private DatePicker dpNgaySinh;

    @FXML
    private TextField txtGioiTinh;

    @FXML
    private TextField txtSoDienThoai;

    @FXML
    private TextField txtTaiKhoan;

    @FXML
    private PasswordField txtMatKhau;

    private GuardManagementController parentController;
    private GuardDao guardDao = new GuardDao();

    public void setParentController(GuardManagementController parentController) {
        this.parentController = parentController;
    }

    @FXML
    void luuThongTin(ActionEvent event) {
        String maQN = txtMaQN.getText().trim();
        String hoTen = txtHoTen.getText().trim();
        LocalDate localDate = dpNgaySinh.getValue();
        String gioiTinh = txtGioiTinh.getText().trim();
        String sdt = txtSoDienThoai.getText().trim();
        String taiKhoan = txtTaiKhoan.getText().trim();
        String matKhau = txtMatKhau.getText().trim();

        if (maQN.isEmpty() || hoTen.isEmpty() || localDate == null || taiKhoan.isEmpty() || matKhau.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Cảnh báo", "Vui lòng nhập đầy đủ thông tin bắt buộc (Mã, Tên, Ngày sinh, Tài khoản, Mật khẩu)!");
            return;
        }

        Date ngaySinh = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Guard newGuard = new Guard(maQN, hoTen, ngaySinh, gioiTinh, sdt, "");

        try {
            boolean isSuccess = guardDao.insertGuardAndAccount(newGuard, taiKhoan, matKhau);
            if (isSuccess) {
                showAlert(Alert.AlertType.INFORMATION, "Thành công", "Đã thêm quản ngục và tạo tài khoản thành công!");
                if (parentController != null) {
                    parentController.loadData();
                }
                dongCuaSo(event);
            } else {
                showAlert(Alert.AlertType.ERROR, "Lỗi", "Thêm quản ngục thất bại!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Lỗi Database", "Lỗi: " + e.getMessage());
        }
    }

    @FXML
    void dongCuaSo(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
