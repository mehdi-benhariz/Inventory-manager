package com.example.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import com.example.App;
import com.example.DAO.ProductDAO;
import com.example.DAO.ReceptionDAO;
import com.example.DTO.ProductDTO;
import com.example.Models.Product;
import com.example.Models.Reception;
import com.example.Models.ReceptionLine;
import com.example.provider.ProductProvider;
import com.example.utils.Notifiar;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AddReceptionController implements Initializable {
    @FXML
    private TableView<ReceptionLine> receptionLines;
    @FXML
    private TableColumn<ReceptionLine, ChoiceBox<String>> ref;
    @FXML
    private TableColumn<ReceptionLine, TextField> quantity;
    @FXML
    private TableColumn<ReceptionLine, TextField> price;
    @FXML
    TextField refField;
    @FXML
    TextField totalField;
    ObservableList<String> refs;
    ObservableList<ReceptionLine> lines = FXCollections.observableArrayList();

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        initFields();
        initListner();
    }

    private void calcTotal() {
        float sum = 0.0f;
        for (ReceptionLine line : lines)
            sum += Integer.parseInt(line.getQuantity().getText()) * Float.parseFloat(line.getPrice().getText());

        totalField.setText(String.valueOf(sum));
    }

    private void initListner() {
        // change total value when change quantity and price

        quantity.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*"))
                quantity.setText(newValue.replaceAll("[^\\d]", ""));
            System.out.println(newValue);
            if (newValue.length() > 0)
                calcTotal();

        });
        price.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*"))
                price.setText(newValue.replaceAll("[^\\d]", ""));

            if (newValue.length() > 0)
                calcTotal();

        });

        // }

    }

    private void initFields() {
        refs = ReceptionDAO.getAllRef();
        ref.setCellValueFactory(new PropertyValueFactory<ReceptionLine, ChoiceBox<String>>("ref"));
        quantity.setCellValueFactory(new PropertyValueFactory<ReceptionLine, TextField>("quantity"));
        price.setCellValueFactory(new PropertyValueFactory<ReceptionLine, TextField>("price"));
        refField.setText("");

        receptionLines.setItems(lines);
        addLine();

    }

    private void updateList() {
        for (ReceptionLine line : lines)
            // compare the old list with the new one
            if (!line.getRef().getItems().equals(refs))
                line.getRef().setItems(refs);

    }

    @FXML
    void addReception() {
        Reception r = new Reception(refField.getText());
        Boolean res = ReceptionDAO.addReception(r);
        if (res) {
            // create receptions_products
            for (ReceptionLine line : lines) {
                ReceptionDAO.addReceptionProduct(r.getRef(), line);
                // add quantity to products
                for (ProductDTO p : ProductProvider.products)
                    if (p.getRef().equals(line.getRef().getValue())) {
                        int addedQt = line.getQuantity().getText().isEmpty() ? 0
                                : Integer.parseInt(line.getQuantity().getText());
                        p.setQuantity(p.getQuantity() + addedQt);
                        ProductDAO.updateQuantity(p.getRef(), p.getQuantity());
                    }
            }
            Notifiar.showInfoMsg("succes", "Reception added successfully");
        } else
            Notifiar.showErrorMsg("erreur", "erreur lors la creation de la reception");
    }

    @FXML
    void addLine() {
        ReceptionLine line = new ReceptionLine(new ChoiceBox<String>(), new TextField(), new TextField());
        line.getRef().setItems(refs);
        lines.add(line);
    }

    @FXML
    void openCreateProduct() {

    }

}
