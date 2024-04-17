package com.example.healthcare;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class PrescribeMedicineActivity extends AppCompatActivity {

    private Spinner patientDropdown;
    private EditText medicineEditText, dosageEditText;
    private Button prescribeButton;
    private DatabaseHelper databaseHelper;
    private List<String> patientNames;
    private ArrayAdapter<String> patientAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prescribe_medicine);

        patientDropdown = findViewById(R.id.patientDropdown);
        medicineEditText = findViewById(R.id.medicineEditText);
        dosageEditText = findViewById(R.id.dosageEditText);
        prescribeButton = findViewById(R.id.prescribeButton);

        databaseHelper = new DatabaseHelper(this);
        patientNames = databaseHelper.getAllPatientNames();
        patientAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, patientNames);
        patientDropdown.setAdapter(patientAdapter);

        prescribeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String patientName = patientDropdown.getSelectedItem().toString();
                String medicine = medicineEditText.getText().toString().trim();
                String dosage = dosageEditText.getText().toString().trim();

                if (medicine.isEmpty() || dosage.isEmpty()) {
                    Toast.makeText(PrescribeMedicineActivity.this, "Please fill out all fields", Toast.LENGTH_SHORT).show();
                } else {
                    boolean result = databaseHelper.createPrescription(patientName, medicine, dosage);
                    if (result) {
                        Toast.makeText(PrescribeMedicineActivity.this, "Prescription added", Toast.LENGTH_SHORT).show();
                        medicineEditText.setText("");
                        dosageEditText.setText("");
                    } else {
                        Toast.makeText(PrescribeMedicineActivity.this, "Error adding prescription", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}
