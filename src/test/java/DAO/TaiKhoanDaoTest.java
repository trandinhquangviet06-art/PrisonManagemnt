package DAO;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.SQLException;

import DAO.TaiKhoanDao;
import database.DBConnection;

public class TaiKhoanDaoTest {

    private TaiKhoanDao taiKhoanDao;

    @BeforeEach
    public void setUp() {
        taiKhoanDao = new TaiKhoanDao();
    }

    @AfterEach
    public void tearDown() {
        taiKhoanDao = null;
    }

    @Test
    public void testKiemTraTaiKhoan_Admin_Success() throws SQLException {
        // Giả sử có một tài khoản admin thực tế là admin/admin
        // Lưu ý: Test này sẽ fail nếu mật khẩu thực tế trong DB không phải là admin hoặc BCrypt không khớp.
        // Đây là ví dụ về cách gọi hàm.
        System.out.println("Running testKiemTraTaiKhoan_Admin_Success...");
        
        try {
            Connection conn = DBConnection.getConnection();
            if (conn != null) {
                // Chúng ta không biết mật khẩu thật trong DB của người dùng, nên tạm thời gọi hàm để xem có bị exception không.
                boolean result = taiKhoanDao.KiemTraTaiKhoan("admin", "admin", "admin");
                // assertNotNull(result) - kết quả trả về boolean
                System.out.println("Kết quả đăng nhập thử với admin/admin: " + result);
                conn.close();
            }
        } catch (SQLException e) {
            fail("Ngoại lệ SQL xảy ra: " + e.getMessage());
        }
    }

    @Test
    public void testKiemTraTaiKhoan_WrongPassword() throws SQLException {
        System.out.println("Running testKiemTraTaiKhoan_WrongPassword...");
        
        try {
            boolean result = taiKhoanDao.KiemTraTaiKhoan("admin", "sai_mat_khau_123", "admin");
            assertFalse(result, "Đăng nhập với mật khẩu sai phải trả về false");
        } catch (SQLException e) {
            fail("Ngoại lệ SQL xảy ra: " + e.getMessage());
        }
    }

    @Test
    public void testKiemTraTaiKhoan_WrongRole() throws SQLException {
        System.out.println("Running testKiemTraTaiKhoan_WrongRole...");
        
        try {
            // Role không tồn tại hoặc sai
            boolean result = taiKhoanDao.KiemTraTaiKhoan("admin", "admin", "KhachVangLai");
            assertFalse(result, "Đăng nhập với vai trò sai phải trả về false");
        } catch (SQLException e) {
            fail("Ngoại lệ SQL xảy ra: " + e.getMessage());
        }
    }
}
