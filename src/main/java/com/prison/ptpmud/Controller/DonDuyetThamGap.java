/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.prison.ptpmud.Controller;

/**
 *
 * @author LOQ
 */
public class DonDuyetThamGap {

    String TenNT;
    String QuanHe;
    String MaPN;
    String TenPN;
    String NgayHen;
    String CaTham;

    public DonDuyetThamGap(String TenNT, String QuanHe, String MaPN, String TenPN, String NgayHen, String CaTham) {
        this.TenNT = TenNT;
        this.QuanHe = QuanHe;
        this.MaPN = MaPN;
        this.TenPN = TenPN;
        this.NgayHen = NgayHen;
        this.CaTham = CaTham;
    }

    public String getTenNT() {
        return TenNT;
    }

    public String getQuanHe() {
        return QuanHe;
    }

    public String getMaPN() {
        return MaPN;
    }

    public String getTenPN() {
        return TenPN;
    }

    public String getNgayHen() {
        return NgayHen;
    }

    public String getCaTham() {
        return CaTham;
    }

}
