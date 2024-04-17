package com.example.healthcare;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class PatientRegisterActivity extends AppCompatActivity {

    private EditText usernameEditText, emailEditText, passwordEditText;
    private Button registerButton;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_register);

        databaseHelper = new DatabaseHelper(this);

        usernameEditText = findViewById(R.id.usernameEditText);
        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        registerButton = findViewById(R.id.registerButton);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameEditText.getText().toString().trim();
                String email = emailEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();

                if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(PatientRegisterActivity.this, "Please fill all fields.", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!isValidEmail(email)) {
                    Toast.makeText(PatientRegisterActivity.this, "Invalid email format.", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (databaseHelper.insertPatient(username, email, password)) {
                    Toast.makeText(PatientRegisterActivity.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                    SharedPrefManager.getInstance(PatientRegisterActivity.this).saveUsername(email);
                    Intent mainIntent = new Intent(PatientRegisterActivity.this, PatientDashboardActivity.class);
                    startActivity(mainIntent);
                    finish();  // Close the Register activity
                } else {
                    Toast.makeText(PatientRegisterActivity.this, "Registration UnSuccessful", Toast.LENGTH_SHORT).show();
                }
            }

        });
    }

    private boolean isValidEmail(CharSequence target) {
        if (target == null) return false;
        return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }
}
