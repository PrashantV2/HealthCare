package com.example.healthcare;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class DoctorRegisterActivity extends AppCompatActivity {

    private EditText usernameEditText, emailEditText, passwordEditText, longitudeEditText, latitudeEditText;
    private Spinner doctorTypeSpinner;
    private Button registerButton;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_register);

        databaseHelper = new DatabaseHelper(this);

        usernameEditText = findViewById(R.id.usernameEditText);
        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        doctorTypeSpinner = findViewById(R.id.doctorTypeSpinner);
        registerButton = findViewById(R.id.registerButton);

        // Populate Spinner for Doctor Type
        Spinner doctorTypeSpinner = findViewById(R.id.doctorTypeSpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.doctor_types, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        doctorTypeSpinner.setAdapter(adapter);


        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameEditText.getText().toString().trim();
                String email = emailEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();
                String latitude = latitudeEditText.getText().toString().trim();
                String longitude = longitudeEditText.getText().toString().trim();
                String doctorType = doctorTypeSpinner.getSelectedItem().toString();

                // Validation
                if (username.isEmpty() || email.isEmpty() || password.isEmpty() || longitude.isEmpty() || latitude.isEmpty()) {
                    Toast.makeText(DoctorRegisterActivity.this, "Please fill all fields.", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!isValidEmail(email)) {
                    Toast.makeText(DoctorRegisterActivity.this, "Invalid email format.", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (databaseHelper.insertDoctor(username, email, password, latitude , longitude, doctorType)) {
                    Toast.makeText(DoctorRegisterActivity.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                    // Redirect to main activity or dashboard
                    SharedPrefManager.getInstance(DoctorRegisterActivity.this).saveUsername(email);
                    Intent mainIntent = new Intent(DoctorRegisterActivity.this, DoctorDashboardActivity.class);
                    startActivity(mainIntent);
                    finish();  // Close the Register activity
                } else {
                    Toast.makeText(DoctorRegisterActivity.this, "Registration UnSuccessful", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean isValidEmail(CharSequence target) {
        if (target == null) return false;
        return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }
}
