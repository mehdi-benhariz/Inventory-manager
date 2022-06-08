package com.example.utils;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class Notifiar {
    public static void showErrorMsg(String title, String msg) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(title);

        // Header Text: null
        alert.setHeaderText(null);
        alert.setContentText(msg);

        alert.showAndWait();
    }

    public static void showInfoMsg(String title, String msg) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);

        // Header Text: null\
        alert.setHeaderText(null);
        alert.setContentText(msg);

        alert.showAndWait();
    }
}
