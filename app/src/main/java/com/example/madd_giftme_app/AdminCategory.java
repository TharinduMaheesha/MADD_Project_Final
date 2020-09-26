package com.example.madd_giftme_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class AdminCategory extends AppCompatActivity {

    private Button birthday, valentine;
    private Button anniversary, graduation;
    private Button fathers_day, mothers_day ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_category);

        birthday = (Button) findViewById(R.id.birthday);
        valentine = (Button) findViewById(R.id.valentine);
        anniversary = (Button) findViewById(R.id.anniversary);
        graduation = (Button) findViewById(R.id.graduation);
        fathers_day = (Button) findViewById(R.id.fathers_day);
        mothers_day = (Button) findViewById(R.id.mothers_day);

        birthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(AdminCategory.this, AdminAddNewProductsToOccasions.class);
                intent.putExtra("event", "birthday");
                startActivity(intent);
            }
        });

        valentine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(AdminCategory.this, AdminAddNewProductsToOccasions.class);
                intent.putExtra("event", "valentine");
                startActivity(intent);
            }
        });

        anniversary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(AdminCategory.this, AdminAddNewProductsToOccasions.class);
                intent.putExtra("event", "anniversary");
                startActivity(intent);
            }
        });

        graduation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(AdminCategory.this, AdminAddNewProductsToOccasions.class);
                intent.putExtra("event", "graduation");
                startActivity(intent);
            }
        });

        fathers_day.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(AdminCategory.this, AdminAddNewProductsToOccasions.class);
                intent.putExtra("event", "fathers_day");
                startActivity(intent);
            }
        });

        mothers_day.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(AdminCategory.this, AdminAddNewProductsToOccasions.class);
                intent.putExtra("event", "mothers_day");
                startActivity(intent);
            }
        });
    }
}