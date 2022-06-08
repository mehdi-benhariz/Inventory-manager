package com.example.Models;

import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

public class ReceptionLine {
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

    public ReceptionLine(ChoiceBox<String> ref, TextField quantity, TextField price) {
        this.ref = ref;
        this.quantity = quantity;
        this.price = price;
    }

}
