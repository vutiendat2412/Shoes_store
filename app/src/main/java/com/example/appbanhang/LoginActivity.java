package com.example.appbanhang;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    private EditText mEdittextUsername;
    private EditText mEdittextPassword;
    private Button mButtonLogin;
    private TextView mTextviewRegister;
    private DatabaseHelper databaseHelper;
    ViewGroup progressView;
    protected boolean isProgressShowing = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mEdittextUsername = findViewById(R.id.edittext_username);
        mEdittextPassword = findViewById(R.id.edittext_password);
        mButtonLogin = findViewById(R.id.button_login);
        mTextviewRegister = findViewById(R.id.textview_register);

        Dialog dialog = new Dialog(this, android.R.style.Theme_Translucent_NoTitleBar);
        View v = this.getLayoutInflater().inflate(R.layout.progressbar, null);
        dialog.setContentView(v);
        dialog.show();

        databaseHelper = new DatabaseHelper(this);
        mTextviewRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerIntent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(registerIntent);
            }
        });

        mButtonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = mEdittextPassword.getText().toString().trim();
                String password = mEdittextPassword.getText().toString().trim();
                Boolean res = databaseHelper.checkUser(user, password);
                if (res == true) {
                    Intent HomePage = new Intent(LoginActivity.this, HomeActivity.class);
                    startActivity(HomePage);
                } else {
                    Toast.makeText(LoginActivity.this, "Login Error", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public void showProgressingView() {

        if (!isProgressShowing) {
            View view = findViewById(R.id.progressBar1);
            view.bringToFront();
        }
    }

    public void hideProgressingView() {
        View v = this.findViewById(android.R.id.content).getRootView();
        ViewGroup viewGroup = (ViewGroup) v;
        viewGroup.removeView(progressView);
        isProgressShowing = false;
    }
}
