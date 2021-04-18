package com.example.dodocuisto.view;

import android.content.Intent;
import android.os.Bundle;
import com.google.android.material.textfield.TextInputLayout;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.example.dodocuisto.R;
import com.example.dodocuisto.controller.DatabaseController;

public class SigninActivity extends ToolbarActivity {
    private TextInputLayout mLoginEmail;
    private TextInputLayout mLoginPassword;

    private DatabaseController databaseAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        databaseAdapter = DatabaseController.getInstance(this);

        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Log in to your account");

        mLoginEmail = findViewById(R.id.login_email);
        mLoginPassword = findViewById(R.id.login_password);
    }

    public void onLoginPressed(View view) {
        String email = mLoginEmail.getEditText().getText().toString();
        String password = mLoginPassword.getEditText().getText().toString();

        if (!TextUtils.isEmpty(email) || !TextUtils.isEmpty(password)) {
            loginUser(email, password);
        }
    }

    private void loginUser(String email, String password) {
        boolean status = databaseAdapter.signIn(email, password);
        if (status) {
            Intent intent = new Intent(this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(this, "Invalid credentials.", Toast.LENGTH_LONG).show();
        }
    }

    public void onCreateAccountPressed(View view) {
        startActivity(new Intent(this, SignupActivity.class));
    }
}
