package com.example.madd_giftme_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.madd_giftme_app.Model.Riders;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class Admin_add_to_delivery extends AppCompatActivity {

    TextView name , address , city , phone , postal , oid, delid;
    Button save;
    Spinner spinner;
    List riders;
    DatabaseReference ref , riderRef;
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add_to_delivery);

        riderRef = FirebaseDatabase.getInstance().getReference().child("Riders");

        riders = new ArrayList();
        getRiders();

        riders.add("Select a Rider");

        id = getIntent().getStringExtra("delivery");

        ref = FirebaseDatabase.getInstance().getReference().child("Delivery").child(id);

        name = findViewById(R.id.ADMIN_DELIVERY_NAME);
        address = findViewById(R.id.ADMIN_DELIVERY_ADDRESS);
        city = findViewById(R.id.ADMIN_DELIVERY_CITY);
        postal = findViewById(R.id.ADMIN_DELIVERY_POSTAL);
        oid = findViewById(R.id.ADMIN_DELIVERY_ORDERID);
        delid = findViewById(R.id.ADMIN_DELIVERY_DELID);
        phone = findViewById(R.id.ADMIN_DELIVERY_PHONE);
        save = findViewById(R.id.ADMIN_DELIVERY_SAVE);
        spinner = findViewById(R.id.ADMIN_DELIVERY_RIDER_SPINNER);

        ArrayAdapter<String> test = new ArrayAdapter<>(getApplicationContext() , android.R.layout.simple_list_item_1,
                riders);
        test.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(test);
        spinner.setSelection(0);

        setValues();

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addDelivery();
            }
        });

        ImageButton home , account , products , orders;

        home = findViewById(R.id.btnHome);
        account = findViewById(R.id.btnAccount);
        products = findViewById(R.id.btnProducts);
        orders = findViewById(R.id.btnOrders);

        products.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),Admin_products.class);
                startActivity(i);
            }
        });
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),Admin_home.class);
                startActivity(i);
            }
        });
        orders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),Admin_order_home.class);
                startActivity(i);
            }
        });
        account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),Admin_account.class);
                startActivity(i);
            }
        });




    }

    private void addDelivery() {

        DatabaseReference del = FirebaseDatabase.getInstance().getReference().child("Delivery");

        del.child(id).child("rider").setValue(spinner.getSelectedItem());

    }

    private void getRiders() {


        riderRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
               long x = snapshot.getChildrenCount();
                for(int i = 1 ; i <= x ; i++){

                    riderRef.child("RIDER0"+i).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            riders.add(snapshot.child("riderid").getValue().toString());
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                }




            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




    }

    private void setValues() {


        //setValueForSelection(getIntent().getStringExtra("occasion"));

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                oid.setText("Order ID : " + snapshot.child("orderid").getValue().toString());
                delid.setText("Delivery ID : " + snapshot.child("deliveryid").getValue().toString());
                name.setText("Customer Name : " + snapshot.child("name").getValue().toString());
                address.setText("Delivery Address : " + snapshot.child("address").getValue().toString());
                city.setText("City: " + snapshot.child("city").getValue().toString());
                phone.setText("Contact Number : " + snapshot.child("phone").getValue().toString());
                postal.setText("Postal Code : " + snapshot.child("postalcode").getValue().toString());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}