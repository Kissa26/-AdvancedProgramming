package com.example.proiectJava.ui;

import javax.swing.*;
import java.awt.*;

public class SignUp extends JPanel {
    JFrame parent;
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
    public SignUp(JFrame parent, boolean isLogin) {
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
                System.out.println("S a apasat register");
            }
        });

        bLogin.addActionListener(e -> {
            if (!getLogin()) {
                setLogin(true);
                InitUi();
                validate();
                repaint();
            } else {
                System.out.println("S a apasat login");
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
}
