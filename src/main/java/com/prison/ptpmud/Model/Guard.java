package com.prison.ptpmud.Model;

import java.util.Date;

public class Guard {
    private String maQN;
    private String hoTen;
    private Date ngaySinh;
    private String gioiTinh;
    private String soDienThoai;
    private String hinhAnh;

    public Guard() {
    }

    public Guard(String maQN, String hoTen, Date ngaySinh, String gioiTinh, String soDienThoai, String hinhAnh) {
        this.maQN = maQN;
        this.hoTen = hoTen;
        this.ngaySinh = ngaySinh;
        this.gioiTinh = gioiTinh;
        this.soDienThoai = soDienThoai;
        this.hinhAnh = hinhAnh;
    }

    public String getMaQN() {
        return maQN;
    }

    public void setMaQN(String maQN) {
        this.maQN = maQN;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public Date getNgaySinh() {
        return ngaySinh;
    }

    public void setNgaySinh(Date ngaySinh) {
        this.ngaySinh = ngaySinh;
    }

    public String getGioiTinh() {
        return gioiTinh;
    }

    public void setGioiTinh(String gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    public String getSoDienThoai() {
        return soDienThoai;
    }

    public void setSoDienThoai(String soDienThoai) {
        this.soDienThoai = soDienThoai;
    }

    public String getHinhAnh() {
        return hinhAnh;
    }

    public void setHinhAnh(String hinhAnh) {
        this.hinhAnh = hinhAnh;
    }
}
