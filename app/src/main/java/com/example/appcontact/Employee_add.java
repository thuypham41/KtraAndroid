package com.example.appcontact;

import static android.app.PendingIntent.getActivity;

import android.Manifest;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.appcontact.model.DatabaseHelper;
import com.google.android.material.textfield.TextInputEditText;

import java.io.IOException;

public class Employee_add extends AppCompatActivity {
    private static final int REQUEST_CODE_SELECT_AVATAR = 1;
    private static final int REQUEST_CODE_READ_EXTERNAL_STORAGE = 2;

    TextInputEditText txtmnv, txtname, txtsdt, txtmail, txtchucvu;
    Button btn_add, buttonSelectAvatar;
    ImageView imageViewAvatar;
    Uri selectedImageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_employee_add);

        txtmnv = findViewById(R.id.editTextEmployeeId);
        txtname = findViewById(R.id.editTextEmployeeName);
        txtsdt = findViewById(R.id.editTextPhone);
        txtmail = findViewById(R.id.editTextEmail);
        txtchucvu = findViewById(R.id.editTextPosition);
        btn_add = findViewById(R.id.buttonSave);
        imageViewAvatar = findViewById(R.id.imageViewAvatar);
        buttonSelectAvatar = findViewById(R.id.buttonSelectAvatar);

        imageViewAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Open image picker
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 1);
            }
        });

//        buttonSelectAvatar.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (ContextCompat.checkSelfPermission(Employee_add.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
//                    ActivityCompat.requestPermissions(Employee_add.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CODE_READ_EXTERNAL_STORAGE);
//                } else {
//                    openGallery();
//                }
//            }
//        });

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = txtname.getText().toString();
                String sdt = txtsdt.getText().toString();
                String email = txtmail.getText().toString();
                String chucvu = txtchucvu.getText().toString();

                // Create an instance of DatabaseHelper
                DatabaseHelper dbHelper = new DatabaseHelper(Employee_add.this);

                // Get writable database
                SQLiteDatabase db = dbHelper.getWritableDatabase();

                // Insert values
                ContentValues values = new ContentValues();
                values.put(DatabaseHelper.COLUMN_EMPLOYEE_NAME, name);
                values.put(DatabaseHelper.COLUMN_EMPLOYEE_PHONE, sdt);
                values.put(DatabaseHelper.COLUMN_EMPLOYEE_EMAIL, email);
                values.put(DatabaseHelper.COLUMN_EMPLOYEE_POSITION, chucvu);
                if (selectedImageUri != null) {
                    values.put(DatabaseHelper.COLUMN_EMPLOYEE_AVATAR, selectedImageUri.toString());
                }

                // Insert the new row
                db.insert(DatabaseHelper.TABLE_EMPLOYEES, null, values);

                // Close the database
                db.close();

                // Finish the activity
                finish();
            }
        });
    }

//    private void openGallery() {
//        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//        startActivityForResult(intent, REQUEST_CODE_SELECT_AVATAR);
//    }
//
//
//
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        if (requestCode == REQUEST_CODE_READ_EXTERNAL_STORAGE) {
//            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                openGallery();
//            }
//        }
//    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_SELECT_AVATAR && resultCode == RESULT_OK && data != null) {
            // Handle image selection
            Uri selectedImage = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImage);

                // Calculate the desired size for the circular image
                int sizeInDp = 120;
                int sizeInPx = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, sizeInDp, getResources().getDisplayMetrics());

                // Create a square bitmap with the desired size
                Bitmap squareBitmap = Bitmap.createScaledBitmap(bitmap, sizeInPx, sizeInPx, true);

                // Create circular image
                Bitmap circleBitmap = Bitmap.createBitmap(sizeInPx, sizeInPx, Bitmap.Config.ARGB_8888);
                Canvas canvas = new Canvas(circleBitmap);
                Paint paint = new Paint();
                paint.setAntiAlias(true);
                canvas.drawCircle(sizeInPx / 2f, sizeInPx / 2f, sizeInPx / 2f, paint);
                paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
                canvas.drawBitmap(squareBitmap, 0, 0, paint);

                imageViewAvatar.setImageBitmap(circleBitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }



}
