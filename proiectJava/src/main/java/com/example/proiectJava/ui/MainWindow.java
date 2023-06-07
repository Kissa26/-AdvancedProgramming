package com.example.proiectJava.ui;

import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;

@Component
public class MainWindow extends JFrame{
    CardLayout cl = new CardLayout();
    JPanel panel = new JPanel();
    ReserveParking reserveView = new ReserveParking(this);
    SignUp signUp = new SignUp(this, true);

    public MainWindow() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("SmartCity");
        panel.setLayout(cl);
        panel.setVisible(true);
        setSize(800, 800);
        panel.add(reserveView, "reserve");
        panel.add(signUp, "signUp");
        cl.show(panel, "signUp");
        add(panel);
    }

    public void changeView(String view) {
        cl.show(panel, view);
        validate();
        repaint();
    }
}
