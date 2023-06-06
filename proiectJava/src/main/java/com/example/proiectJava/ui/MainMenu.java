package com.example.proiectJava.ui;

import javax.swing.*;
import java.awt.*;

public class MainMenu extends JPanel{
    JButton pbAvailable = new JButton("Find available parking");
    JButton pbReserve = new JButton("Reserve parking spot");

    public MainMenu() {
        add(pbAvailable, BorderLayout.CENTER);
        add(pbReserve, BorderLayout.CENTER);
    }
}
