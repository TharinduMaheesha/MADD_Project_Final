package com.example.madd_giftme_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import com.example.madd_giftme_app.Model.AddProducts;
import com.example.madd_giftme_app.ViewHolder.OrderProductsViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class ViewOrderProducts extends AppCompatActivity {

    private RecyclerView productList ;
    private String customerID = "" ;
    private RecyclerView.LayoutManager layoutManager;
    private Button home ;

    public ViewOrderProducts() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_order_products);

        customerID = getIntent().getStringExtra("cid");

        productList = (RecyclerView) findViewById(R.id.view_products_product_list);
        productList.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        productList.setLayoutManager(layoutManager);

        home = (Button) findViewById(R.id.order_products_button_go_to_home);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(ViewOrderProducts.this, Home.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onStart() {
        super.onStart();

        final DatabaseReference cartListRef = FirebaseDatabase.getInstance().getReference().child("AddProducts")
                .child("Admin View").child(customerID).child("Products");

        FirebaseRecyclerOptions<AddProducts> options = new FirebaseRecyclerOptions.Builder<AddProducts>()
                .setQuery(cartListRef, AddProducts.class)
                .build();

        FirebaseRecyclerAdapter<AddProducts, OrderProductsViewHolder> adapter = new FirebaseRecyclerAdapter<AddProducts, OrderProductsViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull OrderProductsViewHolder holder, int i, @NonNull final AddProducts model) {

                holder.txtOrderProductQuantity.setText("Quantity = " + model.getQuantity());
                holder.txtOrderProductName.setText(model.getPname());
                holder.txtOrderProductPrice.setText("Price = " + model.getPrice() + " LKR");
                Picasso.get().load(model.getImage()).into(holder.OrderProductImage);

            }

            @NonNull
            @Override
            public OrderProductsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.orderitems, parent, false);
                OrderProductsViewHolder holder = new OrderProductsViewHolder(view);
                return holder;
            }
        };
        productList.setAdapter(adapter);
        adapter.startListening();

    }
}