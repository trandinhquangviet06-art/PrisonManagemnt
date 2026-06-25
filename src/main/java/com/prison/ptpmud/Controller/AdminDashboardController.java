package com.prison.ptpmud.Controller;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class AdminDashboardController {

    @FXML
    public void initialize() {
        // Init logic if needed
    }

    @FXML
    void handleOpenPrisonerMgmt(ActionEvent event) {
        changeScene(event, "/view/prisoner_mgmt.fxml", "Quản Lý Phạm Nhân");
    }

    @FXML
    void handleOpenGuardMgmt(ActionEvent event) {
        changeScene(event, "/view/guard_management.fxml", "Quản Lý Quản Ngục");
    }

    @FXML
    void handleOpenCreateAccount(ActionEvent event) {
        changeScene(event, "/view/create_guard_account.fxml", "Cấp Tài Khoản");
    }

    @FXML
    void handleLogout(ActionEvent event) {
        changeScene(event, "/view/UI.fxml", "Hệ Thống Quản Lý Trại Giam - Đăng nhập");
    }

    private void changeScene(ActionEvent event, String fxmlFile, String title) {
        try {
            Parent view = FXMLLoader.load(getClass().getResource(fxmlFile));
            Scene scene = new Scene(view);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(scene);
            window.setTitle(title);
            window.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
