package com.example.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.example.App;
import com.example.DAO.ProductDAO;
import com.example.DTO.ProductDTO;
import com.example.Models.Product;
import com.example.provider.ProductProvider;

import javafx.beans.Observable;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ProductsController implements Initializable {
    @FXML
    TableView<ProductDTO> ProductTable;
    @FXML
    TableColumn<ProductDTO, String> ref;
    @FXML
    TableColumn<ProductDTO, String> name;
    @FXML
    TableColumn<ProductDTO, Double> quantity;
    @FXML
    TableColumn<ProductDTO, String> family;
    @FXML
    TableColumn<ProductDTO, Float> price;
    @FXML
    TableColumn<ProductDTO, Float> priceTTC;
    @FXML
    TextField searchField;

    private void updateTable(ObservableList<ProductDTO> products) {
        ref.setCellValueFactory(new PropertyValueFactory<ProductDTO, String>("ref"));
        name.setCellValueFactory(new PropertyValueFactory<ProductDTO, String>("name"));
        price.setCellValueFactory(new PropertyValueFactory<ProductDTO, Float>("price"));
        family.setCellValueFactory(new PropertyValueFactory<ProductDTO, String>("family"));
        quantity.setCellValueFactory(new PropertyValueFactory<ProductDTO, Double>("quantity"));
        priceTTC.setCellValueFactory(new PropertyValueFactory<ProductDTO, Float>("priceTTC"));
        // add tow buttons to actions column

        ProductTable.setItems(products);

    }

    @FXML
    void searchProducts() {
        String query = searchField.getText();
        ObservableList<ProductDTO> res = ProductDAO.search(query);
        updateTable(res);
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        ObservableList<ProductDTO> res = ProductDAO.getStock();
        updateTable(res);
        // add event listern on click on row
        ProductTable.setRowFactory(tv -> {
            TableRow<ProductDTO> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    ProductDTO rowData = row.getItem();
                    System.out.println("Double click on: " + rowData);
                    ProductProvider.setCurrentProduct(row.getItem());
                    // open new window with detail product
                    openDetailProduct();
                }
            });
            return row;
        });
    }

    private void openDetailProduct() {
        // open new window with detail product
        try {
            Parent root = FXMLLoader.load(App.class.getResource("DetailProduct.fxml"));
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void backToMenu() {
        try {
            App.setRoot("Home");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
