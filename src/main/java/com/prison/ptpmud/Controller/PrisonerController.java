package com.prison.ptpmud.Controller;

import DAO.PrisonerDao;
import com.prison.ptpmud.Model.Prisoner;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;
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
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.geometry.Insets;
import javafx.stage.FileChooser;
import java.io.File;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class PrisonerController {

    @FXML
    private TextField searchField;

    @FXML
    private TableView<Prisoner> prisonerTable;

    @FXML
    private TableColumn<Prisoner, String> idColumn;

    @FXML
    private TableColumn<Prisoner, String> nameColumn;

    @FXML
    private TableColumn<Prisoner, String> crimeColumn;

    @FXML
    private TableColumn<Prisoner, String> cellColumn;

    @FXML
    private TableColumn<Prisoner, String> dateColumn;

    @FXML
    private TableColumn<Prisoner, String> statusColumn;

    @FXML
    private Label totalLabel;

    private PrisonerDao prisonerDao = new PrisonerDao();

    @FXML
    public void initialize() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("maPhamNhan"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("hoTen"));
        crimeColumn.setCellValueFactory(new PropertyValueFactory<>("toiDanh"));
        cellColumn.setCellValueFactory(new PropertyValueFactory<>("khuGiamGiu"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("ngaySinh"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("trangThai"));

        loadData();

        prisonerTable.setRowFactory(tv -> {
            javafx.scene.control.TableRow<Prisoner> row = new javafx.scene.control.TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    Prisoner rowData = row.getItem();
                    showPrisonerCV(rowData); 
                }
            });
            return row;
        });
    }

    private void showPrisonerCV(Prisoner prisoner) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/prisoner_cv.fxml"));
            Parent root = loader.load();

            PrisonerCVController controller = loader.getController();
            controller.setPrisonerData(prisoner);

            Stage cvStage = new Stage();
            cvStage.setTitle("Hồ Sơ Lý Lịch Cải Tạo - " + prisoner.getMaPhamNhan());
            cvStage.setScene(new Scene(root));
            cvStage.setResizable(false);
            cvStage.initModality(Modality.APPLICATION_MODAL); 
            cvStage.show();
        } catch (Exception e) {
            System.err.println("[ERROR] Lỗi không thể tải giao diện CV.");
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Lỗi hệ thống", "Không tìm thấy file giao diện prisoner_cv.fxml!");
        }
    }

    private void loadData() {
        try {
            ObservableList<Prisoner> list = FXCollections.observableArrayList(prisonerDao.findAll());
            prisonerTable.setItems(list);
            totalLabel.setText("Tổng số phạm nhân đang hiển thị: " + list.size());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleSearch(KeyEvent event) {
        search();
    }
    
    @FXML
    private void handleSearch(ActionEvent event) {
        search();
    }
    
    private void search() {
        try {
            String keyword = searchField.getText();
            ObservableList<Prisoner> list = FXCollections.observableArrayList(prisonerDao.searchByName(keyword));
            prisonerTable.setItems(list);
            totalLabel.setText("Tổng số phạm nhân đang hiển thị: " + list.size());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void showAddDialog(ActionEvent event) {
        Dialog<Prisoner> dialog = new Dialog<>();
        dialog.setTitle("Hệ thống - Thêm phạm nhân mới");
        dialog.setHeaderText("Nhập thông tin hồ sơ phạm nhân nhập trại:");

        ButtonType saveButtonType = new ButtonType("Lưu Hồ Sơ", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(saveButtonType, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10); grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField idTxt = new TextField(); idTxt.setPromptText("Ví dụ: PN1005");
        TextField nameTxt = new TextField(); nameTxt.setPromptText("Họ và tên");
        TextField crimeTxt = new TextField(); crimeTxt.setPromptText("Tội danh khởi tố");
        TextField cellTxt = new TextField(); cellTxt.setPromptText("Mã buồng giam");
        TextField dateTxt = new TextField(); dateTxt.setPromptText("YYYY-MM-DD");
        TextField statusTxt = new TextField("Đang chấp hành");
        
        TextField imagePathTxt = new TextField(); imagePathTxt.setEditable(false); imagePathTxt.setPromptText("Chưa chọn ảnh...");
        Button btnBrowse = new Button("Chọn ảnh...");
        btnBrowse.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Chọn ảnh chân dung phạm nhân");
            fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg"));
            File selectedFile = fileChooser.showOpenDialog(idTxt.getScene().getWindow());
            if (selectedFile != null) {
                imagePathTxt.setText(selectedFile.getAbsolutePath());
            }
        });

        grid.add(new Label("Số danh bộ (Mã):"), 0, 0); grid.add(idTxt, 1, 0);
        grid.add(new Label("Họ và tên:"), 0, 1);       grid.add(nameTxt, 1, 1);
        grid.add(new Label("Tội danh:"), 0, 2);        grid.add(crimeTxt, 1, 2);
        grid.add(new Label("Buồng giam:"), 0, 3);      grid.add(cellTxt, 1, 3);
        grid.add(new Label("Ngày nhập trại:"), 0, 4);   grid.add(dateTxt, 1, 4);
        grid.add(new Label("Trạng thái:"), 0, 5);      grid.add(statusTxt, 1, 5);
        
        HBox imageBox = new HBox(10, imagePathTxt, btnBrowse);
        grid.add(new Label("Ảnh chân dung:"), 0, 6);   grid.add(imageBox, 1, 6);

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == saveButtonType) {
                return new Prisoner(
                    idTxt.getText().trim(), nameTxt.getText().trim(), dateTxt.getText().trim(),
                    crimeTxt.getText().trim(), cellTxt.getText().trim(), statusTxt.getText().trim(),
                    imagePathTxt.getText().trim()
                );
            }
            return null;
        });

        Optional<Prisoner> result = dialog.showAndWait();
        result.ifPresent(prisoner -> {
            if (prisoner.getMaPhamNhan().isEmpty() || prisoner.getHoTen().isEmpty() || prisoner.getNgaySinh().isEmpty()) {
                showAlert(Alert.AlertType.WARNING, "Lỗi nhập liệu", "Vui lòng điền đầy đủ các thông tin cốt lõi!");
                return;
            }
            
            try {
                boolean success = prisonerDao.save(prisoner);
                if (success) {
                    showAlert(Alert.AlertType.INFORMATION, "Thành công", "Đã lưu hồ sơ phạm nhân " + prisoner.getHoTen());
                    loadData();
                } else {
                    showAlert(Alert.AlertType.ERROR, "Thất bại", "Mã số đã tồn tại trong cơ sở dữ liệu!");
                }
            } catch (SQLException e) {
                e.printStackTrace();
                showAlert(Alert.AlertType.ERROR, "Lỗi DB", "Lỗi: " + e.getMessage());
            }
        });
    }

    @FXML
    private void handleUpdatePrisoner(ActionEvent event) {
        Prisoner selected = prisonerTable.getSelectionModel().getSelectedItem();
        
        if (selected == null) {
            showAlert(Alert.AlertType.WARNING, "Thông báo", "Vui lòng chọn một phạm nhân trên bảng để chỉnh sửa!");
            return;
        }

        Dialog<Prisoner> dialog = new Dialog<>();
        dialog.setTitle("Hệ thống - Cập nhật hồ sơ");
        dialog.setHeaderText("Chỉnh sửa thông tin phạm nhân: " + selected.getHoTen());

        ButtonType updateButtonType = new ButtonType("Cập Nhật", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(updateButtonType, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10); grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField idTxt = new TextField(selected.getMaPhamNhan()); idTxt.setEditable(false); idTxt.setStyle("-fx-background-color: #e0e0e0;");
        TextField nameTxt = new TextField(selected.getHoTen());
        TextField crimeTxt = new TextField(selected.getToiDanh());
        TextField cellTxt = new TextField(selected.getKhuGiamGiu());
        TextField dateTxt = new TextField(selected.getNgaySinh());
        TextField statusTxt = new TextField(selected.getTrangThai());
        
        TextField imagePathTxt = new TextField(selected.getHinhAnh() != null ? selected.getHinhAnh() : "");
        imagePathTxt.setEditable(false);
        Button btnBrowse = new Button("Chọn ảnh...");
        btnBrowse.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Cập nhật ảnh chân dung phạm nhân");
            fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg"));
            File selectedFile = fileChooser.showOpenDialog(idTxt.getScene().getWindow());
            if (selectedFile != null) {
                imagePathTxt.setText(selectedFile.getAbsolutePath());
            }
        });

        grid.add(new Label("Số danh bộ (Khóa cố định):"), 0, 0); grid.add(idTxt, 1, 0);
        grid.add(new Label("Họ và tên:"), 0, 1);                 grid.add(nameTxt, 1, 1);
        grid.add(new Label("Tội danh:"), 0, 2);                  grid.add(crimeTxt, 1, 2);
        grid.add(new Label("Buồng giam:"), 0, 3);                grid.add(cellTxt, 1, 3);
        grid.add(new Label("Ngày sinh/nhập trại:"), 0, 4);       grid.add(dateTxt, 1, 4);
        grid.add(new Label("Trạng thái:"), 0, 5);                grid.add(statusTxt, 1, 5);
        
        HBox imageBox = new HBox(10, imagePathTxt, btnBrowse);
        grid.add(new Label("Ảnh chân dung:"), 0, 6);             grid.add(imageBox, 1, 6);

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == updateButtonType) {
                return new Prisoner(idTxt.getText(), nameTxt.getText().trim(), dateTxt.getText().trim(),
                                    crimeTxt.getText().trim(), cellTxt.getText().trim(), statusTxt.getText().trim(),
                                    imagePathTxt.getText().trim());
            }
            return null;
        });

        Optional<Prisoner> result = dialog.showAndWait();
        result.ifPresent(updatedPrisoner -> {
            if (updatedPrisoner.getHoTen().isEmpty() || updatedPrisoner.getNgaySinh().isEmpty()) {
                showAlert(Alert.AlertType.WARNING, "Lỗi dữ liệu", "Họ tên hoặc ngày nhập trại không được để trống!");
                return;
            }

            try {
                boolean success = prisonerDao.update(updatedPrisoner);
                if (success) {
                    showAlert(Alert.AlertType.INFORMATION, "Thành công", "Cập nhật thông tin hồ sơ thành công.");
                    loadData();
                } else {
                    showAlert(Alert.AlertType.ERROR, "Thất bại", "Lỗi Cập nhật!");
                }
            } catch (SQLException e) {
                e.printStackTrace();
                showAlert(Alert.AlertType.ERROR, "Lỗi DB", "Lỗi: " + e.getMessage());
            }
        });
    }

    @FXML
    private void handleDeletePrisoner(ActionEvent event) {
        Prisoner selected = prisonerTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert(Alert.AlertType.WARNING, "Cảnh báo", "Vui lòng chọn phạm nhân cần xóa trên bảng!");
            return;
        }

        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Xác nhận xóa");
        confirm.setHeaderText("Bạn có chắc chắn muốn xóa phạm nhân này?");
        if (confirm.showAndWait().get() == ButtonType.OK) {
            try {
                if (prisonerDao.delete(selected.getMaPhamNhan())) {
                    showAlert(Alert.AlertType.INFORMATION, "Thành công", "Đã xóa thành công phạm nhân.");
                    loadData();
                } else {
                    showAlert(Alert.AlertType.ERROR, "Lỗi", "Xóa thất bại!");
                }
            } catch (SQLException e) {
                e.printStackTrace();
                showAlert(Alert.AlertType.ERROR, "Lỗi DB", "Lỗi: " + e.getMessage());
            }
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
