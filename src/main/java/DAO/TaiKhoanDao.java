/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import com.prison.ptpmud.Main.userNT_session;
import database.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.mindrot.jbcrypt.BCrypt;

/**
 *
 * @author LOQ
 */
public class TaiKhoanDao {

    public boolean KiemTraTaiKhoan(String taikhoan, String pass, String vaitro) throws SQLException {
        
            Connection conn = DBConnection.getConnection();
            boolean isLoginSuccess=false;
            PreparedStatement pst = null;
            
            ResultSet rs = null;
            
            if (conn != null) {
                String sql = "select * from TaiKhoan where taikhoan=? and vaitro=?";
                pst = conn.prepareStatement(sql);
                
                
                pst.setString(1, taikhoan);
                pst.setString(2, vaitro);
                rs = pst.executeQuery();
                
                if (rs.next()) {
                    String passDB = rs.getString("MatKhau");
                    if(BCrypt.checkpw(pass, passDB)){
                        isLoginSuccess=true;
                        String query_cccdNT="select nt.CCCD from NguoiThan as nt join PhamNhan as pn on pn.MaPhamNhan=nt.MaPhamNhan where taikhoan=?";
                        PreparedStatement pst2=conn.prepareStatement(query_cccdNT);
                        pst2.setString(1, taikhoan);
                        ResultSet rs2=pst2.executeQuery();
                        if(rs2.next()){
                            userNT_session.setCurrentCCCD(rs2.getString("cccd"));
                        }
                    }
                }
                
                
            }
        
        return isLoginSuccess;
        }
        
    }

