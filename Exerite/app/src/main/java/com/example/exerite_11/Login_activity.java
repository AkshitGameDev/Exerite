package com.example.exerite_11;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class Login_activity extends AppCompatActivity {
    private static final String SHARED_PREF_NAME = "my_shared_pref";
    private static final String KEY_IS_LOGGED_IN = "is_logged_in";
    private EditText userEmail;
    private EditText editTextPassword;
    private Button buttonLogin;
    private Button signUpButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        userEmail = findViewById(R.id.editTextUsername);
        editTextPassword = findViewById(R.id.editTextPassword);
        buttonLogin = findViewById(R.id.buttonLogin);
        signUpButton = findViewById(R.id.signUpButton);

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser();
            }
        });

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login_activity.this, Signup_activity.class));
            }
        });
    }

    private void loginUser() {
        DBHelper DB;
        DB = new DBHelper(this);

        String username = userEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        if (username.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please fill required information", Toast.LENGTH_SHORT).show();
        } else {
            boolean checkuser = DB.checkUserEmail(username);
            if (!checkuser) {
                boolean insert = DB.insertData(username, password);
                if (insert) {
                    Toast.makeText(this, "Registration success", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(this, Home_Activity.class);
                    intent.putExtra("USER_EMAIL", username);
                    startActivity(intent);
                } else {
                    Toast.makeText(this, "Registration failed", Toast.LENGTH_SHORT).show();
                }
            } else {
                boolean checkuserpass = DB.checkPassword(username, password);
                if (checkuserpass) {
                    setLoggedInStatus(true);
                    Intent intent = new Intent(this, Home_Activity.class);
                    intent.putExtra("USER_EMAIL", username);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(this, "Invalid username or password", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private void setLoggedInStatus(boolean isLoggedIn) {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(KEY_IS_LOGGED_IN, isLoggedIn);
        editor.apply();
    }
}
