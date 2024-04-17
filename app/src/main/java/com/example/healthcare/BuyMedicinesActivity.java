package com.example.healthcare;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.List;

public class BuyMedicinesActivity extends AppCompatActivity {

    private ListView medicinesListView;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_medicines);

        medicinesListView = findViewById(R.id.medicinesListView);
        databaseHelper = new DatabaseHelper(this);

        List<String> medicines = databaseHelper.getMedicines();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, medicines);
        medicinesListView.setAdapter(adapter);
    }
}
