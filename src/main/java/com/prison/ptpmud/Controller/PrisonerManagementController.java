package com.prison.ptpmud.Controller;

import DAO.PrisonerDao;
import com.prison.ptpmud.Model.Prisoner;
import java.io.IOException;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class PrisonerManagementController {
    @FXML
    private TextField searchField;

    @FXML
    private TableView<Prisoner> prisonerTable;

    @FXML
    private TableColumn<Prisoner, String> idColumn;

    @FXML
    private TableColumn<Prisoner, String> nameColumn;

    @FXML
    private TableColumn<Prisoner, String> dateColumn;

    @FXML
    private TableColumn<Prisoner, String> crimeColumn;

    @FXML
    private TableColumn<Prisoner, String> cellColumn;

    @FXML
    private TableColumn<Prisoner, String> statusColumn;

    @FXML
    private Label totalLabel;

    private PrisonerDao prisonerDao = new PrisonerDao();

    @FXML
    public void initialize() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("maPhamNhan"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("hoTen"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("ngaySinh")); // Or ngayNhapTrai if model has it
        crimeColumn.setCellValueFactory(new PropertyValueFactory<>("toiDanh"));
        cellColumn.setCellValueFactory(new PropertyValueFactory<>("khuGiamGiu"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("trangThai"));

        loadData();
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
    private void handleSearch() {
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
        // Implement add logic (e.g. open add_prisoner.fxml)
    }

    @FXML
    private void handleUpdatePrisoner(ActionEvent event) {
        // Implement update logic
    }

    @FXML
    private void handleDeletePrisoner(ActionEvent event) {
        // Implement delete logic
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
}
