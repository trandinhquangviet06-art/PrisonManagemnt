/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.prison.ptpmud.Main;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author LOQ
 */
public class navigator {
    public static void chuyenManHinh(ActionEvent event,String fxmlPath, String title,String pathDecor) throws IOException{
        Parent root=FXMLLoader.load(navigator.class.getResource(fxmlPath));
        Scene scene=new Scene(root);
        scene.getStylesheets().add(navigator.class.getResource(pathDecor).toExternalForm());
        Stage stage= (Stage)((Node) event.getSource()).getScene().getWindow();
        stage.setTitle(title);
        stage.setScene(scene);
        stage.show();
    }
}
