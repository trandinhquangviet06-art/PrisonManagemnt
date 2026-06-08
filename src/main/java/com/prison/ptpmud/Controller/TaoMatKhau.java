/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.prison.ptpmud.Controller;

import org.mindrot.jbcrypt.BCrypt;

/**
 *
 * @author LOQ
 */
public class TaoMatKhau {
    public static void main(String[] args) {
        // Sinh mã Hash chuẩn cho mật khẩu "1987"
        String hashChuan = BCrypt.hashpw("1987", BCrypt.gensalt());
        System.out.println("Mã Hash mới của 1987 là:");
        System.out.println(hashChuan);
    }
}
