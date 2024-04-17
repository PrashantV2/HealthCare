package com.example.healthcare;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class ViewDoctorAppointmentsActivity extends AppCompatActivity {

    private ListView appointmentDoctorListView;
    private DatabaseHelper databaseHelper;
    private List<String> appointments;
    private ArrayAdapter<String> appointmentAdapter;
    private String currentDoctorName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_doctor_appointments);

        // Initialize Views
        appointmentDoctorListView = findViewById(R.id.appointmentDoctorListView);

        // Create DatabaseHelper instance
        databaseHelper = new DatabaseHelper(this);

        // Get the current logged-in doctor's name from Shared Preferences
        currentDoctorName = SharedPrefManager.getInstance(ViewDoctorAppointmentsActivity.this).getUsername();

        // If no doctor name found (which shouldn't happen), show an error or return from the activity
        if (currentDoctorName == null) {
            // Show an error message (you can use a Toast or Dialog)
            // And then return
            return;
        }

        // Fetch all appointments for this doctor from the database
        appointments = databaseHelper.getAppointments();

        // Set the appointments to the ListView
        appointmentAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, appointments);
        appointmentDoctorListView.setAdapter(appointmentAdapter);
    }
}
