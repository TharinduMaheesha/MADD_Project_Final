package com.example.madd_giftme_app;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.madd_giftme_app.Model.AddProducts;
import com.example.madd_giftme_app.ViewHolder.AddProductsViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class ViewOrderProductsFragment extends AppCompatActivity {

    private RecyclerView productList ;
    private String customerID = "" ;
    private RecyclerView.LayoutManager layoutManager;

    public ViewOrderProductsFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //customerID = getIntent().getStringExtra("cid");

        productList = (RecyclerView) findViewById(R.id.view_products_product_list);
        productList.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        productList.setLayoutManager(layoutManager);

    }

    @Override
    public void onStart() {
        super.onStart();

        final DatabaseReference cartListRef = FirebaseDatabase.getInstance().getReference().child("AddProducts").child("Admin View").child("amal").child("Products");

        FirebaseRecyclerOptions<AddProducts> options = new FirebaseRecyclerOptions.Builder<AddProducts>()
                .setQuery(cartListRef, AddProducts.class)
                .build();

        FirebaseRecyclerAdapter<AddProducts, AddProductsViewHolder> adapter = new FirebaseRecyclerAdapter<AddProducts, AddProductsViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull AddProductsViewHolder holder, int i, @NonNull AddProducts model) {

                holder.txtProductQuantity.setText("Quantity = "+model.getQuantity());
                holder.txtProductName.setText(model.getPname());
                holder.txtProductPrice.setText("Price = " + model.getPrice() + " LKR");
                Picasso.get().load(model.getImage()).into(holder.cartImage);

            }

            @NonNull
            @Override
            public AddProductsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_items_layout, parent, false);
                AddProductsViewHolder holder = new AddProductsViewHolder(view);
                return holder ;
            }
        };
        productList.setAdapter(adapter);
        adapter.startListening();

    }
}