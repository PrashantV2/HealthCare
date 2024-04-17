package com.example.healthcare;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.List;

public class SearchDoctorsActivity extends AppCompatActivity {

    private EditText searchEditText;
    private Button searchButton;
    private ListView doctorsListView;
    private List<String> doctorsList; // This will hold our search results
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_doctors);

        searchEditText = findViewById(R.id.searchEditText);
        searchButton = findViewById(R.id.searchButton);
        doctorsListView = findViewById(R.id.doctorsListView);

        doctorsList = new ArrayList<>();
        databaseHelper = new DatabaseHelper(this); // Initialize the DatabaseHelper instance

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchForDoctors();
            }
        });
    }

    private void searchForDoctors() {
        String query = searchEditText.getText().toString().trim().toLowerCase();
        doctorsList.clear();

        // Fetch doctors from the database based on the query
        doctorsList = databaseHelper.searchDoctors(query);

        if (doctorsList.isEmpty()) {
            doctorsList.add("No doctors found for the specified specialty.");
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, doctorsList);
        doctorsListView.setAdapter(adapter);
    }
}
