package com.example.assignment.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.assignment.Database.PhoneBookDB;
import com.example.assignment.Entities.Contact;
import com.example.assignment.R;

public class DetailPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("Detail Page");
        setContentView(R.layout.activity_detail_page);

        TextView name = findViewById(R.id.txtName);
        TextView email = findViewById(R.id.txtEmail);
        TextView phone = findViewById(R.id.txtPhone);
        TextView date = findViewById(R.id.txtDate);

        if(getIntent().hasExtra("selContact")){
            int contactId = getIntent().getIntExtra("selContact", 0);
            Contact contact = PhoneBookDB.getInstance(this).contactDao().getContactById(contactId);
            name.setText("Name:\n\n" + contact.fName);
            email.setText("Email:\n\n" + contact.email);
            phone.setText("Phone:\n\n" + contact.phone);
            date.setText("Date:\n\n" + contact.date);
        }
        backButton();
    }

    private void backButton(){
        findViewById(R.id.btnBack).setOnClickListener(v -> onBackPressed());
    }
}
