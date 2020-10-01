package com.example.madd_giftme_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import io.paperdb.Paper;

public class Admin_account extends AppCompatActivity {

    TextView name , email , phone;
    Button password , logout , edit;
    private DatabaseReference ref ;
    private String pwd , id , number , uname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_account);

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

        ref = FirebaseDatabase.getInstance().getReference().child("Admins").child("admin");


        name = findViewById(R.id.TVACCOUNT2);
        email = findViewById(R.id.TVACCOUNT3);
        phone = findViewById(R.id.TVACCOUNT4);

        password = findViewById(R.id.BTN_ADMIN_RSESTPASSWORD);
        logout = findViewById(R.id.BTN_ACCOUNT_ADMIN_LOGOUT );
        edit = findViewById(R.id.BN_ADMIN_EDIT_ACCOUNT);

        password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i =  new Intent(getApplicationContext(),Admin_reset_password.class);
                i.putExtra("password" , pwd);
                i.putExtra("email" , id);

                startActivity(i);

            }
        });

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i =  new Intent(getApplicationContext(),Admin_edit_account.class);
                i.putExtra("password" , pwd);
                i.putExtra("email" , id);
                i.putExtra("name" , uname);
                i.putExtra("phone" , number);
                startActivity(i);

            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i =  new Intent(getApplicationContext(),StartingActivity.class);
                startActivity(i);
                Paper.book().destroy();

            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(dataSnapshot.hasChildren()){
                    name.setText("Name : "+ dataSnapshot.child("name").getValue().toString());
                    phone.setText("Phone Number : "+dataSnapshot.child("phone").getValue().toString());
                    email.setText("Email : "+dataSnapshot.child("email").getValue().toString());
                    pwd = dataSnapshot.child("password").getValue().toString();
                    id = dataSnapshot.child("email").getValue().toString();
                    number = dataSnapshot.child("phone").getValue().toString();
                    uname = dataSnapshot.child("name").getValue().toString();

                }
                else{
                    Toast.makeText(getApplicationContext(),"No data to be shown",Toast.LENGTH_LONG).show();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }
}