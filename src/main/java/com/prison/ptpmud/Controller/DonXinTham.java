/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.prison.ptpmud.Controller;

/**
 *
 * @author LOQ
 */
public class DonXinTham {
    String MaYC;
    String NgayXinTham;
    String CaTham;
    String NgayTao;
    String TrangThai;
    String LyDoPhanHoi;

    public DonXinTham(String MaYC, String NgayXinTham, String CaTham, String NgayTao, String TrangThai, String LyDoPhanHoi) {
        this.MaYC = MaYC;
        this.NgayXinTham = NgayXinTham;
        this.CaTham = CaTham;
        this.NgayTao = NgayTao;
        this.TrangThai = TrangThai;
        this.LyDoPhanHoi = LyDoPhanHoi;
    }

    public DonXinTham() {
    }

    public String getMaYC() {
        return MaYC;
    }

    public String getNgayXinTham() {
        return NgayXinTham;
    }

    public String getCaTham() {
        return CaTham;
    }

    public String getNgayTao() {
        return NgayTao;
    }

    public String getTrangThai() {
        return TrangThai;
    }

    public String getLyDoPhanHoi() {
        return LyDoPhanHoi;
    }
    
}
