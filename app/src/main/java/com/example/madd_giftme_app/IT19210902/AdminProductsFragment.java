package com.example.madd_giftme_app.IT19210902;

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
import androidx.fragment.app.Fragment;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.example.madd_giftme_app.Home;
import com.example.madd_giftme_app.R;
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

public class AdminProductsFragment extends Fragment {

    private Button addToCart ;
    private ElegantNumberButton numberBtn ;
    private ImageView productDetailsImage ;
    private TextView productDetailsName, productDetailsPrice, productDetailsDescription ;
    private String productID = "" ;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;

    public AdminProductsFragment() {
        // Required empty public constructor
    }

    public static AdminProductsFragment newInstance(String param1, String param2) {
        AdminProductsFragment fragment = new AdminProductsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Bundle bundle = getArguments();
        if(bundle!=null){

            productID = bundle.getString("pid");

        }

        View v = inflater.inflate(R.layout.fragment_admin_products, container, false) ;

        addToCart = (Button) v.findViewById(R.id.pd_add_to_cart_button);
        numberBtn = (ElegantNumberButton) v.findViewById(R.id.add_product_number_btn);
        productDetailsImage = (ImageView) v.findViewById(R.id.product_image_details);
        productDetailsName = (TextView) v.findViewById(R.id.product_name_details);
        productDetailsPrice = (TextView) v.findViewById(R.id.product_price_details);
        productDetailsDescription = (TextView) v.findViewById(R.id.product_description_details);

        getProductDetails(productID) ;

        addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                addToCartList() ;

            }
        });

        return v;
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
        cartMap.put("price", productDetailsPrice.getText().toString());
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

                                                Toast.makeText(getContext(), "Added to cart list!", Toast.LENGTH_SHORT).show();

                                                Intent intent = new Intent(getActivity(), Home.class);
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
                    productDetailsPrice.setText(products.getProduct_price());
                    productDetailsDescription.setText(products.getProduct_description());
                    Picasso.get().load(products.getProduct_image()).into(productDetailsImage);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }



}