package com.example.proiectJava.ui;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class SignUp extends JPanel {
    MainWindow parent;
    JLabel lEmail = new JLabel("Email:");
    JLabel lPassword = new JLabel("Password:");
    JLabel lRewritePassword = new JLabel("Rewrite password:");
    JTextField tfEmail = new JTextField(30);
    JPasswordField pfPassword = new JPasswordField(30);
    JPasswordField pfRewritePassword = new JPasswordField(30);
    JButton bRegister = new JButton("Register");
    JButton bLogin = new JButton("Login");
    Boolean isLogin;
    Image img;
    public SignUp(MainWindow parent, boolean isLogin) {
        this.parent = parent;
        this.isLogin = isLogin;

        ImageIcon image = new ImageIcon("src/main/resources/background.png");
        img = image.getImage();
        setLayout(new GridBagLayout());

        tfEmail.setMinimumSize(new Dimension(100, 30));
        pfPassword.setMinimumSize(new Dimension(100, 30));
        pfRewritePassword.setMinimumSize(new Dimension(100, 30));

        lEmail.setFont(new Font("Arial", Font.PLAIN, 20));
        lPassword.setFont(new Font("Arial", Font.PLAIN, 20));
        lRewritePassword.setFont(new Font("Arial", Font.PLAIN, 20));

        bRegister.addActionListener(e -> {
            if (getLogin()) {
                setLogin(false);
                InitUi();
                validate();
                repaint();
            } else {
                performRegisterAction(tfEmail.getText(), new String(pfPassword.getPassword()), new String(pfRewritePassword.getPassword()));
            }
        });

        bLogin.addActionListener(e -> {
            if (!getLogin()) {
                setLogin(true);
                InitUi();
                validate();
                repaint();
            } else {
                performSignInAction(tfEmail.getText(), new String(pfPassword.getPassword()));
            }
        });

        InitUi();
    }

    public Boolean getLogin() {
        return isLogin;
    }

    public void setLogin(Boolean login) {
        isLogin = login;
    }

    public void InitUi() {
        removeAll();

        tfEmail.setText("");
        pfPassword.setText("");
        pfRewritePassword.setText("");

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.WEST;
        constraints.insets = new Insets(10, 10, 10, 10);

        constraints.gridx = 0;
        constraints.gridy = 0;
        add(lEmail, constraints);

        constraints.gridx = 1;
        add(tfEmail, constraints);

        constraints.gridx = 0;
        constraints.gridy = 1;
        add(lPassword, constraints);

        constraints.gridx = 1;
        add(pfPassword, constraints);

        if (!isLogin) {
            constraints.gridx = 0;
            constraints.gridy = 2;
            add(lRewritePassword, constraints);

            constraints.gridx = 1;
            add(pfRewritePassword, constraints);
        }

        JPanel buttons = new JPanel(new FlowLayout());
        buttons.add(bLogin);
        buttons.add(bRegister, constraints);
        buttons.setOpaque(false);

        constraints.gridx = 0;
        constraints.gridy = isLogin ? 2 : 3;
        constraints.anchor = GridBagConstraints.CENTER;
        constraints.gridwidth = 2;
        add(buttons, constraints);
    }

    public void paintComponent(Graphics g) {
        g.drawImage(img, 0, 0, null);
    }

    private void performRegisterAction(String email, String password, String confirmPasswd) {
        try {
            String urlString = "http://localhost:8081/register";
            String fullUrl = urlString + "?email=" + email + "&passwd=" + password + "&confirmPasswd=" + confirmPasswd;
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

            if (responseCode == HttpURLConnection.HTTP_OK & response.toString().equals("User registered successfully!")) {
                setLogin(true);
                InitUi();
                validate();
                repaint();
            }
            JOptionPane.showMessageDialog(this, response);
        } catch (IOException e) {
            System.err.println(e.getMessage());
            JOptionPane.showMessageDialog(this, "An error occurred in registration");
        }
    }

    private void performSignInAction(String email, String passwd) {
        try {
            String urlString = "http://localhost:8081/signin";
            String fullUrl = urlString + "?email=" + email + "&passwd=" + passwd;
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

            if (responseCode == HttpURLConnection.HTTP_OK & response.toString().equals("User signed-in successfully!")) {
                parent.changeView("reserve");
            }
            JOptionPane.showMessageDialog(this, response);
        } catch (IOException e) {
            System.err.println(e.getMessage());
            JOptionPane.showMessageDialog(this, "An error occurred in sign-in");
        }
    }
}
