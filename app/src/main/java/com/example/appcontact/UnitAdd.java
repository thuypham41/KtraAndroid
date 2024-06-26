package com.example.appcontact;

import android.Manifest;
import android.content.ContentValues;
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
import android.widget.EditText;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.appcontact.model.DatabaseHelper;

import java.io.IOException;

public class UnitAdd extends AppCompatActivity {
    private static final int REQUEST_CODE_SELECT_AVATAR = 1;
    private static final int REQUEST_CODE_READ_EXTERNAL_STORAGE = 2;

    EditText unitCode, unitName, unitEmail, unitWebsite, unitPhone, unitAddress, parentId;
    Button saveUnitButton, selectLogoButton;
    ImageView unitLogo;
    Uri selectedImageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_unit_add);

        unitCode = findViewById(R.id.unitCode);
        unitName = findViewById(R.id.unitName);
        unitEmail = findViewById(R.id.unitEmail);
        unitWebsite = findViewById(R.id.unitWebsite);
        unitPhone = findViewById(R.id.unitPhone);
        unitAddress = findViewById(R.id.unitAddress);
        parentId = findViewById(R.id.parentId);
        saveUnitButton = findViewById(R.id.saveUnitButton);
        selectLogoButton = findViewById(R.id.selectLogoButton);
        unitLogo = findViewById(R.id.unitLogo);

        selectLogoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(UnitAdd.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(UnitAdd.this,
                            new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                            REQUEST_CODE_READ_EXTERNAL_STORAGE);
                } else {
                    openGallery();
                }
            }
        });

        saveUnitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = unitName.getText().toString();
                String email = unitEmail.getText().toString();
                String website = unitWebsite.getText().toString();
                String address = unitAddress.getText().toString();
                String phone = unitPhone.getText().toString();
                String parent_id = parentId.getText().toString();

                DatabaseHelper dbHelper = new DatabaseHelper(UnitAdd.this);
                SQLiteDatabase db = dbHelper.getWritableDatabase();

                ContentValues values = new ContentValues();
                values.put(DatabaseHelper.COLUMN_UNIT_NAME, name);
                values.put(DatabaseHelper.COLUMN_UNIT_EMAIL, email);
                values.put(DatabaseHelper.COLUMN_UNIT_WEBSITE, website);
                values.put(DatabaseHelper.COLUMN_UNIT_ADDRESS, address);
                values.put(DatabaseHelper.COLUMN_UNIT_PHONE, phone);
                values.put(DatabaseHelper.COLUMN_UNIT_PARENT_ID, parent_id);

                if (selectedImageUri != null) {
                    values.put(DatabaseHelper.COLUMN_UNIT_LOGO, selectedImageUri.toString());
                }

                db.insert(DatabaseHelper.TABLE_UNITS, null, values);
                db.close();
                finish();
            }
        });
    }

    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, REQUEST_CODE_SELECT_AVATAR);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_SELECT_AVATAR && resultCode == RESULT_OK && data != null) {
            selectedImageUri = data.getData();
            unitLogo.setImageURI(selectedImageUri);

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImageUri);
                int sizeInDp = 120;
                int sizeInPx = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, sizeInDp, getResources().getDisplayMetrics());

                Bitmap squareBitmap = Bitmap.createScaledBitmap(bitmap, sizeInPx, sizeInPx, true);
                Bitmap circleBitmap = Bitmap.createBitmap(sizeInPx, sizeInPx, Bitmap.Config.ARGB_8888);
                Canvas canvas = new Canvas(circleBitmap);
                Paint paint = new Paint();
                paint.setAntiAlias(true);
                canvas.drawCircle(sizeInPx / 2f, sizeInPx / 2f, sizeInPx / 2f, paint);
                paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
                canvas.drawBitmap(squareBitmap, 0, 0, paint);

                unitLogo.setImageBitmap(circleBitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        if (requestCode == REQUEST_CODE_READ_EXTERNAL_STORAGE) {
//            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                openGallery();
//            }
//        }
//    }
}
