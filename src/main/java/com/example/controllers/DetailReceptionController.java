package com.example.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import com.example.DAO.ProductDAO;
import com.example.DAO.ReceptionDAO;
import com.example.DTO.ProductDTO;
import com.example.Models.Product;
import com.example.Models.Reception;
import com.example.Models.ReceptionLine;
import com.example.provider.ProductProvider;
import com.example.provider.ReceptionProvider;
import com.example.utils.Notifiar;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;

import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.shape.Line;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class DetailReceptionController implements Initializable {
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
    ObservableList<ReceptionLine> lines = FXCollections.observableArrayList();

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        if (ReceptionProvider.currentReception != null)
            initReceptionFields();
        initFields();
        initListner();
    }

    private void initReceptionFields() {
        refField.setText(ReceptionProvider.currentReception.getRef());

        lines = ReceptionDAO.getLinesByRef(ReceptionProvider.currentReception.getRef());
        ReceptionProvider.setCurrentLines(lines);
        receptionLines.setItems(lines);
        calcTotal();
    }

    private void calcTotal() {
        float sum = 0.0f;
        for (ReceptionLine line : lines)
            sum += Integer.parseInt(line.getQuantity().getText()) * Float.parseFloat(line.getPrice().getText());
        totalField.setText(String.valueOf(sum));
    }

    private void initListner() {
        receptionLines.setOnMouseClicked(event -> {
            calcTotal();
        });

    }

    private void initFields() {

        ref.setCellValueFactory(new PropertyValueFactory<ReceptionLine, ChoiceBox<String>>("ref"));
        quantity.setCellValueFactory(new PropertyValueFactory<ReceptionLine, TextField>("quantity"));
        price.setCellValueFactory(new PropertyValueFactory<ReceptionLine, TextField>("price"));
        refField.setText("");

        receptionLines.setItems(lines);
        // change value of ref field when ref is changed

    }

    private void updateList() {
        for (ReceptionLine line : lines) {
            ObservableList<String> refs = ProductProvider.getRefs();
            // compare the old list with the new one
            if (!line.getRef().getItems().equals(refs))
                line.getRef().setItems(ProductProvider.getRefs());
        }
    }

    @FXML
    void saveChanges() {
        if (ReceptionProvider.currentReception != null)
            editReception();
        else
            createReception();
        clear();
    }

    private void editReception() {
        String oldRef = ReceptionProvider.currentReception.getRef();
        if (!refField.getText().equals(oldRef)) {
            // change ref in reception table
            ReceptionDAO.updateRef(oldRef, refField.getText());
        }
        // update reception lines
        for (int i = 0; i < lines.size(); i++) {
            ReceptionLine newLine = lines.get(i);
            ReceptionLine oldLine = ReceptionProvider.currentLines.get(i);
            if (!oldLine.equals(newLine)) {
                ReceptionDAO.updateLine(refField.getText(), oldLine.getRef().getValue(), newLine);
            }
        }

        Notifiar.showInfoMsg("modification succes", "Reception updated successfully");
    }

    private void createReception() {
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
        line.getRef().setItems(ProductProvider.getRefs());
        lines.add(line);
    }

    @FXML
    void openCreateProduct() {

    }

    private void clear() {
        ReceptionProvider.clear();
    }

}
