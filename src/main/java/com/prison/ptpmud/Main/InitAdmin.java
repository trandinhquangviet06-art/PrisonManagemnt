package com.prison.ptpmud.Main;

import database.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import org.mindrot.jbcrypt.BCrypt;

public class InitAdmin {
    public static void main(String[] args) {
        String username = "admin";
        String password = "123";
        String role = "admin";
        
        System.out.println("Đang tạo tài khoản Cục trưởng...");
        
        try (Connection conn = DBConnection.getConnection()) {
            if (conn != null) {
                String sql = "INSERT INTO TaiKhoan (TaiKhoan, MatKhau, VaiTro) VALUES (?, ?, ?)";
                PreparedStatement pst = conn.prepareStatement(sql);
                
                pst.setString(1, username);
                pst.setString(2, BCrypt.hashpw(password, BCrypt.gensalt()));
                pst.setString(3, role);
                
                int rows = pst.executeUpdate();
                if (rows > 0) {
                    System.out.println("Thành công! Đã tạo tài khoản.");
                    System.out.println("Tài khoản: " + username);
                    System.out.println("Mật khẩu : " + password);
                    System.out.println("Bây giờ bạn có thể chạy dự án và đăng nhập.");
                }
            } else {
                System.out.println("Không thể kết nối đến cơ sở dữ liệu!");
            }
        } catch (SQLException e) {
            System.out.println("Lỗi (có thể tài khoản 'admin' đã tồn tại): " + e.getMessage());
        }
    }
}
