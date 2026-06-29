package DAO;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import com.prison.ptpmud.Model.Prisoner;
import java.sql.SQLException;
import java.util.ArrayList;

public class PrisonerDaoTest {

    private PrisonerDao prisonerDao;
    private final String TEST_ID = "TEST_PN_001";

    @BeforeEach
    public void setUp() {
        prisonerDao = new PrisonerDao();
    }

    @AfterEach
    public void tearDown() {
        // Dọn dẹp dữ liệu test nếu có
        try {
            prisonerDao.delete(TEST_ID);
        } catch (SQLException e) {
            // Bỏ qua nếu không tồn tại
        }
        prisonerDao = null;
    }

    @Test
    public void testFindAll() {
        try {
            ArrayList<Prisoner> list = prisonerDao.findAll();
            assertNotNull(list, "Danh sách trả về không được null");
            System.out.println("Tìm thấy " + list.size() + " phạm nhân.");
        } catch (SQLException e) {
            fail("Ngoại lệ SQL: " + e.getMessage());
        }
    }

    @Test
    public void testSaveAndFindByIdAndDelete() {
        Prisoner p = new Prisoner(TEST_ID, "Nguyen Van Test", "1990-01-01", "Giet Nguoi", "Khu A", "Dang Giam Giu", "img.jpg");
        
        try {
            // 1. Test Save
            boolean isSaved = prisonerDao.save(p);
            assertTrue(isSaved, "Phải lưu thành công phạm nhân mới");

            // 2. Test FindById
            Prisoner found = prisonerDao.findById(TEST_ID);
            assertNotNull(found, "Phải tìm thấy phạm nhân vừa thêm");
            assertEquals("Nguyen Van Test", found.getHoTen());

            // 3. Test Delete
            boolean isDeleted = prisonerDao.delete(TEST_ID);
            assertTrue(isDeleted, "Phải xóa thành công phạm nhân");
            
            Prisoner notFound = prisonerDao.findById(TEST_ID);
            assertNull(notFound, "Phạm nhân sau khi xóa phải không tìm thấy (null)");

        } catch (SQLException e) {
            fail("Ngoại lệ SQL: " + e.getMessage());
        }
    }

    @Test
    public void testUpdate() {
        Prisoner p = new Prisoner(TEST_ID, "Nguyen Van Test", "1990-01-01", "Giet Nguoi", "Khu A", "Dang Giam Giu", "img.jpg");
        
        try {
            prisonerDao.save(p);

            // Sửa thông tin
            p.setHoTen("Nguyen Van Test Updated");
            p.setKhuGiamGiu("Khu B");
            boolean isUpdated = prisonerDao.update(p);
            assertTrue(isUpdated, "Phải cập nhật thành công");

            // Kiểm tra lại trong DB
            Prisoner updated = prisonerDao.findById(TEST_ID);
            assertEquals("Nguyen Van Test Updated", updated.getHoTen());
            assertEquals("Khu B", updated.getKhuGiamGiu());

        } catch (SQLException e) {
            fail("Ngoại lệ SQL: " + e.getMessage());
        } finally {
            try {
                prisonerDao.delete(TEST_ID);
            } catch (Exception ignored) {}
        }
    }

    @Test
    public void testSearchByName() {
        Prisoner p = new Prisoner(TEST_ID, "Nguyen Van Test", "1990-01-01", "Giet Nguoi", "Khu A", "Dang Giam Giu", "img.jpg");
        
        try {
            prisonerDao.save(p);

            ArrayList<Prisoner> results = prisonerDao.searchByName("Nguyen Van Test");
            assertNotNull(results);
            assertFalse(results.isEmpty(), "Phải tìm thấy ít nhất 1 kết quả");

            boolean match = false;
            for (Prisoner result : results) {
                if (result.getMaPhamNhan().equals(TEST_ID)) {
                    match = true;
                    break;
                }
            }
            assertTrue(match, "Kết quả tìm kiếm phải chứa phạm nhân vừa thêm");

        } catch (SQLException e) {
            fail("Ngoại lệ SQL: " + e.getMessage());
        } finally {
            try {
                prisonerDao.delete(TEST_ID);
            } catch (Exception ignored) {}
        }
    }
}
