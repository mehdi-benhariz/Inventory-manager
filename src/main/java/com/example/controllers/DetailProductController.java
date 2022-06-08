package com.example.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.example.DAO.ProductDAO;
import com.example.Models.Product;
import com.example.provider.ProductProvider;
import com.example.utils.Notifiar;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class DetailProductController implements Initializable {
    @FXML
    private TextField refField;
    @FXML
    private TextField nameField;
    @FXML
    private TextField quantityField;
    @FXML
    private TextField priceField;
    // @FXML
    // private ChoiceBox<String> familyField;
    @FXML
    private TextField tvaField;
    @FXML
    private TextField margeField;
    @FXML
    private TextField ttcField;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        initFields();
        initListners();

    }

    private void initFields() {
        refField.setText(ProductProvider.getCurrentProduct().getRef());
        nameField.setText(ProductProvider.getCurrentProduct().getName());
        quantityField.setText(String.valueOf(ProductProvider.getCurrentProduct().getQuantity()));
        priceField.setText(String.valueOf(ProductProvider.getCurrentProduct().getPrice()));
        // familyField.setValue(ProductProvider.getCurrentProduct().getFamily());
        tvaField.setText(String.valueOf(ProductProvider.getCurrentProduct().getTVA()));
        margeField.setText(String.valueOf(ProductProvider.getCurrentProduct().getMarge()));
        ttcField.setText(String.valueOf(ProductProvider.getCurrentProduct().getPriceTTC()));

    }

    private void initListners() {
        tvaField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*"))
                tvaField.setText(newValue.replaceAll("[^\\d]", ""));

            if (newValue.length() > 0)
                ttcField.setText(String.valueOf(
                        Float.parseFloat(priceField.getText()) * (1 + Float.parseFloat(tvaField.getText()) / 100)
                                * (1 + Float.parseFloat(margeField.getText()) / 100)));

        });
        margeField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*"))
                margeField.setText(newValue.replaceAll("[^\\d]", ""));
            if (newValue.length() > 0)
                ttcField.setText(String.valueOf(
                        Float.parseFloat(priceField.getText()) * (1 + Float.parseFloat(tvaField.getText()) / 100)
                                * (1 + Float.parseFloat(margeField.getText()) / 100)));

        });
        priceField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*"))
                priceField.setText(newValue.replaceAll("[^\\d]", ""));
            if (newValue.length() > 0)
                ttcField.setText(String.valueOf(
                        Float.parseFloat(priceField.getText()) * (1 + Float.parseFloat(tvaField.getText()) / 100)
                                * (1 + Float.parseFloat(margeField.getText()) / 100)));

        });
    }

    private void exitDetailProduct() {
        Stage stage = (Stage) refField.getScene().getWindow();
        stage.close();
    }

    public void saveChange() {
        // todo add family field
        Product p = new Product(refField.getText(), nameField.getText(),
                Integer.parseInt(quantityField.getText()),
                Float.parseFloat(priceField.getText()),
                Byte.parseByte(tvaField.getText()),
                Byte.parseByte(margeField.getText()));

        Boolean res = ProductDAO.updateProduct(p, ProductProvider.getCurrentProduct().getRef());
        if (res) {
            Notifiar.showInfoMsg("succès", "Modification effectuée avec succès");
            exitDetailProduct();
        } else {
            Notifiar.showErrorMsg("erreur", "Erreur lors de la modification");
        }
    }

    public void discardChange() {
        initFields();
    }

    public void removeProduct() {
        Boolean res = ProductDAO.removeProduct(ProductProvider.getCurrentProduct().getRef());
        if (res) {
            exitDetailProduct();
            Notifiar.showInfoMsg("Product removed", "Product removed successfully");
        } else
            Notifiar.showErrorMsg("Product not removed", "Product not removed");

    }
}
