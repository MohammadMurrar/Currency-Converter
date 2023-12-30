package com.example.currencyconvert;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class UserPage extends AppCompatActivity {
    private TextView txtUserPageMail,txtUserPageName,txtUserPagePhone;
    private Button btsLogOut;
    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_page);

        setUpViews();
        getUserInfo();
        setUpSharedPrefs();
        btsLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });

    }

    private void logout() {
        Intent intent = new Intent(UserPage.this, LogInActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

    private void setUpSharedPrefs(){
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        editor = prefs.edit();
    }

    private void setUpViews(){
        txtUserPageMail = findViewById(R.id.txtUserPageMail);
        txtUserPageName = findViewById(R.id.txtUserPageName);
        txtUserPagePhone = findViewById(R.id.txtUserPagePhone);
        btsLogOut = findViewById(R.id.btsLogOut);
    }
    private void getUserInfo(){
        Intent intent = getIntent();
        txtUserPageMail.setText("Mail : " + HomePage.MAIL);
        txtUserPageName.setText("Name : " +HomePage.NAME);
        txtUserPagePhone.setText("Phone : " +HomePage.PHONE);
    }
}