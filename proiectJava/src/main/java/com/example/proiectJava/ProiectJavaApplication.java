package com.example.proiectJava;

import com.example.proiectJava.ui.MainWindow;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import java.awt.*;

@SpringBootApplication
@EnableWebSecurity
public class ProiectJavaApplication {

    public static void main(String[] args) {
        var ctx = new SpringApplicationBuilder(ProiectJavaApplication.class)
                .headless(false).run(args);

        EventQueue.invokeLater(() -> {

            var ex = ctx.getBean(MainWindow.class);
            ex.setVisible(true);
        });
    }
}

