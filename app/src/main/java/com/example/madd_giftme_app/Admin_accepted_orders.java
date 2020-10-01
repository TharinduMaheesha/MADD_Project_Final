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
import android.widget.ImageButton;

import com.example.madd_giftme_app.Model.Orders;
import com.example.madd_giftme_app.ViewHolder.AdminOrderViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Admin_accepted_orders extends AppCompatActivity {

    private DatabaseReference ref ;
    private RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    ImageButton home , account , products , orders;

    int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_accepted_orders);


        home = findViewById(R.id.btnHome);
        account = findViewById(R.id.btnAccount);
        products = findViewById(R.id.btnProducts);
        orders = findViewById(R.id.btnOrders);

        ref = FirebaseDatabase.getInstance().getReference().child("Orders");
        recyclerView = findViewById(R.id.ACCEPTED_ORDER_ADMIN_RECYCLER);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

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
        FirebaseRecyclerOptions<Orders> options  = new FirebaseRecyclerOptions.Builder<Orders>().setQuery(ref , Orders.class).build();

        final FirebaseRecyclerAdapter<Orders , AdminOrderViewHolder> adapter = new FirebaseRecyclerAdapter<Orders, AdminOrderViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull final AdminOrderViewHolder holder, int position, @NonNull final Orders model) {

                if(!model.getOrder_status().equalsIgnoreCase("Accepted")){
                    holder.card.setVisibility(View.GONE);
                }
                else {



                    holder.user.setText("User : " + model.getEmail());
                    holder.id.setText("Order ID : " + model.getOrderid());
                    holder.date.setText("Order Added on : " + model.getDate());
                    holder.count.setVisibility(View.GONE);

                    holder.card.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent i = new Intent(Admin_accepted_orders.this,Admin_view_order.class);
                            i.putExtra("email" , model.getEmail());
                            i.putExtra("date" , model.getDate());
                            i.putExtra("total" , model.getTotal());
                            i.putExtra("payment" , model.getPayment_status());
                            i.putExtra("id" , model.getOrderid());
                            i.putExtra("token" , "1");




                            startActivity(i);
                        }
                    });


                }




            }

            @NonNull
            @Override
            public AdminOrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.admin_view_orders_layout , parent, false);
                AdminOrderViewHolder holder = new AdminOrderViewHolder(view);
                return holder;

            }
        };

        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }
}