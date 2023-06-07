package com.example.proiectJava.ui;

import com.example.proiectJava.business.Parking;
import com.example.proiectJava.entity.ParkingEntity;
import com.example.proiectJava.entity.ReservationEntity;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Random;


public class ReserveParking extends JPanel {
    MainWindow parent;
    JPanel parkingAvailable = new JPanel(new FlowLayout());
    JButton bReserve = new JButton();
    Boolean isAlreadyReserved;
    Image img;
    ButtonGroup bg;
    double lon;
    double lat;
    String idUser;
    String idParking;


    public ReserveParking(MainWindow parent) {
        Random rand = new Random();
        lon = rand.nextDouble() * 100;
        lat = rand.nextDouble() * 100;

        this.parent = parent;
        isAlreadyReserved = SecurityContextHolder.getContext().getAuthentication() != null;

        ImageIcon image = new ImageIcon("src/main/resources/background.png");
        img = image.getImage();
        setLayout(new GridBagLayout());
        bReserve.addActionListener(e -> {
            if(isAlreadyReserved) {
                performCancelReservation();
            }
            else {
                performCreateReservation();
            }
        });
    }

    public void InitUi() {
        parkingAvailable.removeAll();
        removeAll();
        bg = new ButtonGroup();

        List<Parking> parkings = performNearestParkingsAction(lat, lon);

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.CENTER;
        constraints.insets = new Insets(10, 10, 10, 10);
        constraints.gridx = 0;

        for(Parking parking : parkings) {
            String label = parking.getName() + " " + (parking.getMaxParkingLot()- parking.getCurrentParkingLot()) + "/" + parking.getMaxParkingLot();
            JToggleButton tbParking = new JToggleButton(label);
            tbParking.setActionCommand(parking.getName());
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
        if(isAlreadyReserved) {
            bReserve.setText("Click here to free the spot");
        }
        else {
            bReserve.setText("Reserve spot in this parking");
        }
    }

    public void paintComponent(Graphics g) {
        g.drawImage(img, 0, 0, null);
    }

    private List<Parking> performNearestParkingsAction(double lat, double lon) {
        List<Parking> output = new ArrayList<>();
        try {
            String urlString = "http://localhost:8081/parkings";
            String fullUrl = urlString + "?lat=" + lat + "&lon=" + lon;
            URL url = new URL(fullUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);

            int responseCode = connection.getResponseCode();

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            if (responseCode == HttpURLConnection.HTTP_OK) {
                String jsonResponse = response.toString();
                ObjectMapper objectMapper = new ObjectMapper();
                output = objectMapper.readValue(jsonResponse, new TypeReference<List<Parking>>() {});
            }
            connection.disconnect();
            return output;
        } catch (IOException e) {
            System.err.println(e.getMessage());
            JOptionPane.showMessageDialog(this, "An error occurred getting the parkings");
        }
        return output;
    }

    private void performCancelReservation() {
        String idReservation = performGetActiveReservation();
        if(idReservation.isEmpty())
            return;
        try {
            String urlString = "http://localhost:8081/reservations";
            String fullUrl = urlString + "?idReservation=" + idReservation;
            URL url = new URL(fullUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("PUT");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);

            int responseCode = connection.getResponseCode();

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            if (responseCode == HttpURLConnection.HTTP_OK) {
                isAlreadyReserved = false;
                InitUi();
                validate();
                repaint();
            } else {
                JOptionPane.showMessageDialog(this, response.toString());
            }
            connection.disconnect();
        } catch (IOException e) {
            System.err.println(e.getMessage());
            JOptionPane.showMessageDialog(this, "An error occurred in reservation");
        }
    }

    private void performCreateReservation() {
        ButtonModel selectedButton = bg.getSelection();
        if (selectedButton == null) {
            JOptionPane.showMessageDialog(this, "Please select the parking!");
            return;
        }
        List<ParkingEntity> parkings = performGetParkingByName(selectedButton.getActionCommand());
        if (parkings.size() > 1) {
            JOptionPane.showMessageDialog(this, "Too many parkings with the same name");
            return;
        }
        String userId = "11881ed0-624c-431d-98a0-261434b7fb59";

        try {
            String urlString = "http://localhost:8081/reservations";
            String fullUrl = urlString + "?idUser=" + userId + "&idParking=" + parkings.get(0).getId();
            URL url = new URL(fullUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);

            int responseCode = connection.getResponseCode();

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            if (responseCode == HttpURLConnection.HTTP_OK) {
                isAlreadyReserved = true;
                InitUi();
                validate();
                repaint();
                idUser = userId;
                idParking = parkings.get(0).getId();
            } else {
                JOptionPane.showMessageDialog(this, response.toString());
            }
            connection.disconnect();
        } catch (IOException e) {
            System.err.println(e.getMessage());
            JOptionPane.showMessageDialog(this, "An error occurred in reservation");
        }
    }


    private List<ParkingEntity> performGetParkingByName(String name) {
        List<ParkingEntity> output = new ArrayList<>();
        try {
            String urlString = "http://localhost:8081/parkings/name";
            String fullUrl = urlString + "?name=" + name;
            URL url = new URL(fullUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);

            int responseCode = connection.getResponseCode();

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            if (responseCode == HttpURLConnection.HTTP_OK) {
                String jsonResponse = response.toString();
                ObjectMapper objectMapper = new ObjectMapper();
                output = objectMapper.readValue(jsonResponse, new TypeReference<List<ParkingEntity>>() {});
            }
            connection.disconnect();
            return output;
        } catch (IOException e) {
            System.err.println(e.getMessage());
            JOptionPane.showMessageDialog(this, "An error occurred getting the parkings");
        }
        return output;
    }

    private String performGetActiveReservation() {
        try {
            String urlString = "http://localhost:8081/reservations";
            String fullUrl = urlString + "?idUser=" + idUser + "&idParking=" + idParking + "&isActive=" + true;
            URL url = new URL(fullUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);

            int responseCode = connection.getResponseCode();

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            if (responseCode == HttpURLConnection.HTTP_OK) {
                return String.valueOf(response);
            } else {
                JOptionPane.showMessageDialog(this, response.toString());
            }
            connection.disconnect();
        } catch (IOException e) {
            System.err.println(e.getMessage());
            JOptionPane.showMessageDialog(this, "An error occurred in reservation");
        }
        return "";
    }
}

