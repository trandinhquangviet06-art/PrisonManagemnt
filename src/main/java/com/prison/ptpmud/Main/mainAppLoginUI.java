package com.prison.ptpmud.Main;

import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class mainAppLoginUI extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/view/UI.fxml"));
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/decor/login_style.css").toExternalForm());
        stage.setTitle("Login System");
        stage.setScene(scene);
        
        // ÉP CỬA SỔ TỰ ĐỘNG PHÓNG TO HẾT CỠ KHI MỞ (Maximized)
        stage.setMaximized(true); 
        
        // Nếu muốn ẩn luôn cả thanh Taskbar bên dưới thì dùng dòng này:
        // stage.setFullScreen(true);

        stage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
}