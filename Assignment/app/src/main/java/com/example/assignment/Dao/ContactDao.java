package com.example.assignment.Dao;

import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.assignment.Entities.Contact;

import java.util.List;

@androidx.room.Dao
public interface ContactDao {
    // create
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertContact(Contact... contacts);

    // update
    @Update
    public void updateContact(Contact... contacts);

    // delete
    @Delete
    public void deleteContact(Contact... contacts);

    // delete all
    @Query("DELETE FROM contact")
    public void clearTable();

    // read all
    @Query("SELECT * FROM contact")
    public List<Contact> getAllContacts();

    // read one by id
    @Query("SELECT * FROM contact WHERE id = :id")
    public Contact getContactById(int id);
}
