package com.example.currencyconvert;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class HomePage extends AppCompatActivity {
    public static String MAIL = "mail";
    public static String NAME = "name";
    public static String PHONE = "phone";
    private ImageButton imgBtnUserPage,imgBtnEX,imgBtnBTC;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        getUserInfo();

        setUpViews();

        imgBtnUserPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomePage.this,UserPage.class);
                startActivity(intent);

            }
        });

        imgBtnEX.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomePage.this,EXActivity.class);
                startActivity(intent);
            }
        });

        imgBtnBTC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomePage.this,BTCActivity.class);
                startActivity(intent);
            }
        });

    }
    private void getUserInfo(){
        Intent intent = getIntent();
        MAIL = intent.getStringExtra("mail");
        NAME = intent.getStringExtra("name");
        PHONE = intent.getStringExtra("phone");
    }
    private void setUpViews(){
        imgBtnUserPage = findViewById(R.id.imgBtnUserPage);
        imgBtnEX = findViewById(R.id.imgBtnEX);
        imgBtnBTC = findViewById(R.id.imgBtnBTC);
    }
}