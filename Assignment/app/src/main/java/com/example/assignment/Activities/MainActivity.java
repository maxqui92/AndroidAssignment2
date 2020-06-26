package com.example.assignment.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.view.GestureDetectorCompat;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.Button;
import android.widget.Toast;

import com.example.assignment.Database.RemoteDB;
import com.example.assignment.Entities.Contact;
import com.example.assignment.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private String TAG = this.getClass().getSimpleName();
    public static final String myPreferences = "nightModePrefs";
    public static final String key_IsNightMode = "IsNightMode";
    SharedPreferences sharedPreferences;
    private Button btn_light;
    private Button btn_dark;
    private Button btn_api;

    private GestureDetectorCompat mDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setTitle("Welcome Page");
        sharedPreferences = getSharedPreferences(myPreferences, Context.MODE_PRIVATE);

        checkDarkModeSettings();
        setDark();
        setLight();
        phoneCall();
        camera();
        sendSms();
        api();

        mDetector = new GestureDetectorCompat(this, new MyGestureListener());
    }

    private void saveNightModeState(boolean b) {
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putBoolean(key_IsNightMode, b);
        editor.apply();
    }

    private void checkDarkModeSettings () {
        if (sharedPreferences.getBoolean(key_IsNightMode, false)) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
    }

    private void setDark() {
        btn_dark = findViewById(R.id.btn_dark);
        btn_dark.setOnClickListener(v -> {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            saveNightModeState(true);
        });
    }

    private void setLight() {
        btn_light = findViewById(R.id.btn_light);
        btn_light.setOnClickListener(v -> {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            saveNightModeState(false);
        });
    }

    // method to call API on the server-side
    public void api() {
        btn_api = findViewById(R.id.btn_api);
        btn_api.setOnClickListener(v -> {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://192.168.1.108:80/api/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            RemoteDB service = retrofit.create(RemoteDB.class);

            // Prepare API Call
            Call<Contact> contactCreate = service.ContactCreate(new Contact("Massimiliano Quinto", "mquinto@gmail.com", "0422570999", "12/08/1992"));
            // Call API
            contactCreate.enqueue(new Callback<Contact>() {

                // Call API (successful result)
                @Override
                public void onResponse(Call<Contact> call, Response<Contact> response) {
                    Contact contact = response.body();
                    Log.d(TAG, contact.toString());
                    return;
                }

                // Call API (unsuccessful result)
                @Override
                public void onFailure(Call<Contact> call, Throwable t) {
                    Log.d(TAG, "onFailure");
                    return;
                }
            });
            Toast.makeText(this, "API called successfully", Toast.LENGTH_LONG).show();
        });
    }

    public void phoneCall() {
        Button call = findViewById(R.id.btn_call);
        call.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, PhoneCallPage.class);
            startActivity(intent);
        });
    }

    public void sendSms() {
        Button sms = findViewById(R.id.btn_sms);
        sms.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, SmsPage.class);
            startActivity(intent);
        });
    }

    public void camera() {
        Button camera = findViewById(R.id.btn_camera);
        camera.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, CameraPage.class);
            startActivity(intent);
        });
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        this.mDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    class MyGestureListener extends GestureDetector.SimpleOnGestureListener {
        private static final String DEBUG_TAG = "Gestures";

        @Override
        public boolean onDown(MotionEvent event) {
            Log.d(DEBUG_TAG, "onDown: " + event.toString());
            return true;
        }

        @Override
        public boolean onFling(MotionEvent event1, MotionEvent event2,
                               float velocityX, float velocityY) {
            Log.d(DEBUG_TAG, "onFling: " + event1.toString() + event2.toString());

            Intent intent = new Intent(MainActivity.this, ListPage.class);
            startActivity(intent);
            return true;
        }
    }
}
