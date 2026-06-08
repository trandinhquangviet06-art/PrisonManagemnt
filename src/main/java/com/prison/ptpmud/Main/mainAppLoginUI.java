/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.prison.ptpmud.Main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author LOQ
 */
public class mainAppLoginUI extends Application{
    @Override
    public void start(Stage stage) throws Exception {
        Parent root =FXMLLoader.load(getClass().getResource("/view/UI.fxml"));
        Scene scene=new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/decor/login_style.css").toExternalForm());
        stage.setTitle("Login System");
        stage.setScene(scene);
        stage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
}
