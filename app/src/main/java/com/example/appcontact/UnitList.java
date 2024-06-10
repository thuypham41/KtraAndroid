package com.example.appcontact;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class UnitList extends AppCompatActivity {
    Button buttondv2,buttonnv2 ;
    ImageButton imgbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_unit_list);
imgbtn = findViewById(R.id.fab);
        imgbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UnitList.this,UnitAdd.class);
                startActivity(intent);
            }
        });
        buttondv2 = findViewById(R.id.buttondv2);
        buttonnv2 = findViewById(R.id.buttonnv2);

       buttondv2.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Intent intent = new Intent(UnitList.this,UnitList.class);
               startActivity(intent);
           }
       });
       buttonnv2.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Intent intent = new Intent(UnitList.this,EmployeeList.class);
               startActivity(intent);
    }
       });
    }

}