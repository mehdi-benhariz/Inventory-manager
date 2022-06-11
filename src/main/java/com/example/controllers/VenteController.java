package com.example.controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;

import javax.print.DocFlavor.STRING;

import com.example.App;
import com.example.DAO.ReceptionDAO;
import com.example.DAO.VenteDAO;
import com.example.Models.Reception;
import com.example.Models.ReceptionLine;
import com.example.Models.Vente;
import com.example.provider.VenteProvider;

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

public class VenteController implements Initializable {
    @FXML
    private TextField searchField;
    @FXML
    private TableView<Vente> venteTable;
    @FXML
    private TableColumn<Vente, String> refVente;
    @FXML
    private TableColumn<Vente, Date> dateVente;
    private ObservableList<Vente> ventes = FXCollections.observableArrayList();

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        // add lines to scroll pane
        initFields();
        initListner();

    }

    private void initListner() {
        // double click on row to view details
        venteTable.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                try {
                    FXMLLoader loader = new FXMLLoader(
                            App.class.getResource("DetailVente.fxml"));
                    Parent root = loader.load();
                    Stage stage = new Stage();
                    stage.setScene(new Scene(root));
                    stage.setTitle("Vente Details");
                    stage.initModality(Modality.APPLICATION_MODAL);
                    stage.show();
                    // set current vente in the provider
                    VenteProvider.setCurrentVente(venteTable.getSelectionModel().getSelectedItem());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void initFields() {
        ventes = VenteDAO.getAll();
        refVente.setCellValueFactory(new PropertyValueFactory<Vente, String>("ref"));
        dateVente.setCellValueFactory(new PropertyValueFactory<Vente, Date>("date"));

        venteTable.setItems(ventes);
        VenteProvider.setVentes(ventes);
    }

    @FXML
    void backToMenu() {
        try {
            App.setRoot("Home");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void openAddWindow() {
        try {
            Parent root = FXMLLoader.load(App.class.getResource("DetailVente.fxml"));
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
    void searchVentes() {
        String query = searchField.getText();
        ventes = VenteDAO.search(query);
        venteTable.setItems(ventes);
        VenteProvider.setVentes(ventes);
    }

    @FXML
    void openCreateProduct() {
        try {
            Parent root = FXMLLoader.load(App.class.getResource("DetailVente.fxml"));
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
