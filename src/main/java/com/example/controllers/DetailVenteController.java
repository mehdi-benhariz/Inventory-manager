package com.example.controllers;

import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;

import com.example.App;
import com.example.DAO.ProductDAO;
import com.example.DAO.ReceptionDAO;
import com.example.DAO.VenteDAO;
import com.example.DTO.ProductDTO;
import com.example.Models.Reception;
import com.example.Models.Vente;
import com.example.Models.VenteLine;
import com.example.provider.ProductProvider;
import com.example.provider.VenteProvider;
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

public class DetailVenteController implements Initializable {
    @FXML
    private TableView<VenteLine> venteTable;
    @FXML
    private TableColumn<VenteLine, ChoiceBox<String>> ref;
    @FXML
    private TableColumn<VenteLine, TextField> quantity;
    @FXML
    private TableColumn<VenteLine, TextField> price;
    private ObservableList<VenteLine> lines = FXCollections.observableArrayList();
    @FXML
    TextField refField;
    @FXML
    TextField totalField;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        // TODO Auto-generated method stub
        initFields();
        initListner();
        if (VenteProvider.currentVente != null)
            initVenteFields();
    }

    private void calcTotal() {
        float sum = 0.0f;
        for (VenteLine line : lines)
            sum += Integer.parseInt(line.getQuantity().getText()) * Float.parseFloat(line.getPrice().getText());
        totalField.setText(String.valueOf(sum));
    }

    private void initListner() {
        venteTable.setOnMouseClicked(event -> {
            calcTotal();
        });
    }

    private void initVenteFields() {
        refField.setText(VenteProvider.currentVente.getRef());

    }

    private void initFields() {

        // lines =VenteDAO.get
        ref.setCellValueFactory(new PropertyValueFactory<>("ref"));
        quantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        price.setCellValueFactory(new PropertyValueFactory<>("price"));
        refField.setText("");
        venteTable.setItems(lines);
        addLine();
    }

    @FXML
    void addLine() {
        VenteLine line = new VenteLine(new ChoiceBox<String>(), new TextField(), new TextField());
        line.getRef().setItems(ProductProvider.getRefs());
        lines.add(line);
    }

    @FXML
    void openCreateProduct() {

    }

    private void createVente() {
        Vente v = new Vente(refField.getText(), new Date(System.currentTimeMillis()));
        Boolean res = VenteDAO.addVente(v);
        if (res) {
            for (VenteLine line : lines) {
                VenteDAO.AddVenteProduct(v.getRef(), line);
                // sub quantity from product
                for (ProductDTO p : ProductProvider.products)
                    if (p.getRef().equals(line.getRef().getValue())) {
                        int subbeddQt = line.getQuantity().getText().isEmpty() ? 0
                                : Integer.parseInt(line.getQuantity().getText());
                        p.setQuantity(p.getQuantity() - subbeddQt);
                        ProductDAO.updateQuantity(p.getRef(), p.getQuantity());
                    }

            }
            Notifiar.showInfoMsg("succes", "Vente added successfully");
        } else
            Notifiar.showErrorMsg("erreur", "erreur lors la creation de la vente");

    }

    @FXML
    void saveChange() {
        if (VenteProvider.currentVente != null) {
            // update vente
            editVente();
        } else {
            // create new vente
            createVente();
        }

    }

    private void editVente() {
        // TODO Auto-generated method stub

    }

    @FXML
    void removeVente() {
        // TODO Auto-generated method stub

    }

    @FXML
    void openDetailProduct() {
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
}
