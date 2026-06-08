/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.prison.ptpmud.Main;

/**
 *
 * @author LOQ
 */
public class userNT_session {
    public static String currentCCCD;

    public userNT_session() {
    }

    public static String getCurrentCCCD() {
        return currentCCCD;
    }

    public static void setCurrentCCCD(String currentCCCD) {
        userNT_session.currentCCCD = currentCCCD;
    }

    public void clear_Session(){
        currentCCCD=null;
    }
}
