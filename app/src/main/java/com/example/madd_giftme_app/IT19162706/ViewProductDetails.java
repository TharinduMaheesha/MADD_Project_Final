package com.example.madd_giftme_app.IT19162706;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.example.madd_giftme_app.Model.Products;
import com.example.madd_giftme_app.Prevalent.Prevalent;
import com.example.madd_giftme_app.R;
import com.example.madd_giftme_app.ui.cart.CartFragment;
import com.example.madd_giftme_app.ui.home.HomeFragment;
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


public class ViewProductDetails extends Fragment {

    private Button addToCart ;
    ElegantNumberButton numberBtn ;
    ImageView productDetailsImage ;
    TextView productDetailsName, productDetailsPrice, productDetailsDescription, occassion ;
    private String productID = "" ;
    private View view ;
    private String im;


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_view_product_details, container, false);

        productID = getArguments().getString("pid");

        addToCart = (Button) view.findViewById(R.id.pd_add_to_cart_button);
        numberBtn = (ElegantNumberButton) view.findViewById(R.id.add_product_number_btn);
        productDetailsImage = (ImageView)view.findViewById(R.id.product_image_details);
        productDetailsName = (TextView) view.findViewById(R.id.product_name_details);
        productDetailsPrice = (TextView) view.findViewById(R.id.product_price_details);
        productDetailsDescription = (TextView)view. findViewById(R.id.product_description_details);
        occassion = (TextView) view.findViewById(R.id.event_details);


        getProductDetails(productID) ;

        addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                addToCartList() ;

            }
        });


        return view;
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
        cartMap.put("price", getArguments().getString("price"));
        cartMap.put("date", saveCurrentDate);
        cartMap.put("time", saveCurrentTime);
        cartMap.put("quantity", numberBtn.getNumber());
        cartMap.put("discount", "");
        cartMap.put("image", im);


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

                                                Toast.makeText(getContext(), "Added to cart list.", Toast.LENGTH_SHORT).show();

                                                CartFragment frag = new CartFragment();


                                                FragmentManager manager = getFragmentManager();
                                                manager.beginTransaction().replace(R.id.nav_host_fragment,frag).commit();

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
                    productDetailsDescription.setText("Item Description : \n" + "\n"+ products.getProduct_description());
                    occassion.setText("Occasion :   "+products.getProduct_event());
                    im=products.getProduct_image();

                    Picasso.get().load(products.getProduct_image()).into(productDetailsImage);


                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}