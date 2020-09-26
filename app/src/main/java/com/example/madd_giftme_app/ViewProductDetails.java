package com.example.madd_giftme_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.example.madd_giftme_app.Model.Products;
import com.example.madd_giftme_app.Prevalent.Prevalent;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;


public class ViewProductDetails extends AppCompatActivity {

    private Button addToCart ;
    ElegantNumberButton numberBtn ;
    ImageView productDetailsImage ;
    TextView productDetailsName, productDetailsPrice, productDetailsDescription, occassion ;
    private String productID = "" ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_product_details);

        productID = getIntent().getStringExtra("pid");

        addToCart = (Button) findViewById(R.id.pd_add_to_cart_button);
        numberBtn = (ElegantNumberButton) findViewById(R.id.add_product_number_btn);
        productDetailsImage = (ImageView) findViewById(R.id.product_image_details);
        productDetailsName = (TextView) findViewById(R.id.product_name_details);
        productDetailsPrice = (TextView) findViewById(R.id.product_price_details);
        productDetailsDescription = (TextView) findViewById(R.id.product_description_details);
        occassion = (TextView) findViewById(R.id.event_details);


        getProductDetails(productID) ;

        addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                addToCartList() ;

            }
        });



    }

    private void addToCartList() {

        String saveCurrentDate, saveCurrentTime ;

        Calendar calForDate = Calendar.getInstance();

        SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd, yyyy");
        saveCurrentDate = currentDate.format(calForDate.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentDate.format(calForDate.getTime());

        final DatabaseReference cartList = FirebaseDatabase.getInstance().getReference().child("AddProducts");

        final HashMap<String , Object> cartMap = new HashMap<>();
        cartMap.put("pid", productID);
        cartMap.put("pname", productDetailsName.getText().toString());
        cartMap.put("price", getIntent().getStringExtra("price"));
        cartMap.put("date", saveCurrentDate);
        cartMap.put("time", saveCurrentTime);
        cartMap.put("quantity", numberBtn.getNumber());
        cartMap.put("discount", "");

        cartList.child("User View").child(Prevalent.currentOnlineUser.getEmail())
                .child("Products").child(productID)
                .updateChildren(cartMap)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if(task.isSuccessful()){

                            cartList.child("Admin View").child(Prevalent.currentOnlineUser.getEmail())
                                    .child("Products").child(productID)
                                    .updateChildren(cartMap)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {

                                            if(task.isSuccessful()){

                                                Toast.makeText(ViewProductDetails.this, "Added to cart list.", Toast.LENGTH_SHORT).show();

                                                Intent intent = new Intent(ViewProductDetails.this, display_product_test.class);
                                                startActivity(intent);

                                            }
                                        }
                                    });

                        }
                    }
                });

    }

    private void getProductDetails(String productID) {

        DatabaseReference productRef = FirebaseDatabase.getInstance().getReference().child("Products");
        productRef.child(productID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(dataSnapshot.exists()){

                    Products products = dataSnapshot.getValue(Products.class);

                    productDetailsName.setText(products.getProduct_name());
                    productDetailsPrice.setText("Price :   LKR " + products.getProduct_price());
                    productDetailsDescription.setText("Item Description : " + products.getProduct_description());
                    occassion.setText("Occasion :   "+products.getProduct_event());

                    Picasso.get().load(products.getProduct_image()).into(productDetailsImage);


                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}