package com.example.giftme;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.regex.Pattern;

public class MainActivity4 extends AppCompatActivity {
    Button reg,backto;
    DatabaseReference DBref;
    EditText name,address,phone,email,age,bike,nic,lison;
    long minID=2020;
    AddRider rider;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);
        getSupportActionBar().setTitle("GiftME Delivery Administrator");

        name = (EditText) findViewById(R.id.nameD);
        address = (EditText) findViewById(R.id.addressD);
        phone = (EditText) findViewById(R.id.phoneD);
        email = (EditText) findViewById(R.id.emailD);
        age = (EditText) findViewById(R.id.ageD);
        bike = (EditText) findViewById(R.id.bikenumberD);
        nic = (EditText) findViewById(R.id.nicD);
        lison = (EditText) findViewById(R.id.lisonD);
        backto = (Button) findViewById(R.id.btnBack);
        reg = (Button) findViewById(R.id.reg);

        rider = new AddRider();
        DBref = FirebaseDatabase.getInstance().getReference().child("DeliveryEmployees");

        DBref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    minID=(snapshot.getChildrenCount());
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) { }
        });
                reg.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String txt_name = name.getText().toString();
                        String txt_address= address.getText().toString();
                        String txt_phone= phone.getText().toString();
                        String txt_email = email.getText().toString();
                        String txt_age = age.getText().toString();
                        String txt_bike = bike.getText().toString();
                        String txt_nic= nic.getText().toString();
                        String txt_lison =lison.getText().toString();

                        if (txt_name.isEmpty()) {
                            Toast.makeText(MainActivity4.this, "Please enter rider's name !", Toast.LENGTH_LONG).show();
                        }
                        else if(txt_address.isEmpty()){
                            Toast.makeText(MainActivity4.this, "Please enter address !", Toast.LENGTH_LONG).show();
                        }
                        else if(txt_phone.isEmpty()){
                            Toast.makeText(MainActivity4.this, "Please enter phone !", Toast.LENGTH_LONG).show();
                        }
                        else if(txt_phone.length()<10){
                            Toast.makeText(MainActivity4.this, "enter 10 number for phone !", Toast.LENGTH_LONG).show();
                        }
                        else if(txt_email.isEmpty()){
                            Toast.makeText(MainActivity4.this, "Please enter email !", Toast.LENGTH_LONG).show();
                        }
                        else if(Patterns.EMAIL_ADDRESS.matcher(txt_email).matches()){
                            email.setError("Email is not valid");
                            email.requestFocus();
                            return;
                        }
                        else if(txt_age.length()<2) {
                            Toast.makeText(MainActivity4.this, "Please enter age !", Toast.LENGTH_LONG).show();
                        }
                        else if(txt_bike.isEmpty()){
                            Toast.makeText(MainActivity4.this, "Please enter vehicle numbers !", Toast.LENGTH_LONG).show();
                        }
                        else if(txt_nic.length()<10){
                            Toast.makeText(MainActivity4.this, "Please enter nic !", Toast.LENGTH_LONG).show();
                        }
                        else if(txt_lison.isEmpty()){
                            Toast.makeText(MainActivity4.this, "Please enter lison number !", Toast.LENGTH_LONG).show();
                        }
                        else {
                            int ageR = Integer.parseInt(age.getText().toString().trim());
                            rider.setName(name.getText().toString().trim());
                            rider.setAddress(address.getText().toString().trim());
                            rider.setPhone(phone.getText().toString().trim());
                            rider.setEmail(email.getText().toString().trim());
                            rider.setAge(ageR);
                            rider.setBike(bike.getText().toString().trim());
                            rider.setNic(nic.getText().toString().trim());
                            rider.setLison(lison.getText().toString().trim());
                            DBref.child(String.valueOf(minID+2)).setValue(rider);

                            Toast.makeText(MainActivity4.this, "New employee inserted successfully", Toast.LENGTH_LONG).show();
                        }
                    }
                });


        backto.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                back();
            }
        });



    }
    public void back(){
        Intent intent= new Intent(this,MainActivity2.class);
        startActivity(intent);
    }

}