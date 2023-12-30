package com.example.currencyconvert;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class SignUpActivity extends AppCompatActivity {
    public static final String USERS = "USERS";
    private EditText edtSignUpMail;
    private EditText edtSignUpName;
    private EditText edtSignUpPass;
    private EditText edtSignUpAge;
    private EditText edtSignUpPhone;
    private RadioButton rbMale;
    private RadioButton rbFemale;
    private Button btnSignUp;
    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;
    private SharedPreferences prefs2;
    private SharedPreferences.Editor editor2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        setUpViews();
        setUpSharedPrefs();
        setUpSharedPrefs2();
        loadSavedUserInfo();
        Intent intent = getIntent();
        LogInActivity.usersList = loadTasksFromLS();


        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
                storeUsers();
                clearEDTs();
                finish();
            }
        });

    }

    @Override
    protected void onStop() {
        super.onStop();
        saveUserInfo();
    }

    private void saveUserInfo(){
        String userMail = edtSignUpMail.getText().toString();
        String userName = edtSignUpName.getText().toString();
        String userPass = edtSignUpPass.getText().toString();
        String userAge = edtSignUpAge.getText().toString();
        String userPhone = edtSignUpPhone.getText().toString();
        boolean isMale = rbMale.isChecked();

        editor2.putString("userMail", userMail);
        editor2.putString("userName", userName);
        editor2.putString("userPass", userPass);
        editor2.putString("userAge", userAge);
        editor2.putString("userPhone", userPhone);
        editor2.putBoolean("isMale", isMale);
        editor2.apply();
    }

    private void loadSavedUserInfo() {
        String userMail = prefs2.getString("userMail", "");
        String userName = prefs2.getString("userName", "");
        String userPass = prefs2.getString("userPass", "");
        String userAge = prefs2.getString("userAge", "");
        String userPhone = prefs2.getString("userPhone", "");
        boolean isMale = prefs2.getBoolean("isMale", false);

        edtSignUpMail.setText(userMail);
        edtSignUpName.setText(userName);
        edtSignUpPass.setText(userPass);
        edtSignUpAge.setText(userAge);
        edtSignUpPhone.setText(userPhone);
        rbMale.setChecked(isMale);
        rbFemale.setChecked(!isMale);
    }

    private void setUpViews(){
        edtSignUpMail = findViewById(R.id.edtSignUpMail);
        edtSignUpName = findViewById(R.id.edtSignUpName);
        edtSignUpPass = findViewById(R.id.edtSignUpPass);
        edtSignUpAge = findViewById(R.id.edtSignUpAge);
        edtSignUpPhone = findViewById(R.id.edtSignUpPhone);
        rbMale = findViewById(R.id.rbMale);
        rbFemale = findViewById(R.id.rbFemale);
        btnSignUp = findViewById(R.id.btnSignUp);
    }

    private void setUpSharedPrefs(){
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        editor = prefs.edit();
    }

    private void setUpSharedPrefs2(){
        prefs2 = PreferenceManager.getDefaultSharedPreferences(this);
        editor2 = prefs2.edit();
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void storeUsers(){
        Gson gson = new Gson();
        String userAsJSON = gson.toJson(LogInActivity.usersList);
        editor.putString(USERS,userAsJSON);
        editor.apply();
    }

    private ArrayList<User> loadTasksFromLS(){
        Gson gson = new Gson();
        String userAsJSON = prefs.getString(USERS,"");
        if(!userAsJSON.isEmpty()){
            Type type = new TypeToken<ArrayList<User>>(){}.getType();
            return gson.fromJson(userAsJSON, type);
        }
        return new ArrayList<>();
    }

    private void register(){
        String userMail = edtSignUpMail.getText().toString();
        String userName = edtSignUpName.getText().toString();
        String userPass = edtSignUpPass.getText().toString();
        String userAge = edtSignUpAge.getText().toString();
        String userPhone = edtSignUpPhone.getText().toString();
        String gander;
        if(rbMale.isChecked()){
            gander = rbMale.getText().toString();
        }
        else{
            gander = rbFemale.getText().toString();
        }
        if((userName.length() < 5  || userPass.length() < 5)){
            showToast("User Name or Password must be at least 5 characters");
            return;
        }
        if(userAge.isEmpty() || userPhone.isEmpty()){
            showToast("You have to fill all fields");
        }
        else{
            User newUser = new User(userMail,userName,userPass,userAge,userPhone,gander);
            LogInActivity.usersList.add(newUser);
            showToast("User registered successfully");
        }
    }

    private void clearEDTs(){
        edtSignUpMail.setText("");
        edtSignUpName.setText("");
        edtSignUpPass.setText("");
        edtSignUpAge.setText("");
        edtSignUpPhone.setText("");
        rbMale.setChecked(false);
        rbFemale.setChecked(false);

    }


}