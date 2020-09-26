package com.example.giftme;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    EditText txtName,address,phone,items,price,boy,date;
    Button btnInsert,btn2,btntest;
    DatabaseReference reff;
    Delivery delivery;
    long maxDeliveryID=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setTitle("GiftME Delivery Administrator");

        txtName=(EditText) findViewById(R.id.txtName);
        address=(EditText) findViewById(R.id.address);
        phone=(EditText) findViewById(R.id.phone);
        items=(EditText) findViewById(R.id.items);
        price=(EditText) findViewById(R.id.price);
        boy=(EditText) findViewById(R.id.boyid);
        date=(EditText) findViewById(R.id.date);
        btnInsert=(Button) findViewById(R.id.btn1);
        btn2=(Button)findViewById(R.id.btn2);
        btntest=(Button)findViewById(R.id.button12);

        delivery = new Delivery();
       reff = FirebaseDatabase.getInstance().getReference().child("Delivery");

        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    maxDeliveryID=(snapshot.getChildrenCount());
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) { }
        });

        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String txt_name = txtName.getText().toString();
                String txt_address= address.getText().toString();
                String txt_phone= phone.getText().toString();
                String txt_items = items.getText().toString();
                String  txt_price = price.getText().toString();
                String txt_boyid = boy.getText().toString();
                String txt_date = date.getText().toString();

                if (txt_name.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please enter name", Toast.LENGTH_LONG).show();
                }
                else if(txt_address.isEmpty()){
                    Toast.makeText(MainActivity.this, "Please enter address", Toast.LENGTH_LONG).show();
                }
                else if(txt_phone.isEmpty()){
                    Toast.makeText(MainActivity.this, "please enter phone", Toast.LENGTH_LONG).show();
                }
                else if(txt_phone.length()<10){
                    Toast.makeText(MainActivity.this, "Phone number incorrect format", Toast.LENGTH_LONG).show();
                }

                else if(txt_items.isEmpty()){
                    Toast.makeText(MainActivity.this, "Please enter items", Toast.LENGTH_LONG).show();
                }
                else if(txt_price.isEmpty()){
                    Toast.makeText(MainActivity.this, "Please enter price", Toast.LENGTH_LONG).show();
                }

                else if(txt_boyid.isEmpty()){
                    Toast.makeText(MainActivity.this, "Assigned D.boy's ID is empty", Toast.LENGTH_LONG).show();
                }
                else if(txt_date.isEmpty()){
                    Toast.makeText(MainActivity.this, "Please enter date", Toast.LENGTH_LONG).show();
                }
                else {

                    double priceD = Double.parseDouble(price.getText().toString().trim());

                    delivery.setName(txtName.getText().toString().trim());
                    delivery.setAddress(address.getText().toString().trim());
                    delivery.setPhone(phone.getText().toString().trim());
                    delivery.setItems(items.getText().toString().trim());
                    delivery.setPrice(priceD);
                    delivery.setBoyid(boy.getText().toString().trim());
                    delivery.setDate(date.getText().toString().trim());
                    //reff.child(String.valueOf(maxDeliveryID+1)).setValue(delivery);


                    reff.push().setValue(delivery);
                    Toast.makeText(MainActivity.this, "Delivery inserted successfully", Toast.LENGTH_LONG).show();
                }
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openNext();
            }
        });

    }
    public void openNext(){
        Intent intent= new Intent(this,MainActivity2.class);
                startActivity(intent);
    }


}