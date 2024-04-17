package com.example.healthcare;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.List;

public class ViewPrescriptionsActivity extends AppCompatActivity {

    private ListView prescriptionsListView;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_prescriptions);

        prescriptionsListView = findViewById(R.id.prescriptionsListView);
        databaseHelper = new DatabaseHelper(this);

        List<String> prescriptions = databaseHelper.getPrescriptions();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, prescriptions);
        prescriptionsListView.setAdapter(adapter);
    }
}
