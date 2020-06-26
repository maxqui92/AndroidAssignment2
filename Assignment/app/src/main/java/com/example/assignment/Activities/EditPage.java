package com.example.assignment.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.assignment.Database.PhoneBookDB;
import com.example.assignment.Entities.Contact;
import com.example.assignment.R;

public class EditPage extends AppCompatActivity {

    int id = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("Edit Page");
        setContentView(R.layout.activity_edit_page);

        TextView name = findViewById(R.id.txt_EditName);
        TextView email = findViewById(R.id.txt_EditEmail);
        TextView phone = findViewById(R.id.txt_EditPhone);
        TextView date = findViewById(R.id.txt_EditDate);

        if(getIntent().hasExtra("selContact")){
            id = getIntent().getIntExtra("selContact", 0);
            Contact record = PhoneBookDB.getInstance(getApplicationContext()).contactDao().getContactById(id);
            name.setText(record.getfName());
            email.setText(record.getEmail());
            phone.setText(record.getPhone());
            date.setText(record.getDate());
        }

        Button update = findViewById(R.id.btnEdit);

        //set update button method
        update.setOnClickListener(v -> {

            String name_update = name.getText().toString();
            String email_update = email.getText().toString();
            String phone_update = phone.getText().toString();
            String date_update = date.getText().toString();

            //get data from Txt fields
            Contact editContact = new Contact(
                    id, name_update, email_update, phone_update, date_update
            );

            PhoneBookDB.getInstance(getApplicationContext()).contactDao().updateContact(editContact);

            Toast.makeText(this, "The contact has been updated successfully", Toast.LENGTH_LONG).show();

            Intent resultIntent = new Intent();
            resultIntent.putExtra("Edit_Contact", 1);
            setResult(RESULT_OK, resultIntent);

            EditPage.this.finish();
        });
    }
}
