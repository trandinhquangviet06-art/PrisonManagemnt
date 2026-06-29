package DAO;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import com.prison.ptpmud.Model.Guard;
import database.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public class GuardDaoTest {

    private GuardDao guardDao;
    private final String TEST_MAQN = "TEST_QN_001";
    private final String TEST_USERNAME = "test_guard_user";

    @BeforeEach
    public void setUp() {
        guardDao = new GuardDao();
    }

    @AfterEach
    public void tearDown() {
        try {
            guardDao.deleteGuard(TEST_MAQN);
            
            // Xóa tài khoản nếu có tạo
            try (Connection conn = DBConnection.getConnection();
                 PreparedStatement pst = conn.prepareStatement("DELETE FROM TaiKhoan WHERE TaiKhoan = ?")) {
                pst.setString(1, TEST_USERNAME);
                pst.executeUpdate();
            }
        } catch (SQLException e) {
            // ignore
        }
        guardDao = null;
    }

    @Test
    public void testFindAll() {
        try {
            List<Guard> guards = guardDao.findAll();
            assertNotNull(guards, "Danh sách quản ngục trả về không được null");
            System.out.println("Tìm thấy " + guards.size() + " quản ngục.");
        } catch (SQLException e) {
            fail("Ngoại lệ SQL: " + e.getMessage());
        }
    }

    @Test
    public void testInsertGuardAndAccountAndSearchAndDelete() {
        Guard guard = new Guard();
        guard.setMaQN(TEST_MAQN);
        guard.setHoTen("Test Guard Name");
        guard.setNgaySinh(new Date());
        guard.setGioiTinh("Nam");
        guard.setSoDienThoai("0123456789");
        guard.setHinhAnh("img.jpg");

        try {
            // 1. Insert Guard & Account
            boolean isInserted = guardDao.insertGuardAndAccount(guard, TEST_USERNAME, "password123");
            assertTrue(isInserted, "Phải thêm thành công quản ngục và tài khoản");

            // 2. Search
            List<Guard> results = guardDao.searchByNameOrId("Test Guard Name");
            assertNotNull(results);
            assertFalse(results.isEmpty(), "Phải tìm thấy quản ngục vừa thêm");

            boolean match = false;
            for (Guard g : results) {
                if (g.getMaQN().equals(TEST_MAQN)) {
                    match = true;
                    break;
                }
            }
            assertTrue(match, "Kết quả tìm kiếm phải chứa mã quản ngục vừa thêm");

            // 3. Delete
            boolean isDeleted = guardDao.deleteGuard(TEST_MAQN);
            assertTrue(isDeleted, "Phải xóa thành công quản ngục");

        } catch (SQLException e) {
            fail("Ngoại lệ SQL: " + e.getMessage());
        }
    }
}
