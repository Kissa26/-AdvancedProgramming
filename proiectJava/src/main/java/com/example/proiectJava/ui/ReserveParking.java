package com.example.proiectJava.ui;

import com.example.proiectJava.entity.ParkingEntity;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ReserveParking extends JPanel {
    MainWindow parent;
    JPanel parkingAvailable = new JPanel(new FlowLayout());
    JButton bReserve = new JButton();
    Boolean isAlreadyReserved;
    Image img;


    public ReserveParking(MainWindow parent) {
        this.parent = parent;

        ImageIcon image = new ImageIcon("src/main/resources/background.png");
        img = image.getImage();
        setLayout(new GridBagLayout());
    }

    public void InitUi(List<ParkingEntity> parkings, Boolean isAlreadyParked) {
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.CENTER;
        constraints.insets = new Insets(10, 10, 10, 10);
        constraints.gridx = 0;

        isAlreadyReserved = isAlreadyParked;
        ButtonGroup bg = new ButtonGroup();
        for(ParkingEntity parking : parkings) {
            String label = parking.getName() + " " + parking.getCurrentParkingLot() + "/" + parking.getMaxParkingLot();
            JToggleButton tbParking = new JToggleButton(label);
            bg.add(tbParking);
            parkingAvailable.add(tbParking);
        }
        JScrollPane scrollPanel = new JScrollPane(parkingAvailable);
        scrollPanel.setMinimumSize(new Dimension(750, 60));

        parkingAvailable.setOpaque(false);
        scrollPanel.getViewport().setOpaque(false);
        scrollPanel.setOpaque(false);

        constraints.gridy = 0;
        add(scrollPanel, constraints);

        constraints.gridy = 1;
        add(bReserve, constraints);
        if(isAlreadyParked) {
            bReserve.setText("Click here to free the spot");
        }
        else {
            bReserve.setText("Reserve spot in this parking");
        }
    }

    public void paintComponent(Graphics g) {
        g.drawImage(img, 0, 0, null);
    }
}
