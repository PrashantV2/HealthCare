package com.example.healthcare;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.List;

public class ViewAppointmentsActivity extends AppCompatActivity {

    private Spinner doctorDropdown;
    private EditText appointmentDetailEditText;
    private Button saveAppointmentButton;
    private ListView appointmentListView;

    private DatabaseHelper databaseHelper;
    private List<String> doctorNames;
    private ArrayAdapter<String> doctorAdapter;
    private ArrayAdapter<String> appointmentAdapter;
    private List<String> appointments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_appointments);

        doctorDropdown = findViewById(R.id.doctorDropdown);
        appointmentDetailEditText = findViewById(R.id.appointmentDetailEditText);
        saveAppointmentButton = findViewById(R.id.saveAppointmentButton);
        appointmentListView = findViewById(R.id.appointmentListView);

        databaseHelper = new DatabaseHelper(this);
        doctorNames = databaseHelper.getAllDoctorNames();
        doctorAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, doctorNames);
        doctorDropdown.setAdapter(doctorAdapter);

        saveAppointmentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String doctorName = "";
                if (doctorDropdown.getSelectedItem() != null) {
                    doctorName = doctorDropdown.getSelectedItem().toString();
                } else {
                    // Handle the case where no doctor is selected from the dropdown
                    // For example, you can show a Toast message to inform the user
                    Toast.makeText(ViewAppointmentsActivity.this, "Please select a doctor", Toast.LENGTH_SHORT).show();
                    return;
                }
                String details = appointmentDetailEditText.getText().toString().trim();
                String currentPatientName = SharedPrefManager.getInstance(ViewAppointmentsActivity.this).getUsername();

                databaseHelper.createAppointment(doctorName, currentPatientName ,details);
                refreshAppointmentList();
            }
        });

        appointments = new ArrayList<>();
        appointmentAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, appointments);
        appointmentListView.setAdapter(appointmentAdapter);
        refreshAppointmentList();
    }

    private void refreshAppointmentList() {
        appointments.clear();
        appointments.addAll(databaseHelper.getAppointments());
        appointmentAdapter.notifyDataSetChanged();
    }
}
