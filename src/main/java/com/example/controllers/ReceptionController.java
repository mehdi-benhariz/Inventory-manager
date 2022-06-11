package com.example.controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;

import javax.print.DocFlavor.STRING;

import com.example.App;
import com.example.DAO.ReceptionDAO;
import com.example.Models.Reception;
import com.example.Models.ReceptionLine;
import com.example.provider.ReceptionProvider;

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

public class ReceptionController implements Initializable {
    @FXML
    private TextField searchField;
    @FXML
    private TableView<Reception> receptionTable;
    @FXML
    private TableColumn<Reception, String> refRecept;
    @FXML
    private TableColumn<Reception, Date> dateRecept;
    private ObservableList<Reception> receptions = FXCollections.observableArrayList();

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        // add lines to scroll pane
        initFields();
        initListner();

    }

    private void initListner() {
        // double click on row to view details
        receptionTable.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                try {
                    FXMLLoader loader = new FXMLLoader(
                            App.class.getResource("DetailReception.fxml"));
                    ReceptionProvider.setCurrentReception(receptionTable.getSelectionModel().getSelectedItem());
                    Parent root = loader.load();
                    Stage stage = new Stage();
                    stage.setScene(new Scene(root));
                    stage.initModality(Modality.APPLICATION_MODAL);
                    stage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void initFields() {
        receptions = ReceptionDAO.getAll();
        refRecept.setCellValueFactory(new PropertyValueFactory<Reception, String>("ref"));
        dateRecept.setCellValueFactory(new PropertyValueFactory<Reception, Date>("date"));

        receptionTable.setItems(receptions);
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
    void searchReceptions() {
        String query = searchField.getText();
        ObservableList<Reception> res = ReceptionDAO.search(query);
        receptionTable.setItems(res);

    }

    @FXML
    void openAddWindow() {
        try {
            Parent root = FXMLLoader.load(App.class.getResource("DetailReception.fxml"));
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
