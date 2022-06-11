package com.example.Models;

import java.sql.Date;

public class Vente {
    String ref;

    Date date;

    public Vente(String ref, Date date) {
        this.ref = ref;
        this.date = date;
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

    @Override
    public String toString() {
        return "Vente [ref=" + ref + ", date=" + date + "]";
    }
}