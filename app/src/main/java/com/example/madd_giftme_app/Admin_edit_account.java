package com.example.madd_giftme_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Admin_edit_account extends AppCompatActivity {


    private EditText email , name , phone;
    private Button save ,cancel;
    private DatabaseReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_edit_account);

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

        ref = FirebaseDatabase.getInstance().getReference().child("Admins");

        name = findViewById(R.id.ed_admin_edit_name);
        phone = findViewById(R.id.ed_admin_edit_phone);
        email = findViewById(R.id.ed_admin_edit_email);

        save = findViewById(R.id.BTN_SAVE_ADMIN_account);
        cancel = findViewById(R.id.BTN_ADMIN_CANCEL_edit);

        name.setText(getIntent().getStringExtra("name"));
        email.setText(getIntent().getStringExtra("email"));
        phone.setText(getIntent().getStringExtra("phone"));


        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editDetails();
            }
        });
    }

    private void editDetails() {


        if(TextUtils.isEmpty(name.getText())){
            Snackbar.make(findViewById(android.R.id.content), "Please enter your name", Snackbar.LENGTH_LONG).show();
        }
        else if(TextUtils.isEmpty(phone.getText())){
            Snackbar.make(findViewById(android.R.id.content), "Please enter your Phone number", Snackbar.LENGTH_LONG).show();
        }
        else if(TextUtils.isEmpty(email.getText())){
            Snackbar.make(findViewById(android.R.id.content), "Please repeat your Email", Snackbar.LENGTH_LONG).show();
        }
        else{

            ref.child(getIntent().getStringExtra("email")).child("name").setValue(name.getText().toString().trim());
            ref.child(getIntent().getStringExtra("email")).child("email").setValue(email.getText().toString().trim());
            ref.child(getIntent().getStringExtra("email")).child("phone").setValue(phone.getText().toString().trim());
            Intent i = new Intent(getApplicationContext(),Admin_account.class);
            startActivity(i);
            Toast.makeText(getApplicationContext(),"Account details changed successfully " , Toast.LENGTH_LONG).show();
        }
    }
}