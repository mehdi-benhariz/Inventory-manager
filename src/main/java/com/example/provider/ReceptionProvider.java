package com.example.provider;

import com.example.Models.Reception;
import com.example.Models.ReceptionLine;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;

public class ReceptionProvider {
    public static Reception currentReception;
    public static ObservableList<Reception> receptions;
    public static ObservableList<ReceptionLine> currentLines;

    public static void setCurrentReception(Reception reception) {
        currentReception = reception;
    }

    public static void setReceptions(ObservableList<Reception> receptions) {
        ReceptionProvider.receptions = receptions;

    }

    public static void setCurrentLines(ObservableList<ReceptionLine> lines) {
        ReceptionProvider.currentLines = lines;
    }

    public static void clear() {
    }
}
