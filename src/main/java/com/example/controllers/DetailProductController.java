package com.example.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.example.DAO.ProductDAO;
import com.example.DTO.ProductDTO;
import com.example.Models.Product;
import com.example.provider.ProductProvider;
import com.example.utils.Notifiar;
import com.example.utils.Shared;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
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
    @FXML
    private ComboBox<String> familyField;
    @FXML
    private TextField tvaField;
    @FXML
    private TextField margeField;
    @FXML
    private TextField ttcField;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        if (ProductProvider.currentProduct != null)
            initFields();
        familyField.setItems(Shared.productFamilies);
        initListners();

    }

    private void initFields() {
        refField.setText(ProductProvider.getCurrentProduct().getRef());
        nameField.setText(ProductProvider.getCurrentProduct().getName());
        quantityField.setText(String.valueOf(ProductProvider.getCurrentProduct().getQuantity()));
        priceField.setText(String.valueOf(ProductProvider.getCurrentProduct().getPrice()));
        familyField.setValue(ProductProvider.getCurrentProduct().getFamily());
        tvaField.setText(String.valueOf(ProductProvider.getCurrentProduct().getTVA()));
        margeField.setText(String.valueOf(ProductProvider.getCurrentProduct().getMarge()));
        ttcField.setText(String.valueOf(ProductProvider.getCurrentProduct().getPriceTTC()));

    }

    private void clearFields() {
        refField.setText("");
        nameField.setText("");
        quantityField.setText("");
        priceField.setText("");
        // set family field from list
        familyField.setValue("");
        tvaField.setText("");
        margeField.setText("");
        ttcField.setText("");
    }

    private void initListners() {
        tvaField.textProperty().addListener((observable, oldValue, newValue) -> {
            // oblige to enter a number
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
            if (!newValue.matches("\\d*(\\.\\d*)?"))
                tvaField.setText(newValue.replaceAll("[^\\d*(\\.\\d*)?]", ""));
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
                Byte.parseByte(margeField.getText()),
                familyField.getValue());

        Boolean res = false;
        if (ProductProvider.currentProduct == null)
            res = ProductDAO.addProduct(p);
        else
            // p.setId(ProductProvider.getCurrentProduct().getId());
            res = ProductDAO.updateProduct(p, ProductProvider.getCurrentProduct().getRef());

        if (res) {
            Notifiar.showInfoMsg("succès", "Modification effectuée avec succès");
            if (ProductProvider.currentProduct != null)
                exitDetailProduct();
            else {
                clearFields();
                ProductProvider.products.add(new ProductDTO(p));
            }
        } else
            Notifiar.showErrorMsg("erreur", "Erreur lors de la modification");

    }

    public void discardChange() {
        if (ProductProvider.currentProduct != null)
            initFields();
        else
            clearFields();
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
