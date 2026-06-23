/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;
import com.prison.ptpmud.Model.Prisoner;
import database.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
/**
 *
 * @author Admin
 */
public class PrisonerDao {
   public ArrayList<Prisoner> findAll() throws SQLException {
        ArrayList<Prisoner> list = new ArrayList<>();

        String sql = "SELECT * FROM PhamNhan";

        Connection conn = DBConnection.getConnection();
        PreparedStatement pst = conn.prepareStatement(sql);
        ResultSet rs = pst.executeQuery();

        while (rs.next()) {
            Prisoner p = new Prisoner(
                    rs.getString("maPhamNhan"),
                    rs.getString("hoTen"),
                    rs.getString("ngaySinh"),
                    rs.getString("toiDanh"),
                    rs.getString("khuGiamGiu"),
                    rs.getString("trangThai"),
                    rs.getString("hinhAnh")
            );

            list.add(p);
        }

        return list;
    }

    public Prisoner findById(String maPhamNhan) throws SQLException {
        String sql = "SELECT * FROM PhamNhan WHERE maPhamNhan = ?";

        Connection conn = DBConnection.getConnection();
        PreparedStatement pst = conn.prepareStatement(sql);
        pst.setString(1, maPhamNhan);

        ResultSet rs = pst.executeQuery();

        if (rs.next()) {
            return new Prisoner(
                    rs.getString("maPhamNhan"),
                    rs.getString("hoTen"),
                    rs.getString("ngaySinh"),
                    rs.getString("toiDanh"),
                    rs.getString("khuGiamGiu"),
                    rs.getString("trangThai"),
                    rs.getString("hinhAnh")
            );
        }

        return null;
    }

    public boolean save(Prisoner prisoner) throws SQLException {
        String sql = "INSERT INTO PhamNhan "
                + "(maPhamNhan, hoTen, ngaySinh, toiDanh, khuGiamGiu, trangThai,hinhAnh) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?)";

        Connection conn = DBConnection.getConnection();
        PreparedStatement pst = conn.prepareStatement(sql);

        pst.setString(1, prisoner.getMaPhamNhan());
        pst.setString(2, prisoner.getHoTen());
        pst.setString(3, prisoner.getNgaySinh());
        pst.setString(4, prisoner.getToiDanh());
        pst.setString(5, prisoner.getKhuGiamGiu());
        pst.setString(6, prisoner.getTrangThai());
        pst.setString(7, prisoner.getHinhAnh());

        return pst.executeUpdate() > 0;
    }

    public boolean update(Prisoner prisoner) throws SQLException {
        String sql = "UPDATE PhamNhan SET "
                + "hoTen = ?, "
                + "ngaySinh = ?, "
                + "toiDanh = ?, "
                + "KhuGiamGiu = ?, "
                + "trangThai = ?, "
                + "hinhAnh = ? "
                + "WHERE maPhamNhan = ?";

        Connection conn = DBConnection.getConnection();
        PreparedStatement pst = conn.prepareStatement(sql);

        pst.setString(1, prisoner.getHoTen());
        pst.setString(2, prisoner.getNgaySinh());
        pst.setString(3, prisoner.getToiDanh());
        pst.setString(4, prisoner.getKhuGiamGiu());
        pst.setString(5, prisoner.getTrangThai());
        pst.setString(6, prisoner.getMaPhamNhan());
        pst.setString(7, prisoner.getHinhAnh());
        return pst.executeUpdate() > 0;
    }

    public boolean delete(String maPhamNhan) throws SQLException {
        String sql = "DELETE FROM PhamNhan WHERE maPhamNhan = ?";

        Connection conn = DBConnection.getConnection();
        PreparedStatement pst = conn.prepareStatement(sql);
        pst.setString(1, maPhamNhan);

        return pst.executeUpdate() > 0;
    }

    public ArrayList<Prisoner> searchByName(String keyword) throws SQLException {
        ArrayList<Prisoner> list = new ArrayList<>();

        String sql = "SELECT * FROM PhamNhan WHERE hoTen LIKE ? OR maPhamNhan LIKE ?";

        Connection conn = DBConnection.getConnection();
        PreparedStatement pst = conn.prepareStatement(sql);

        pst.setString(1, "%" + keyword + "%");
        pst.setString(2, "%" + keyword + "%");

        ResultSet rs = pst.executeQuery();

        while (rs.next()) {
            Prisoner p = new Prisoner(
                    rs.getString("maPhamNhan"),
                    rs.getString("hoTen"),
                    rs.getString("ngaySinh"),
                    rs.getString("toiDanh"),
                    rs.getString("khuGiamGiu"),
                    rs.getString("trangThai"),
                    rs.getString("hinhAnh")
            );

            list.add(p);
        }

        return list;
    } 
}
