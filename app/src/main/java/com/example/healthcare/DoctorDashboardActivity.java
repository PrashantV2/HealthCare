package com.example.healthcare;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class DoctorDashboardActivity extends AppCompatActivity {

    private Button btnViewAllAppointments, btnPrescribeMedicine, logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_dashboard);

        btnViewAllAppointments = findViewById(R.id.btnViewAllAppointments);
        btnPrescribeMedicine = findViewById(R.id.btnPrescribeMedicine);
        logout = findViewById(R.id.logoutDoctor);

        btnViewAllAppointments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DoctorDashboardActivity.this, ViewDoctorAppointmentsActivity.class);
                startActivity(intent);
            }
        });

        btnPrescribeMedicine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DoctorDashboardActivity.this, PrescribeMedicineActivity.class);
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
