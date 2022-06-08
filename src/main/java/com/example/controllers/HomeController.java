package com.example.controllers;

import java.io.IOException;

import com.example.App;

import javafx.fxml.FXML;

public class HomeController {
    private void navigateTo(String fxml) {
        try {
            App.setRoot(fxml);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void navigateToProducts() {
        navigateTo("Products");
    }

    @FXML
    void navigateToReceptions() {
        navigateTo("Receptions");
    }

    @FXML
    void navigateToVentes() {
        navigateTo("Ventes");
    }
}
