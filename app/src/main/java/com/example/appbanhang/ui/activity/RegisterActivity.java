package com.example.appbanhang.ui.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appbanhang.R;
import com.example.appbanhang.data.DatabaseHelper;
import com.example.appbanhang.model.User;

import androidx.appcompat.app.AppCompatActivity;


public class RegisterActivity extends AppCompatActivity {

    private DatabaseHelper databaseHelper;
    private EditText mEdittextUsername;
    private EditText mEdittextPassword;
    private EditText mEdittextConfirmPassword;
    private Button mButtonRegister;
    private TextView mLinkLogin;
    private User userClass;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        init();

        databaseHelper = new DatabaseHelper(this);

        mLinkLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent LoginIntent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(LoginIntent);
            }
        });
        mButtonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RegisterAccount();
            }

        });

    }

    public void RegisterAccount() {

        mButtonRegister.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(RegisterActivity.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage("Creating Account...");
        progressDialog.show();

        String user = mEdittextUsername.getText().toString().trim();
        String password = mEdittextPassword.getText().toString().trim();
        String confirm_password = mEdittextConfirmPassword.getText().toString().trim();

        if (!TextUtils.isEmpty(user) && !TextUtils.isEmpty(password)
                && !TextUtils.isEmpty(confirm_password)) {
            if (password.equals(confirm_password)) {
                long val = databaseHelper.addUser(user,password);
                if (val > 0) {
                    Toast.makeText(RegisterActivity.this, "You have registered", Toast.LENGTH_SHORT).show();
                    new Handler().postDelayed(
                            new Runnable() {
                                public void run() {
                                    // On complete call either onSignupSuccess or onSignupFailed
                                    // depending on success
                                    onSignupSuccess();
                                    // onSignupFailed();
                                    progressDialog.dismiss();
                                }
                            }, 3000);
                    Intent moveToLogin = new Intent(RegisterActivity.this, LoginActivity.class);
                    startActivity(moveToLogin);
                } else {
                    Toast.makeText(RegisterActivity.this, "Registeration Error", Toast.LENGTH_SHORT).show();
                }

            } else {
                Toast.makeText(RegisterActivity.this, "Password is not matching", Toast.LENGTH_SHORT).show();
            }
        } else {
            final ProgressDialog progressDialogLoginFail = new ProgressDialog(RegisterActivity.this,
                    R.style.AppTheme_Dark_Dialog);
            progressDialog.setIndeterminate(true);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setMessage("Please fill in the box...");
            progressDialog.show();
        }
    }

    public void onSignupSuccess() {
        mButtonRegister.setEnabled(true);
        setResult(RESULT_OK, null);
        finish();
    }

    public void onSignupFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();
        mButtonRegister.setEnabled(true);
    }

    private void init() {
        mEdittextUsername = findViewById(R.id.edittext_username);
        mEdittextPassword = findViewById(R.id.edittext_password);
        mEdittextConfirmPassword = findViewById(R.id.edittext_cnf_password);
        mButtonRegister = findViewById(R.id.button_register);
        mLinkLogin = findViewById(R.id.link_login);
    }
}
