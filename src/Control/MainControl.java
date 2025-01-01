/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Control;

import Client.MainSystemUI;

/**
 *
 * @author OLIVER TAN ZU ZHUO
 */
public class MainControl {
    MainSystemUI mainUI = new MainSystemUI(this);    
    ProgControl progControl = new ProgControl();
    
    public static void main(String[] args) {
        MainControl mainControl = new MainControl();
        mainControl.run();
    }
    
    public void runSub(int choice) {
        switch(choice) {
            case 0 -> progControl.run();
        }
    }
    
    public void run() {
        mainUI.setVisible(true);
    }
}
