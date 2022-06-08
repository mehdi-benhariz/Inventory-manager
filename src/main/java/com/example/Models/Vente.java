package com.example.Models;

import java.sql.Date;

public class Vente {
    String ref;

    Date date;
    double quantity;

    public Vente(String ref, Date date, double quantity) {
        this.ref = ref;
        this.date = date;
        this.quantity = quantity;
    }

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

}
