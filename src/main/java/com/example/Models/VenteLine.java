package com.example.Models;

import com.example.provider.ProductProvider;

import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

public class VenteLine {
    ChoiceBox<String> ref;

    TextField quantity;
    TextField price;

    public ChoiceBox<String> getRef() {
        return ref;
    }

    public void setRef(ChoiceBox<String> ref) {
        this.ref = ref;
    }

    public TextField getQuantity() {
        return quantity;
    }

    public void setQuantity(TextField quantity) {
        this.quantity = quantity;
    }

    public TextField getPrice() {
        return price;
    }

    public void setPrice(TextField price) {
        this.price = price;
    }

    public VenteLine(ChoiceBox<String> ref, TextField quantity, TextField price) {
        this.ref = ref;
        this.quantity = quantity;
        this.price = price;
        // add a listener to quantity field
        quantity.textProperty().addListener((observable, oldValue, newValue) -> {
            // check if newValue is a number
            if (!newValue.matches("\\d*")) {
                quantity.setText(oldValue);
            }
        });
        // check if price is a float
        price.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*(\\.\\d*)?"))
                price.setText(newValue.replaceAll("[^\\d*(\\.\\d*)?]", ""));
        });
        // set price when ref is changed
        ref.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                Product p = ProductProvider.getProduct(newValue);
                price.setText(String.valueOf(p.getPrice()));
            }
        });
    }

}
