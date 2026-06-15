/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.prison.ptpmud.Controller;
import DAO.PrisonerDao;
import com.prison.ptpmud.Model.Prisoner;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
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
}
