package com.example.assignment.Activities;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.assignment.R;

public class SmsPage extends AppCompatActivity {

    private static final int MY_PERMISSIONS_REQUEST_SENS_SMS = 0;
    EditText textMsg, textPhoneNo;
    String msg, phoneNo;
    Button send;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("SMS Page");
        setContentView(R.layout.sms);

        // check if the permission is not granted
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
            // if permission is not granted check if the user has denied the permission
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.SEND_SMS)) {

            } else {
                // a pop up will appear asking for required permission i.e. allow or deny
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, MY_PERMISSIONS_REQUEST_SENS_SMS);
            }
        }

        textMsg = findViewById(R.id.textMsg);
        textPhoneNo = findViewById(R.id.textPhoneNo);
        send = findViewById(R.id.send);

        send.setOnClickListener(v -> sendTextMessage());
        backButton();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
            switch (requestCode)
            {
                case MY_PERMISSIONS_REQUEST_SENS_SMS:
                {
                    // check whether the length of grantResult is greater than 0 and is equal to PERMISSION_GRANTED
                    if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    {
                        Toast.makeText(this, "Permission GRANTED", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(this, "Permission DENIED", Toast.LENGTH_LONG).show();
                    }
                }
            }
    }

    protected void sendTextMessage() {
        msg = textMsg.getText().toString();
        phoneNo = textPhoneNo.getText().toString();

        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(phoneNo, null, msg, null, null);
        Toast.makeText(this, "SMS Sent!", Toast.LENGTH_LONG).show();
        finish();
    }

    private void backButton(){
        findViewById(R.id.back).setOnClickListener(v -> onBackPressed());
    }
}
