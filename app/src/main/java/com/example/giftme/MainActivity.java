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
* class for the main page
* */
public class MainActivity extends AppCompatActivity {

    Button deliveryPage, RiderPage,Logout,guide;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        deliveryPage=findViewById(R.id.btnDelivery);
        RiderPage=findViewById(R.id.btnRider);
        Logout=findViewById(R.id.btnLogout);
        guide=findViewById(R.id.btnGuide);

        deliveryPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDelivery();
            }
        });
        RiderPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openRider();
            }
        });
guide.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        guide();
    }
});

    }


//open delivery controlling activity
    public void openDelivery(){
        Intent intent= new Intent(this,InsertDelivery.class);
        startActivity(intent);
    }

 //open rider controlling activity
    public void openRider(){
        Intent intent= new Intent(this,RiderControl.class);
        startActivity(intent);
    }

    //logout
    public void logout(){

    }
//user guide
    public void guide(){
        Intent intent= new Intent(this, DeliveryControlGuide.class);
        startActivity(intent);
        Toast.makeText(MainActivity.this, "Use this guide for the first time", Toast.LENGTH_LONG).show();
    }
}