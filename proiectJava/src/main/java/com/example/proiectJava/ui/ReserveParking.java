package com.example.proiectJava.ui;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

public class ReserveParking extends JPanel {
    JFrame parent;
    JPanel parkingAvailable = new JPanel(new FlowLayout());
    JButton bReserve = new JButton("Reserve spot in this parking");

    public ReserveParking(JFrame parent) {
        this.parent = parent;
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
    }

    public void InitUi(Map<String, Integer> parkings, Boolean isAlreadyParked) {
        for(int i = 0; i < parkings.size(); i++) {
            String[] strings;
        }
    }
}
