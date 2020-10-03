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
import android.widget.Toast;

import com.example.madd_giftme_app.Model.Delivery;
import com.example.madd_giftme_app.Model.Products;
import com.example.madd_giftme_app.ViewHolder.AdminProductsViewHolder;
import com.example.madd_giftme_app.ViewHolder.OngoingDeliveryViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class Admin_ongoing_deliveries extends AppCompatActivity {

    private DatabaseReference ref;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_ongoing_deliveries);

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

                if(!model.getDelivery_status().equalsIgnoreCase("delivered")) {

                    holder.status.setText("Delivery Status : " + model.getDelivery_status());
                    holder.uname.setText("User : " + model.getName());
                    holder.rname.setText("Rider : " + model.getRider());
                    holder.did.setText("Delivery ID : " + model.getDeliveryid());
                    holder.oid.setText("Order ID : " + model.getOrderid());
                    if (!model.getDelivery_status().equalsIgnoreCase("shipped")) {
                        holder.shipped.setVisibility(View.GONE);
                    }


                    holder.card.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            CharSequence test[] = new CharSequence[]
                                    {
                                            "Shipped",
                                            "Delivered"

                                    };
                            AlertDialog.Builder builder = new AlertDialog.Builder(Admin_ongoing_deliveries.this);
                            builder.setTitle("Delivery options : ");

                            builder.setItems(test, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                    DatabaseReference dataref = FirebaseDatabase.getInstance().getReference().child("Delivery").child(model.getDeliveryid());

                                    if (i == 0) {


                                        dataref.child("delivery_status").setValue("shipped");
                                        Intent inte = new Intent(getApplicationContext(),Admin_ongoing_deliveries.class);
                                        startActivity(inte);

                                    }
                                    if (i == 1) {
                                        dataref.child("delivery_status").setValue("delivered");
                                        Intent inte = new Intent(getApplicationContext(),Admin_ongoing_deliveries.class);
                                        startActivity(inte);
                                    }
                                }

                            });
                            builder.show();
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

                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ongoingdeliveries_layout , parent, false);
                OngoingDeliveryViewHolder holder = new OngoingDeliveryViewHolder(view);
                return holder;

            }
        };

        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }
}