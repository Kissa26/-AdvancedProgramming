package com.example.proiectJava.ui;

import javax.swing.*;
import java.awt.*;

public class MainWindow extends JFrame{
    CardLayout cl = new CardLayout();
    JPanel panel = new JPanel();
    ReserveParking reserveView = new ReserveParking(this);
    MainMenu menuView = new MainMenu();
    SignUp signUp = new SignUp(this, true);

    public MainWindow() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("SmartCity");
        panel.setLayout(cl);
        panel.setVisible(true);
        setSize(800, 800);
        panel.add(menuView, "menu");
        panel.add(reserveView, "reserve");
        panel.add(signUp, "signUp");
        cl.show(panel, "signUp");
        add(panel);
    }
}