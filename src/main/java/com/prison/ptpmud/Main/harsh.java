/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.prison.ptpmud.Main;

import org.mindrot.jbcrypt.BCrypt;

/**
 *
 * @author LOQ
 */
public class harsh {
    public static String TaoHamBam(String pass){
        String harshPass= BCrypt.hashpw(pass, BCrypt.gensalt());
        return harshPass;
    }
    public static void main(String[] args) {
        String pass="123456";
        String newPass= TaoHamBam(pass);
        System.out.println(newPass);
    }
}
