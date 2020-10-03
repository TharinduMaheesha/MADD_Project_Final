package com.example.madd_giftme_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Admin_home extends AppCompatActivity {

    ImageButton home , account , products , orders;
    TextView date;
    String curDate;
    CardView cardrider , cardGuide;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);

        home = findViewById(R.id.btnHome);
        account = findViewById(R.id.btnAccount);
        products = findViewById(R.id.btnProducts);
        orders = findViewById(R.id.btnOrders);
        date = findViewById(R.id.TV_DATE);

        cardrider = findViewById(R.id.cardRider);
        cardGuide = findViewById(R.id.cardGuide);

        Calendar calendar = Calendar.getInstance();

        SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd, yyy");
        curDate = currentDate.format(calendar.getTime());

        date.setText(curDate);



        products.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Admin_home.this,Admin_products.class);
                startActivity(i);
            }
        });
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"You are curretly in the Home Page" , Toast.LENGTH_LONG).show();
            }
        });
        orders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Admin_home.this,Admin_order_home.class);
                startActivity(i);
            }
        });
        account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Admin_home.this,Admin_account.class);
                startActivity(i);
            }
        });


        cardrider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Admin_home.this,Admin_view_riders.class);
                startActivity(i);
            }
        });

        cardGuide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                guide();
            }
        });




    }

    public void guide () {
        Intent intent = new Intent(this, DeliveryControlGuide.class);
        startActivity(intent);
        Toast.makeText(getApplicationContext(), "Use this guide for the first time", Toast.LENGTH_LONG).show();
    }
}