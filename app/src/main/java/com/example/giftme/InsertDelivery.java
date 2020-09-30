/*N.I.T.Hewage_IT19220116
 * MADD mini project 2020_Y2S2_GIFT_ODERIN_GAPP
 * Delivery managment
 * */
package com.example.giftme;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/*
* class for record the details of a particular delivery for an order
* */
public class InsertDelivery extends AppCompatActivity {

    EditText txtName,address,phone,items,price,boy,date;
    Button btnInsert,btnHistory,btnback;
    DatabaseReference reff;
    Delivery delivery;
    long maxDeliveryID=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_delivery);
        getSupportActionBar().setTitle("GiftME Delivery Administrator");

        txtName=(EditText) findViewById(R.id.txtName);
        address=(EditText) findViewById(R.id.address);
        phone=(EditText) findViewById(R.id.phone);
        items=(EditText) findViewById(R.id.items);
        price=(EditText) findViewById(R.id.price);
        boy=(EditText) findViewById(R.id.boyid);
        date=(EditText) findViewById(R.id.date);
        btnInsert=(Button) findViewById(R.id.btn1);
        btnHistory=(Button)findViewById(R.id.btn2);
        btnback=(Button)findViewById(R.id.btn3);


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


                /*
                * validations for the inputs when insert data of a delivery
                * */
                if (txt_name.isEmpty()) {
                    Toast.makeText(InsertDelivery.this, "Please enter name", Toast.LENGTH_LONG).show();
                }
                else if(txt_address.isEmpty()){
                    Toast.makeText(InsertDelivery.this, "Please enter address", Toast.LENGTH_LONG).show();
                }
                else if(txt_phone.isEmpty()){
                    Toast.makeText(InsertDelivery.this, "please enter phone", Toast.LENGTH_LONG).show();
                }
                else if(txt_phone.length()<10){
                    Toast.makeText(InsertDelivery.this, "Phone number incorrect format", Toast.LENGTH_LONG).show();
                }

                else if(txt_items.isEmpty()){
                    Toast.makeText(InsertDelivery.this, "Please enter items", Toast.LENGTH_LONG).show();
                }
                else if(txt_price.isEmpty()){
                    Toast.makeText(InsertDelivery.this, "Please enter price", Toast.LENGTH_LONG).show();
                }

                else if(txt_boyid.isEmpty()){
                    Toast.makeText(InsertDelivery.this, "Assigned D.boy's ID is empty", Toast.LENGTH_LONG).show();
                }
                else if(txt_date.isEmpty()){
                    Toast.makeText(InsertDelivery.this, "Please enter date", Toast.LENGTH_LONG).show();
                }
                else {


                    /*
                    * insert data to the firebase database
                    * */
                    double priceD = Double.parseDouble(price.getText().toString().trim());

                    delivery.setName(txtName.getText().toString().trim());
                    delivery.setAddress(address.getText().toString().trim());
                    delivery.setPhone(phone.getText().toString().trim());
                    delivery.setItems(items.getText().toString().trim());
                    delivery.setPrice(priceD);
                    delivery.setBoyid(boy.getText().toString().trim());
                    delivery.setDate(date.getText().toString().trim());

                    reff.push().setValue(delivery);
                    Toast.makeText(InsertDelivery.this, "Delivery inserted successfully", Toast.LENGTH_LONG).show();
                }
            }
        });

        //go back button
        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Goback();
            }
        });

        btnHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewDeliveryhitory();
            }
        });

    }
    //one page back
    public void Goback(){
        Intent intent= new Intent(this,MainActivity.class); //go to home activity
        startActivity(intent);
    }

    //view delivery history
    public void viewDeliveryhitory(){
        Intent intent3=new Intent(this,Viewdelivery.class);
        startActivity(intent3);
        Toast.makeText(InsertDelivery.this,"Delivery History is here",Toast.LENGTH_LONG).show();
    }
}