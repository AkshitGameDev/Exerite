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
    private static final String KEY_USER_EMAIL = "user_email";

    EditText userEmail;
    EditText editTextPassword;
    Button buttonLogin;
    Button signUpButton;
    DBHelper DB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        userEmail = findViewById(R.id.editTextUsername);
        editTextPassword = findViewById(R.id.editTextPassword);
        buttonLogin = findViewById(R.id.buttonLogin);
        signUpButton = findViewById(R.id.signUpButton);

        // Initialize database helper
        DB = new DBHelper(Login_activity.this);

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
        String useremail = userEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if (useremail.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
        } else {
                boolean checkUserPass = DB.checkPassword(useremail, password);
                        if (checkUserPass) {
                            setLoggedInStatus(true, useremail);
                            Intent intent = new Intent(Login_activity.this, Home_Activity.class);
                            intent.putExtra("USER_EMAIL", useremail);
                            startActivity(intent);

                        } else {
                            Toast.makeText(Login_activity.this, "Invalid username or password", Toast.LENGTH_SHORT).show();
                        }
        }
    }


    private void setLoggedInStatus(boolean isLoggedIn, String email) {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(KEY_IS_LOGGED_IN, isLoggedIn);
        editor.putString(KEY_USER_EMAIL, email); // Store the email instead of null
        editor.apply();
    }

    public static String getUserEmail(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_USER_EMAIL, null);
    }

}
