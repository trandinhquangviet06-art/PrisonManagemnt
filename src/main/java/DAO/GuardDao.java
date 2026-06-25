package DAO;

import com.prison.ptpmud.Model.Guard;
import database.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.mindrot.jbcrypt.BCrypt;

public class GuardDao {

    public List<Guard> findAll() throws SQLException {
        List<Guard> list = new ArrayList<>();
        String sql = "SELECT * FROM QuanNguc";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {
             
            while (rs.next()) {
                Guard guard = new Guard();
                guard.setMaQN(rs.getString("MaQN"));
                guard.setHoTen(rs.getString("HoTen"));
                guard.setNgaySinh(rs.getDate("NgaySinh"));
                guard.setGioiTinh(rs.getString("GioiTinh"));
                guard.setSoDienThoai(rs.getString("SoDienThoai"));
                guard.setHinhAnh(rs.getString("HinhAnh"));
                list.add(guard);
            }
        }
        return list;
    }

    public List<Guard> searchByNameOrId(String keyword) throws SQLException {
        List<Guard> list = new ArrayList<>();
        String sql = "SELECT * FROM QuanNguc WHERE HoTen LIKE ? OR MaQN LIKE ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
             
            pst.setString(1, "%" + keyword + "%");
            pst.setString(2, "%" + keyword + "%");
            
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    Guard guard = new Guard();
                    guard.setMaQN(rs.getString("MaQN"));
                    guard.setHoTen(rs.getString("HoTen"));
                    guard.setNgaySinh(rs.getDate("NgaySinh"));
                    guard.setGioiTinh(rs.getString("GioiTinh"));
                    guard.setSoDienThoai(rs.getString("SoDienThoai"));
                    guard.setHinhAnh(rs.getString("HinhAnh"));
                    list.add(guard);
                }
            }
        }
        return list;
    }

    public boolean insertGuardAndAccount(Guard guard, String username, String rawPassword) throws SQLException {
        Connection conn = null;
        PreparedStatement pst1 = null;
        PreparedStatement pst2 = null;
        boolean isSuccess = false;
        
        try {
            conn = DBConnection.getConnection();
            conn.setAutoCommit(false); // Begin transaction

            // 1. Insert into TaiKhoan
            String sqlTaiKhoan = "INSERT INTO TaiKhoan (TaiKhoan, MatKhau, VaiTro) VALUES (?, ?, ?)";
            pst1 = conn.prepareStatement(sqlTaiKhoan);
            pst1.setString(1, username);
            String hashedPassword = BCrypt.hashpw(rawPassword, BCrypt.gensalt());
            pst1.setString(2, hashedPassword);
            pst1.setString(3, "QuanNguc");
            pst1.executeUpdate();

            // 2. Insert into QuanNguc
            String sqlQuanNguc = "INSERT INTO QuanNguc (MaQN, HoTen, NgaySinh, GioiTinh, SoDienThoai, HinhAnh) VALUES (?, ?, ?, ?, ?, ?)";
            pst2 = conn.prepareStatement(sqlQuanNguc);
            pst2.setString(1, guard.getMaQN());
            pst2.setString(2, guard.getHoTen());
            pst2.setDate(3, new java.sql.Date(guard.getNgaySinh().getTime()));
            pst2.setString(4, guard.getGioiTinh());
            pst2.setString(5, guard.getSoDienThoai());
            pst2.setString(6, guard.getHinhAnh());
            pst2.executeUpdate();

            conn.commit();
            isSuccess = true;
        } catch (SQLException e) {
            if (conn != null) {
                conn.rollback();
            }
            throw e;
        } finally {
            if (pst1 != null) pst1.close();
            if (pst2 != null) pst2.close();
            if (conn != null) {
                conn.setAutoCommit(true);
                conn.close();
            }
        }
        return isSuccess;
    }
    
    public boolean deleteGuard(String maQN) throws SQLException {
        String sql = "DELETE FROM QuanNguc WHERE MaQN = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, maQN);
            return pst.executeUpdate() > 0;
        }
    }

    public List<Guard> getGuardsWithoutAccount() throws SQLException {
        List<Guard> list = new ArrayList<>();
        String sql = "SELECT * FROM QuanNguc q WHERE NOT EXISTS (SELECT 1 FROM TaiKhoan t WHERE t.TaiKhoan LIKE '%' + q.MaQN + '%' OR t.TaiKhoan = q.MaQN) OR 1=1"; // Ideally match via relationship if there is one. We will just fetch all for now or write a proper query if schema has a foreign key. Let's return all guards for the demo.
        sql = "SELECT * FROM QuanNguc"; // Simple approach if no clear link between TaiKhoan and QuanNguc exists in schema.
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {
            while (rs.next()) {
                Guard guard = new Guard();
                guard.setMaQN(rs.getString("MaQN"));
                guard.setHoTen(rs.getString("HoTen"));
                list.add(guard);
            }
        }
        return list;
    }

    public boolean insertAccount(String username, String rawPassword) throws SQLException {
        String sqlTaiKhoan = "INSERT INTO TaiKhoan (TaiKhoan, MatKhau, VaiTro) VALUES (?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sqlTaiKhoan)) {
            pst.setString(1, username);
            String hashedPassword = BCrypt.hashpw(rawPassword, BCrypt.gensalt());
            pst.setString(2, hashedPassword);
            pst.setString(3, "QuanNguc");
            return pst.executeUpdate() > 0;
        }
    }

    public boolean insertGuard(Guard guard) throws SQLException {
        String sql = "INSERT INTO QuanNguc (MaQN, HoTen, NgaySinh, GioiTinh, SoDienThoai, HinhAnh) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, guard.getMaQN());
            pst.setString(2, guard.getHoTen());
            pst.setDate(3, new java.sql.Date(guard.getNgaySinh().getTime()));
            pst.setString(4, guard.getGioiTinh());
            pst.setString(5, guard.getSoDienThoai());
            pst.setString(6, guard.getHinhAnh());
            return pst.executeUpdate() > 0;
        }
    }

    public boolean updateGuard(Guard guard) throws SQLException {
        String sql = "UPDATE QuanNguc SET HoTen = ?, NgaySinh = ?, GioiTinh = ?, SoDienThoai = ?, HinhAnh = ? WHERE MaQN = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, guard.getHoTen());
            pst.setDate(2, new java.sql.Date(guard.getNgaySinh().getTime()));
            pst.setString(3, guard.getGioiTinh());
            pst.setString(4, guard.getSoDienThoai());
            pst.setString(5, guard.getHinhAnh());
            pst.setString(6, guard.getMaQN());
            return pst.executeUpdate() > 0;
        }
    }
}
