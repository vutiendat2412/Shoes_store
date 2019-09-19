package com.example.appbanhang;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {

    private DatabaseHelper databaseHelper;
    private EditText mEdittextUsername;
    private EditText mEdittextPassword;
    private EditText mEdittextConfirmPassword;
    private Button mButtonRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        init();

        databaseHelper = new DatabaseHelper(this);
        mButtonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent LoginIntent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(LoginIntent);
            }
        });
        mButtonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = mEdittextUsername.getText().toString().trim();
                String password = mEdittextPassword.getText().toString().trim();
                String confirm_password = mEdittextConfirmPassword.getText().toString().trim();

                if (password.equals(confirm_password)) {
                    long val = databaseHelper.addUser(user, password);
                    if (val > 0) {
                        Toast.makeText(RegisterActivity.this, "You have registered", Toast.LENGTH_SHORT).show();
                        Intent moveToLogin = new Intent(RegisterActivity.this, LoginActivity.class);
                        startActivity(moveToLogin);
                    } else {
                        Toast.makeText(RegisterActivity.this, "Registeration Error", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(RegisterActivity.this, "Password is not matching", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void init() {
        mEdittextUsername = findViewById(R.id.edittext_username);
        mEdittextPassword = findViewById(R.id.edittext_password);
        mEdittextConfirmPassword = findViewById(R.id.edittext_cnf_password);
        mButtonRegister = findViewById(R.id.button_register);
    }
}
