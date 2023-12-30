package com.example.currencyconvert;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class LogInActivity extends AppCompatActivity {
    public static final String NAME = "NAME";
    public static final String PASS = "PASS";
    public static final String FLAG = "FLAG";
    public static ArrayList<User> usersList;
    private boolean flag = false;
    private EditText edtLogInUserName;
    private EditText edtLogInPass;
    private CheckBox chkME;
    private Button btn_LOGIN;
    private TextView txtCreateAccount;
    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        setUpViews();
        setUpSharedPrefs();
        checkPrefs();
        usersList = loadTasksFromSP();

        btn_LOGIN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_LOGINOnClick(v);
            }
        });

        txtCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LogInActivity.this,SignUpActivity.class);
                startActivity(intent);
            }
        });

    }

    private void setUpViews(){
        edtLogInUserName = findViewById(R.id.edtLogInUserName);
        edtLogInPass = findViewById(R.id.edtLogInPass);
        chkME = findViewById(R.id.chkME);
        btn_LOGIN = findViewById(R.id.btn_LOGIN);
        txtCreateAccount = findViewById(R.id.txtCreateAccount);
    }

    private void setUpSharedPrefs(){
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        editor = prefs.edit();
    }

    private void btn_LOGINOnClick(View v){
        String userName = edtLogInUserName.getText().toString();
        String password = edtLogInPass.getText().toString();
        if(authenticateUser(userName, password)){
            rememberMe(userName,password);
            startHomePageActivity(userName);
        } else {
            showToast("Invalid username or password");
        }


    }
    private boolean authenticateUser(String userName, String password) {
        for (User user : usersList) {
            if (user.getName().equals(userName) && user.getPass().equals(password)) {
                return true;
            }
        }
        return false;
    }

    private void rememberMe(String userName, String password){
        if(chkME.isChecked() && !flag){
            editor.putString(NAME,userName);
            editor.putString(PASS,password);
            editor.putBoolean(FLAG,true);
            editor.apply();
        }
        else if(!chkME.isChecked() && flag) {
            editor.remove(NAME);
            editor.remove(PASS);
            editor.putBoolean(FLAG,false);
            editor.apply();
        }
    }

    private void startHomePageActivity(String userName){
        User user = getUser(userName);
        if (user != null){
            Intent intent = new Intent(this,HomePage.class);
            intent.putExtra("mail",user.getMail());
            intent.putExtra("name",user.getName());
            intent.putExtra("phone",user.getPhone());
            startActivity(intent);
        }
    }

    private User getUser(String userName){
        for (User user : usersList) {
            if (user.getName().equals(userName)) {
                return user;
            }
        }
        return null;
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void checkPrefs() {
        flag = prefs.getBoolean(FLAG, false);
        if(flag){
            String name = prefs.getString(NAME, "");
            String password = prefs.getString(PASS, "");
            edtLogInUserName.setText(name);
            edtLogInPass.setText(password);
            chkME.setChecked(true);
        }
    }

    private ArrayList<User> loadTasksFromSP(){
        Gson gson = new Gson();
        String userAsJSON = prefs.getString(SignUpActivity.USERS,"");
        if(!userAsJSON.isEmpty()){
            Type type = new TypeToken<ArrayList<User>>(){}.getType();
            return gson.fromJson(userAsJSON, type);
        }
        return new ArrayList<>();
    }

}