package com.example.DTO;

import com.example.Models.Product;

import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

public class ProductDTO {
    String ref;
    String name;
    int quantity;
    String family;

    float price;
    float priceTTC;
    byte TVA;
    byte marge;
    HBox actions;

    public ProductDTO(Product p) {
        this.ref = p.getRef();
        this.name = p.getName();
        this.quantity = p.getQuantity();
        this.price = p.getPrice();
        this.family = p.getFamily();
        this.TVA = p.getTVA();
        this.marge = p.getMarge();
        this.priceTTC = p.getPriceTTC();

        this.actions = new HBox();
        this.actions.getChildren().addAll(new Button("delete"), new Button("edit"));
    }

    public String getRef() {
        return this.ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return this.quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public float getPrice() {
        return this.price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public float getPriceTTC() {
        return this.priceTTC;
    }

    public void setPriceTTC(float priceTTC) {
        this.priceTTC = priceTTC;
    }

    public String getFamily() {
        return family;
    }

    public void setFamily(String family) {
        this.family = family;
    }

    public byte getTVA() {
        return this.TVA;
    }

    public void setTVA(byte TVA) {
        this.TVA = TVA;
    }

    public byte getMarge() {
        return this.marge;
    }

    public void setMarge(byte marge) {
        this.marge = marge;
    }

    public HBox getActions() {
        return this.actions;
    }

    public void setHbox(HBox actions) {
        this.actions = actions;
    }

    public Product getProduct() {
        return new Product(this.ref, this.name, this.quantity, this.price, this.TVA, this.marge);
    }

}
