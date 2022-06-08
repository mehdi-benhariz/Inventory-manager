package com.example.Models;

import java.sql.Date;

public class Reception {
    String ref;

    Date date;

    public Reception(String ref, Date date) {
        this(ref);
        this.date = date;
    }

    public Reception(String ref) {
        this.ref = ref;
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

}
