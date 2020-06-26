package com.example.assignment.Entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "contact")
public class Contact implements Comparable {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    public int id;

    @ColumnInfo(name = "name")
    public String fName;

    @ColumnInfo(name = "email")
    public String email;

    @ColumnInfo(name = "phone")
    public String phone;

    @ColumnInfo(name = "date")
    public String date;

    public Contact(){}

    @Ignore
    public Contact(String fName, String email, String phone, String date) {
        this.fName = fName;
        this.email = email;
        this.phone = phone;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Ignore
    public Contact(int id, String fName, String email, String phone, String date) {
        this.id = id;
        this.fName = fName;
        this.email = email;
        this.phone = phone;
        this.date = date;
    }

    @Override
    public int compareTo(Object o) {
        return this.fName.compareToIgnoreCase(((Contact) o).fName);
    }
}
