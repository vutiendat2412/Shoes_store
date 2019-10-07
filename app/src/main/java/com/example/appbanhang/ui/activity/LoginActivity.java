package com.example.appbanhang.ui.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appbanhang.R;
import com.example.appbanhang.data.DatabaseHelper;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    private EditText mEdittextUsername;
    private EditText mEdittextPassword;
    private Button mButtonLogin;
    private TextView mTextviewRegister;
    private DatabaseHelper databaseHelper;
    ViewGroup progressView;
    protected boolean isProgressShowing = false;
    private static final int REQUEST_SIGNUP = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mEdittextUsername = findViewById(R.id.edittext_username);
        mEdittextPassword = findViewById(R.id.edittext_password);
        mButtonLogin = findViewById(R.id.button_login);
        mTextviewRegister = findViewById(R.id.textview_register);

//        Dialog dialog = new Dialog(this, android.R.style.Theme_Translucent_NoTitleBar);
//        View v = this.getLayoutInflater().inflate(R.layout.progressbar, null);
//        dialog.setContentView(v);
//        dialog.show();

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
                Login();
            }
        });
    }

    public void Login() {
        mButtonLogin.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage("Authenticating...");
        progressDialog.show();

        String user = mEdittextPassword.getText().toString().trim();
        String password = mEdittextPassword.getText().toString().trim();

        if (!TextUtils.isEmpty(user) && !TextUtils.isEmpty(password)) {
            Boolean res = databaseHelper.checkUser(user, password);
            if (res == true) {
                new android.os.Handler().postDelayed(
                        new Runnable() {
                            public void run() {
                                // On complete call either onLoginSuccess or onLoginFailed
                                onLoginSuccess();
                                // onLoginFailed();
                                progressDialog.dismiss();
                            }
                        }, 3000);
                Intent HomePage = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(HomePage);
            } else {
                Toast.makeText(LoginActivity.this, "Login Error", Toast.LENGTH_SHORT).show();
                onLoginFailed();
            }
        } else {
            final ProgressDialog progressDialogLoginFail = new ProgressDialog(LoginActivity.this,
                    R.style.AppTheme_Dark_Dialog);
            progressDialog.setIndeterminate(true);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setMessage("Please fill in the box...");
            progressDialog.show();
        }
    }

    public void onLoginSuccess() {
        mButtonLogin.setEnabled(true);
        finish();
    }

    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();
        mButtonLogin.setEnabled(true);
    }

    @Override
    public void onBackPressed() {
        // Disable going back to the MainActivity
        moveTaskToBack(true);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_SIGNUP) {
            if (resultCode == RESULT_OK) {

                // TODO: Implement successful signup logic here
                // By default we just finish the Activity and log them in automatically
                this.finish();
            }
        }
    }
//    public void showProgressingView() {
//
//        if (!isProgressShowing) {
//            View view = findViewById(R.id.progressBar1);
//            view.bringToFront();
//        }
//    }
//
//    public void hideProgressingView() {
//        View v = this.findViewById(android.R.id.content).getRootView();
//        ViewGroup viewGroup = (ViewGroup) v;
//        viewGroup.removeView(progressView);
//        isProgressShowing = false;
//    }
}
