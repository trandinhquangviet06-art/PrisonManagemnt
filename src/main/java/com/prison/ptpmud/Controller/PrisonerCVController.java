package com.prison.ptpmud.Controller;

import com.prison.ptpmud.Model.Prisoner;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import java.io.File;

public class PrisonerCVController {

    @FXML private Label lblId;
    @FXML private Label lblName;
    @FXML private Label lblCrime;
    @FXML private Label lblCell;
    @FXML private Label lblDate;
    @FXML private Label lblStatus;
    @FXML private ImageView imgAvatar; 

    public void setPrisonerData(Prisoner prisoner) {
        lblId.setText(prisoner.getMaPhamNhan());
        lblName.setText(prisoner.getHoTen());
        lblCrime.setText(prisoner.getToiDanh());
        lblCell.setText(prisoner.getKhuGiamGiu());
        lblDate.setText(prisoner.getNgaySinh());
        lblStatus.setText(prisoner.getTrangThai());

        String path = prisoner.getHinhAnh();
        if (path != null && !path.trim().isEmpty()) {
            File file = new File(path);
            if (file.exists()) {
                try {
                    imgAvatar.setImage(new Image(file.toURI().toString()));
                } catch (Exception e) {
                    loadPlaceholderImage();
                }
            } else {
                loadPlaceholderImage();
            }
        } else {
            loadPlaceholderImage();
        }
    }

    private void loadPlaceholderImage() {
        try {
            imgAvatar.setImage(new Image(getClass().getResourceAsStream("/images/logo.png")));
        } catch (Exception e) {
            System.err.println("[WARN] Không tìm thấy file ảnh mặc định");
            imgAvatar.setImage(null);
        }
    }

    @FXML
    private void handleClose(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }
}
