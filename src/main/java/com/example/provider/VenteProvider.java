package com.example.provider;

import com.example.Models.Vente;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class VenteProvider {
    public static Vente currentVente;
    public static ObservableList<Vente> ventes = FXCollections.observableArrayList();

    public static void setCurrentVente(Vente vente) {
        currentVente = vente;
    }

    public static void setVentes(ObservableList<Vente> ventes) {
        VenteProvider.ventes = ventes;

    }

}
