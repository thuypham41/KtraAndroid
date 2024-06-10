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

public class EmployeeList extends AppCompatActivity {
    private ListView listViewEmployees;
    private DatabaseHelper dbHelper;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> employeeList;
    private ImageButton btn_add;
    private Button buttondvi1, buttonnv1;
    private SearchView searchViewnv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //EdgeToEdge.enable(this); // Commented out as it seems non-standard
        setContentView(R.layout.activity_employee_list);
        buttonnv1 = findViewById(R.id.buttonnv1);
        buttondvi1 = findViewById(R.id.buttondvi1);
        searchViewnv = findViewById(R.id.searchViewnv);

        // Initialize views
        listViewEmployees = findViewById(R.id.lsv_nhanvien);
        btn_add = findViewById(R.id.fab);

        // Set the button click listener
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EmployeeList.this, Employee_add.class);
                startActivity(intent);
            }
        });
        buttondvi1.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(EmployeeList.this, UnitList.class);
                        startActivity(intent);
                    }
                });
        buttonnv1.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(EmployeeList.this, EmployeeList.class);
                        startActivity(intent);
                    }
                });

        // Initialize the database helper and employee list
        dbHelper = new DatabaseHelper(this);
        employeeList = new ArrayList<>();

        // Load employee data
        loadEmployee();

        // Set up the adapter and attach it to the ListView
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, employeeList);
        listViewEmployees.setAdapter(adapter);

        // Set up search view listener
        searchViewnv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
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

    private void loadEmployee() {
        // Get a readable database
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // Query the database
        Cursor cursor = db.query(DatabaseHelper.TABLE_EMPLOYEES, null, null, null, null, null, null);

        // Clear the existing list to avoid duplicates
        employeeList.clear();

        // Iterate through the cursor and populate the employee list
        if (cursor.moveToFirst()) {
            do {
                String name = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_EMPLOYEE_NAME));
                String phone = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_EMPLOYEE_PHONE));
                String email = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_EMPLOYEE_EMAIL));
                String position = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_EMPLOYEE_POSITION));
                employeeList.add(name + " - " + phone + " - " + email + " - " + position);
            } while (cursor.moveToNext());
        }

        // Close the cursor and database
        cursor.close();
        db.close();
    }
}
