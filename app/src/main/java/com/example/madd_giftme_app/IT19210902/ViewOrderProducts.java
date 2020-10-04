package com.example.madd_giftme_app.IT19210902;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.madd_giftme_app.Home;
import com.example.madd_giftme_app.Model.OrderItems;
import com.example.madd_giftme_app.Model.Products;
import com.example.madd_giftme_app.R;
import com.example.madd_giftme_app.Model.AddProducts;
import com.example.madd_giftme_app.ViewHolder.OrderProductsViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
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
        setContentView(R.layout.activity_view__order_products);

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

        final DatabaseReference cartListRef = FirebaseDatabase.getInstance().getReference().child("OrderItems");

        FirebaseRecyclerOptions<OrderItems> options = new FirebaseRecyclerOptions.Builder<OrderItems>()
                .setQuery(cartListRef, OrderItems.class)
                .build();

        FirebaseRecyclerAdapter<OrderItems, OrderProductsViewHolder> adapter = new FirebaseRecyclerAdapter<OrderItems, OrderProductsViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull final OrderProductsViewHolder holder, int i, @NonNull final OrderItems model) {

                if(model.getOrderid().equalsIgnoreCase(getIntent().getStringExtra("oid"))) {

                    DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Products").child(model.getPid());

                    ref.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            holder.txtOrderProductName.setText(snapshot.child("product_name").getValue().toString());
                            holder.txtOrderProductPrice.setText("Price = " + snapshot.child("product_price").getValue().toString());
                            Picasso.get().load(snapshot.child("product_image").getValue().toString()).into(holder.OrderProductImage);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                    holder.txtOrderProductQuantity.setText("Quantity = " + model.getQuantity());

                }
                else{
                    holder.OrdersProductsProduct.setVisibility(View.GONE);
                }

            }

            @NonNull
            @Override
            public OrderProductsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_items_layout, parent, false);
                OrderProductsViewHolder holder = new OrderProductsViewHolder(view);
                return holder;
            }
        };
        productList.setAdapter(adapter);
        adapter.startListening();

    }
}