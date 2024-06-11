package com.example.appcontact;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appcontact.model.DatabaseHelper;

import java.util.ArrayList;

public class UnitList extends AppCompatActivity {
    ListView listViewUnits;
    DatabaseHelper dbHelper;
    ArrayAdapter<String> adapter;
    ArrayList<String> unitList;
    Button buttondv2, buttonnv2;
    ImageButton imgbtn;
    SearchView searchViewUnits;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unit_list);

        imgbtn = findViewById(R.id.fab);
        imgbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UnitList.this, UnitAdd.class);
                startActivity(intent);
            }
        });

        buttondv2 = findViewById(R.id.buttondv2);
        buttonnv2 = findViewById(R.id.buttonnv2);
        searchViewUnits = findViewById(R.id.searchViewdv);

        buttondv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UnitList.this, UnitList.class);
                startActivity(intent);
            }
        });

        buttonnv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UnitList.this, EmployeeList.class);
                startActivity(intent);
            }
        });

        // Initialize the database helper and unit list
        dbHelper = new DatabaseHelper(this);
        listViewUnits = findViewById(R.id.lsv_donvi);
        unitList = new ArrayList<>();

        // Load unit data
        loadUnits();

        // Set up the adapter and attach it to the ListView
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, unitList);
        listViewUnits.setAdapter(adapter);

        // Set up search view listener
        searchViewUnits.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });
    }

    private void loadUnits() {
        // Get a readable database
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // Query the database
        Cursor cursor = db.query(DatabaseHelper.TABLE_UNITS, null, null, null, null, null, null);

        // Clear the existing list to avoid duplicates
        unitList.clear();
        // Iterate through the cursor and populate the unit list
        if (cursor.moveToFirst()) {
            do {
                String name = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_UNIT_NAME));
                String phone = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_UNIT_PHONE));
                String email = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_UNIT_EMAIL));
                String address = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_UNIT_ADDRESS));
                unitList.add(name + " - " + phone + " - " + email + " - " + address);
            } while (cursor.moveToNext());
        }

        // Close the cursor and database
        cursor.close();
        db.close();
    }
}
