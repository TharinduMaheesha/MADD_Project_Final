package com.example.madd_giftme_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

public class Admin_order_home extends AppCompatActivity {

    CardView cardNew , cardAccepted , cardDelivery;
    ImageButton home , account , products , orders;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_order_home);

        home = findViewById(R.id.btnHome);
        account = findViewById(R.id.btnAccount);
        products = findViewById(R.id.btnProducts);
        orders = findViewById(R.id.btnOrders);


        products.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Admin_order_home.this,Admin_products.class);
                startActivity(i);
            }
        });
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Admin_order_home.this,Admin_home.class);
                startActivity(i);
            }
        });
        orders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"You are curretly in the Orders Page" , Toast.LENGTH_LONG).show();

            }
        });

        cardNew = findViewById(R.id.CARD_NEW_ORDERS);
        cardAccepted = findViewById(R.id.CARD_ACCEPTED_ORDERS);
        cardDelivery = findViewById(R.id.CARD_DELIVERIES);

        cardNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Admin_order_home.this,Admin_new_orders.class);
                startActivity(i);
            }
        });


    }
}