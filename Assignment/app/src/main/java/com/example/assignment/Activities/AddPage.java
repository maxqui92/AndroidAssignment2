package com.example.assignment.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.assignment.Database.PhoneBookDB;
import com.example.assignment.Entities.Contact;
import com.example.assignment.R;

public class AddPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("Add Page");
        setContentView(R.layout.activity_add_page);

        Button add = findViewById(R.id.btnAdd);

        //set add button method
        add.setOnClickListener(v -> {

            //get data from Txt fields
            Contact addContact = new Contact(
                    ((EditText)findViewById(R.id.txt_AddName)).getText().toString(),
                    ((EditText)findViewById(R.id.txt_AddEmail)).getText().toString(),
                    ((EditText)findViewById(R.id.txt_AddPhone)).getText().toString(),
                    ((EditText)findViewById(R.id.txt_AddDate)).getText().toString()
            );

            //get db instance
            PhoneBookDB db = PhoneBookDB.getInstance(this);

            //add data
            db.contactDao().insertContact(addContact);

            Toast.makeText(this, "The contact has been added successfully", Toast.LENGTH_LONG).show();

            Intent resultIntent = new Intent();
            resultIntent.putExtra("Add_Contact", 1);
            setResult(RESULT_OK, resultIntent);

            AddPage.this.finish();
        });
    }
}
