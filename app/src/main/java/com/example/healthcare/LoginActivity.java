package com.example.healthcare;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    private EditText emailEditText, passwordEditText;
    private Button loginButton;
    private TextView registerPatientTextView, registerDoctorTextView;
    private DatabaseHelper databaseHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        databaseHelper = new DatabaseHelper(this);

        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        loginButton = findViewById(R.id.loginButton);
        registerPatientTextView = findViewById(R.id.registerPatientTextView);
        registerDoctorTextView = findViewById(R.id.registerDoctorTextView);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();

               if (email.isEmpty()) {
                    emailEditText.setError("Email is required");
                    emailEditText.requestFocus();
                    return;
                }

               if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                   emailEditText.setError("Valid email is required");
                   emailEditText.requestFocus();
                   return;
               }

               if (password.isEmpty()) {
                   passwordEditText.setError("Password is required");
                   passwordEditText.requestFocus();
                   return;
               }

                String userType = databaseHelper.checkUser(email, password);

                if (userType != null) {
                    // User exists and credentials are correct
                    Toast.makeText(LoginActivity.this, "Login successful!", Toast.LENGTH_SHORT).show();

                    Intent intent;
                    if (userType.equals("Doctor")) {
                        SharedPrefManager.getInstance(LoginActivity.this).saveUsername(email);
                        intent = new Intent(LoginActivity.this, DoctorDashboardActivity.class);
                    } else {
                        SharedPrefManager.getInstance(LoginActivity.this).saveUsername(email);
                        intent = new Intent(LoginActivity.this, PatientDashboardActivity.class);
                    }

                    startActivity(intent);
                    finish();  // Close the login activity
                } else {
                    // No matching user or wrong credentials
                    Toast.makeText(LoginActivity.this, "Incorrect email or password.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        registerPatientTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, PatientRegisterActivity.class);
                startActivity(intent);
                finish();  // Close the login activity
            }
        });

        registerDoctorTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, DoctorRegisterActivity.class);
                startActivity(intent);
                finish();  // Close the login activity
            }
        });

        TextView developerInfoTextView = findViewById(R.id.developerInfoTextView);
        developerInfoTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, DeveloperInfoActivity.class);
                startActivity(intent);
            }
        });

    }
}
