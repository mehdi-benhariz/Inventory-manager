package com.example.Models;

public class Product {
    String ref;
    String name;
    int quantity;
    Float price;
    byte TVA;
    byte marge;
    String family;

    public Product(String ref, String name, int quantity, Float price, byte TVA, byte marge, String family) {
        this(ref, name, quantity, price, TVA, marge);
        this.family = family;
    }

    public Product(String ref, String name, int quantity, Float price, byte TVA, byte marge) {
        this.ref = ref;
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.TVA = TVA;
        this.marge = marge;
        this.family = "general";
    }

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public byte getMarge() {
        return marge;
    }

    public void setMarge(byte marge) {
        this.marge = marge;
    }

    public String getFamily() {
        return family;
    }

    public void setFamily(String family) {
        this.family = family;
    }

    public byte getTVA() {
        return TVA;
    }

    public void setTVA(byte tVA) {
        TVA = tVA;
    }

    public Float getPriceTTC() {
        return price * (1 + (float) this.TVA / 100) * (1 + (float) this.marge / 100);
    }

    @Override
    public String toString() {
        return "Product [ref=" + ref + ", name=" + name + ", quantity=" + quantity + ", price=" + price + ", TVA=" + TVA
                + ", marge=" + marge + "]";
    }
}
