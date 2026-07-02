/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.prison.ptpmud.Controller;

import com.prison.ptpmud.Main.navigator;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author Admin
 */
public class GuardDashboardController {

    @FXML
    private AnchorPane contentArea;

    @FXML
    private void quanLyPhamNhan(ActionEvent event) {
        try {
            navigator.chuyenManHinh(
                    event,
                    "/view/prisoner_management.fxml",
                    "Quản lý phạm nhân",
                    "/decor/prisoner_management.css"
            );

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void duyetYeuCauThamGap(ActionEvent event) {
        hienThiGiaoDienCon("/view/visit_request_management.fxml");
    }

    @FXML
    private void xemLichThamGap(ActionEvent event) {
        hienThiGiaoDienCon("/view/visit_schedule.fxml");
    }

    @FXML
    private void Dangxuat(ActionEvent event) {
        try {
            navigator.chuyenManHinh(
                    event,
                    "/view/UI.fxml",
                    "Đăng nhập",
                    "/decor/login_style.css"
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void hienThiGiaoDienCon(String duongDanFxml) {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource(duongDanFxml));
            Parent nodeCon = loader.load();

            contentArea.getChildren().clear();

            AnchorPane.setTopAnchor(nodeCon, 0.0);
            AnchorPane.setBottomAnchor(nodeCon, 0.0);
            AnchorPane.setLeftAnchor(nodeCon, 0.0);
            AnchorPane.setRightAnchor(nodeCon, 0.0);

            contentArea.getChildren().add(nodeCon);

        } catch (IOException e) {
            System.out.println("Lỗi: Không thể tải file giao diện tại đường dẫn: " + duongDanFxml);
            e.printStackTrace();
        }
    }
}
