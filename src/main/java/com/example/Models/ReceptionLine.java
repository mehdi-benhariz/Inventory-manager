package com.example.Models;

import com.example.provider.ProductProvider;

import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

public class ReceptionLine {
    ChoiceBox<String> ref;

    TextField quantity;
    TextField price;

    // default constructor
    public ReceptionLine() {
        ref = new ChoiceBox<String>();
        ref.setItems(ProductProvider.getRefs());
        quantity = new TextField();
        price = new TextField();
        this.initListners();

    }

    public ReceptionLine(ChoiceBox<String> ref, TextField quantity, TextField price) {
        this.ref = ref;
        this.quantity = quantity;
        this.price = price;
        this.initListners();

    }

    public ReceptionLine(String ref, int quantity, float price) {
        // create a new choicebox with the ref
        this();
        this.ref.setValue(ref);
        this.quantity.setText(String.valueOf(quantity));
        this.price.setText(String.valueOf(price));

    }

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

    // default listener
    public void initListners() {
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

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ReceptionLine) {
            ReceptionLine line = (ReceptionLine) obj;
            return (line.getRef().getValue().equals(this.getRef().getValue())
                    && line.getQuantity().getText().equals(this.getQuantity().getText())
                    && line.getPrice().getText().equals(this.getPrice().getText()));
        }
        return false;
    }

}
