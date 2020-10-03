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

import com.example.madd_giftme_app.Model.Products;
import com.example.madd_giftme_app.Model.Riders;
import com.example.madd_giftme_app.ViewHolder.AdminProductsViewHolder;
import com.example.madd_giftme_app.ViewHolder.RiderVewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class Admin_view_riders extends AppCompatActivity {

    private DatabaseReference ref;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private FloatingActionButton btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_view_riders);

        ref = FirebaseDatabase.getInstance().getReference().child("Riders");
        recyclerView = findViewById(R.id.viewRiderRecycler);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        btn = findViewById(R.id.ADDRDERBUTTON);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(getApplicationContext() , Admin_Add_rider.class);
                startActivity(i);
            }
        });
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
        FirebaseRecyclerOptions<Riders> options  = new FirebaseRecyclerOptions.Builder<Riders>().setQuery(ref , Riders.class).build();

        final FirebaseRecyclerAdapter<Riders , RiderVewHolder> adapter = new FirebaseRecyclerAdapter<Riders, RiderVewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull RiderVewHolder holder, int position, @NonNull final Riders model) {

                holder.ridername.setText("Rider Name : " + model.getName());
                holder.plate.setText("Vehicle plate : " + model.getPlatenumber());
                holder.riderid.setText("Rider ID : "+model.getRiderid());
                Picasso.get().load(model.getImage()).into(holder.image);
                holder.vehicle.setText("Vehicle Type : "+ model.getVehicle());
                holder.card.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        CharSequence options [] = new CharSequence[]
                                {
                                        "Edit",
                                        "Remove"

                                };
                        androidx.appcompat.app.AlertDialog.Builder builder = new AlertDialog.Builder(Admin_view_riders.this);
                        builder.setTitle("Cart options : ");

                        builder.setItems(options, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                if(i == 0){

                                    Intent intent = new Intent(getApplicationContext(),Admin_edit_rider.class);
                                    intent.putExtra("id" , model.getRiderid());
                                    intent.putExtra("name" , model.getName());
                                    intent.putExtra("license" , model.getLicense());
                                    intent.putExtra("plate" , model.getPlatenumber());
                                    intent.putExtra("phone" , model.getPhone());
                                    intent.putExtra("type" , model.getVehicle());
                                    intent.putExtra("image" , model.getImage());
                                    startActivity(intent);




                                }
                                if(i==1){

                                    android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(Admin_view_riders.this);
                                    builder.setMessage("Are you sure you want to Remove Rider : " + model.getRiderid())
                                            .setTitle("Confirm Delete")
                                            .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int id) {
                                                    ref.child(model.getRiderid()).removeValue();
                                                    Toast.makeText(getApplicationContext() , "Removed Successfully",Toast.LENGTH_LONG).show();
                                                    Intent i = new Intent(getApplicationContext(), Admin_view_riders.class);
                                                    startActivity(i);

                                                }
                                            })
                                            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int id) {
                                                    // CANCEL
                                                }
                                            });
                                    // Create the AlertDialog object and return it
                                    android.app.AlertDialog dialog = builder.create();
                                    dialog.show();
                                }
                            }

                        });builder.show();
                    }
                });





            }

            @NonNull
            @Override
            public RiderVewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.riders_layout , parent, false);
                RiderVewHolder holder = new RiderVewHolder(view);
                return holder;

            }
        };

        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }
}