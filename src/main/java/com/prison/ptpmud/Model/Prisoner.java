/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.prison.ptpmud.Model;

/**
 *
 * @author Admin
 */
public class Prisoner {
    private String maPhamNhan;
    private String hoTen;
    private String ngaySinh;
    private String toiDanh;
    private String khuGiamGiu;
    private String trangThai;
    private String hinhAnh; // Thuộc tính lưu đường dẫn ảnh
    public Prisoner() {
    }

    public Prisoner(String maPhamNhan, String hoTen, String ngaySinh,
                    String toiDanh, String khuGiamGiu, String trangThai,String hinhAnh) {
        this.maPhamNhan = maPhamNhan;
        this.hoTen = hoTen;
        this.ngaySinh = ngaySinh;
        this.toiDanh = toiDanh;
        this.khuGiamGiu = khuGiamGiu;
        this.trangThai = trangThai;
        this.hinhAnh = hinhAnh;
    }

    public String getMaPhamNhan() {
        return maPhamNhan;
    }

    public void setMaPhamNhan(String maPhamNhan) {
        this.maPhamNhan = maPhamNhan;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public String getNgaySinh() {
        return ngaySinh;
    }

    public void setNgaySinh(String ngaySinh) {
        this.ngaySinh = ngaySinh;
    }

    public String getToiDanh() {
        return toiDanh;
    }

    public void setToiDanh(String toiDanh) {
        this.toiDanh = toiDanh;
    }

    public String getKhuGiamGiu() {
        return khuGiamGiu;
    }

    public void setKhuGiamGiu(String khuGiamGiu) {
        this.khuGiamGiu = khuGiamGiu;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }
    public String getHinhAnh() {
        return hinhAnh;
    }

    public void setHinhAnh(String hinhAnh) {
        this.hinhAnh = hinhAnh;
    }
}
