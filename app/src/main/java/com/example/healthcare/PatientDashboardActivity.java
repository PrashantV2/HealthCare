package com.example.healthcare;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class PatientDashboardActivity extends AppCompatActivity {

    private Button btnSearchDoctors, btnViewAppointments, btnViewPrescriptions, logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_dashboard);

        // Initializing the UI components
        btnSearchDoctors = findViewById(R.id.btnSearchDoctors);
        btnViewAppointments = findViewById(R.id.btnViewAppointments);
        btnViewPrescriptions = findViewById(R.id.btnViewPrescriptions);
        logout = findViewById(R.id.logoutPatient);

        // Setting click listeners for the buttons
        btnSearchDoctors.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PatientDashboardActivity.this, SearchDoctorsActivity.class);
                startActivity(intent);
            }
        });

        btnViewAppointments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PatientDashboardActivity.this, ViewAppointmentsActivity.class);
                startActivity(intent);
            }
        });

        btnViewPrescriptions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PatientDashboardActivity.this, ViewPrescriptionsActivity.class);
                startActivity(intent);
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishAffinity();
            }
        });
    }
}
