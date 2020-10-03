package com.example.madd_giftme_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.madd_giftme_app.Model.Delivery;
import com.example.madd_giftme_app.ViewHolder.OngoingDeliveryViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Admin_delivered_activity extends AppCompatActivity {

    private DatabaseReference ref;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_delivered_activity);

        ref = FirebaseDatabase.getInstance().getReference().child("Delivery");
        recyclerView = findViewById(R.id.ongoingRecclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

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
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseRecyclerOptions<Delivery> options  = new FirebaseRecyclerOptions.Builder<Delivery>().setQuery(ref , Delivery.class).build();

        final FirebaseRecyclerAdapter<Delivery , OngoingDeliveryViewHolder> adapter = new FirebaseRecyclerAdapter<Delivery, OngoingDeliveryViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull OngoingDeliveryViewHolder holder, int position, @NonNull final Delivery model) {

                if(model.getDelivery_status().equalsIgnoreCase("delivered")) {

                    holder.status.setText("Delivery Status : " + model.getDelivery_status());
                    holder.uname.setText("User : " + model.getName());
                    holder.rname.setText("Rider : " + model.getRider());
                    holder.did.setText("Delivery ID : " + model.getDeliveryid());
                    holder.oid.setText("Order ID : " + model.getOrderid());



                    holder.card.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {


                        }
                    });
                }
                else{
                    holder.card.setVisibility(View.GONE);
                }

            }

            @NonNull
            @Override
            public OngoingDeliveryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.delivered_orders_layout , parent, false);
                OngoingDeliveryViewHolder holder = new OngoingDeliveryViewHolder(view);
                return holder;

            }
        };

        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }
}