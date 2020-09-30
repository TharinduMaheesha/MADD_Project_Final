/*N.I.T.Hewage_IT19220116
 * MADD mini project 2020_Y2S2_GIFT_ODERIN_GAPP
 * Delivery managment
 * */
package com.example.giftme;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


/*
* class for the rider controlling page
* */
public class RiderControl extends AppCompatActivity {

    Button button1,button2,button4,button3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rider_control);
        getSupportActionBar().setTitle("GiftME Delivery Administrator");

        button1=(Button)findViewById(R.id.btnAdd);
        button2=(Button)findViewById(R.id.btnView);
        button4=(Button)findViewById(R.id.btnupdel);
        button3=(Button)findViewById(R.id.btn3);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                openAddRider();
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewRiders();
            }
        });
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                goback();
            }
        });



    }

    public void openAddRider(){
        Intent intent4= new Intent(this,InsertNewRider.class);
        startActivity(intent4);
        Toast.makeText(RiderControl.this, "Enter new employee details here",Toast.LENGTH_LONG).show();
    }
    public void viewRiders(){
        Intent intent2=new Intent(this,ViewAllRiders.class);
        startActivity(intent2);
        Toast.makeText(RiderControl.this,"All riders are here",Toast.LENGTH_LONG).show();
    }

    public void goback(){
        Intent intent= new Intent(this,MainActivity.class);
        startActivity(intent);
        Toast.makeText(RiderControl.this, "<< One page back", Toast.LENGTH_LONG).show();
    }





}

