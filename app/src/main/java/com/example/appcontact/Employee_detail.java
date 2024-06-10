package com.example.appcontact;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appcontact.model.DatabaseHelper;

import java.io.ByteArrayOutputStream;

public class Employee_detail extends AppCompatActivity {

    private TextView textViewHoTenNV, textViewChucVuNV, textViewEmailNV, textViewSdtNV, txtTendv;
    private ImageView imageViewAnhDaiDienNV;
    private DatabaseHelper databaseHelper;
    private Button btnxoa, btnsua;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_detail);

        btnsua = findViewById(R.id.btnsuanv);
        btnxoa = findViewById(R.id.btnxoanv);

        textViewHoTenNV = findViewById(R.id.textViewHoTenNV);
        textViewChucVuNV = findViewById(R.id.textViewChucVuNV);
        textViewEmailNV = findViewById(R.id.textViewEmailNV);
        textViewSdtNV = findViewById(R.id.textViewSdtNV);
        imageViewAnhDaiDienNV = findViewById(R.id.imgAnhDaiDienNV);
        txtTendv = findViewById(R.id.txtTendv);

        Intent intent = getIntent();
        if (intent != null) {
            String hoTen = intent.getStringExtra("hoTen");
            String chucVu = intent.getStringExtra("chucvu");
            String email = intent.getStringExtra("email");
            String sdt = intent.getStringExtra("sdt");
            byte[] avatarByteArray = intent.getByteArrayExtra("avatar");
            String tenDv = intent.getStringExtra("tenDonVi");

            textViewHoTenNV.setText(hoTen);
            textViewSdtNV.setText(sdt);
            textViewChucVuNV.setText(chucVu);
            textViewEmailNV.setText(email);
            txtTendv.setText(tenDv);

            if (avatarByteArray != null) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(avatarByteArray, 0, avatarByteArray.length);
                imageViewAnhDaiDienNV.setImageBitmap(bitmap);
            }
        }

        btnsua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (intent != null) {
                    String hoTen = intent.getStringExtra("hoTen");
                    String chucVu = intent.getStringExtra("chucvu");
                    String email = intent.getStringExtra("email");
                    String sdt = intent.getStringExtra("sdt");
                    String tendvi = intent.getStringExtra("tenDonvi");
                    byte[] avatarByteArray = intent.getByteArrayExtra("avatar");

                    // Navigate to EmployeeEdit activity
                    // Uncomment and replace with your EmployeeEdit activity
                    // Intent editIntent = new Intent(EmployeeDetail.this, EmployeeEdit.class);
                    // editIntent.putExtra("hoTen", hoTen);
                    // editIntent.putExtra("chucvu", chucVu);
                    // editIntent.putExtra("email", email);
                    // editIntent.putExtra("sdt", sdt);
                    // editIntent.putExtra("tenDonvi", tendvi);
                    // editIntent.putExtra("avatar", avatarByteArray);
                    // startActivity(editIntent);
                }
            }
        });

        btnxoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteEmployee();
            }
        });
    }

    private static byte[] convertBitmapToByteArray(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream.toByteArray();
    }

    private void deleteEmployee() {
        Intent intent = getIntent();
        if (intent != null) {
            String hoTen = intent.getStringExtra("hoTen");

            databaseHelper = new DatabaseHelper(this);
            SQLiteDatabase db = databaseHelper.getWritableDatabase();
            int deletedRows = db.delete(DatabaseHelper.TABLE_CREATE_EMPLOYEES, DatabaseHelper.COLUMN_EMPLOYEE_NAME + "=?", new String[]{hoTen});
            db.close();

            if (deletedRows > 0) {
                Toast.makeText(Employee_detail.this, "Employee deleted successfully", Toast.LENGTH_SHORT).show();
                // Navigate back or perform any other action
                finish();
            } else {
                Toast.makeText(Employee_detail.this, "Failed to delete employee", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
