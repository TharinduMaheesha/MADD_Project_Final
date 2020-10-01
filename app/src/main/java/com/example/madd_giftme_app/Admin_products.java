package com.example.madd_giftme_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.madd_giftme_app.Model.Products;
import com.example.madd_giftme_app.ViewHolder.AdminProductsViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class Admin_products extends AppCompatActivity {

    private DatabaseReference ref ;
    private RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    Button addnew;
    ImageButton home , account , products , orders;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_products);

        home = findViewById(R.id.btnHome);
        account = findViewById(R.id.btnAccount);
        products = findViewById(R.id.btnProducts);
        orders = findViewById(R.id.btnOrders);

        ref = FirebaseDatabase.getInstance().getReference().child("Products");
        recyclerView = findViewById(R.id.adminProductRecycler);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        addnew = findViewById(R.id.btn_add_new_admin);

        addnew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Admin_products.this,Admin_add_new_product.class);
                startActivity(i);
            }
        });
        products.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"You are currently in the Products Page" , Toast.LENGTH_LONG).show();
            }
        });
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Admin_products.this,Admin_home.class);
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
        FirebaseRecyclerOptions<Products> options  = new FirebaseRecyclerOptions.Builder<Products>().setQuery(ref , Products.class).build();

        final FirebaseRecyclerAdapter<Products , AdminProductsViewHolder> adapter = new FirebaseRecyclerAdapter<Products, AdminProductsViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull AdminProductsViewHolder holder, int position, @NonNull final Products model) {


                    holder.prod_name.setText(model.getProduct_id() +" - "+model.getProduct_name());
                    holder.event.setText("Occasion : " + model.getProduct_event());
                    holder.availability.setText(model.getProduct_availability());
                    Picasso.get().load(model.getProduct_image()).into(holder.image);
                    holder.edit.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent i = new Intent(Admin_products.this , Admin_edit_product.class);
                            i.putExtra("pid" , model.getProduct_id());
                            i.putExtra("pName" , model.getProduct_name());
                            i.putExtra("pPrice" , model.getProduct_price());
                            i.putExtra("pdesc" , model.getProduct_description());
                            i.putExtra("image" , model.getProduct_image());
                            i.putExtra("availability" , model.getProduct_availability());




                            startActivity(i);
                        }
                    });

                    holder.remove.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(Admin_products.this);
                            builder.setMessage("Are you sure you want to Remove Product ID : " + model.getProduct_id())
                                    .setTitle("Confirm Delete")
                                    .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            ref.child(model.getProduct_id()).removeValue();
                                            Toast.makeText(getApplicationContext() , "Deleted Succesfully",Toast.LENGTH_LONG).show();
                                            Intent i = new Intent(Admin_products.this, Admin_home.class);
                                            startActivity(i);

                                        }
                                    })
                                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            // CANCEL
                                        }
                                    });
                            // Create the AlertDialog object and return it
                            AlertDialog dialog = builder.create();
                            dialog.show();
                        }

                    });

                holder.edit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent i = new Intent(Admin_products.this , Admin_edit_product.class);
                        i.putExtra("pid" , model.getProduct_id());
                        i.putExtra("pName" , model.getProduct_name());
                        i.putExtra("pPrice" , model.getProduct_price());
                        i.putExtra("pdesc" , model.getProduct_description());
                        i.putExtra("image" , model.getProduct_image());
                        i.putExtra("occasion" , model.getProduct_event());
                        i.putExtra("availability" , model.getProduct_availability());




                        startActivity(i);
                    }
                });



            }

            @NonNull
            @Override
            public AdminProductsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.admin_products_layout , parent, false);
                AdminProductsViewHolder holder = new AdminProductsViewHolder(view);
                return holder;

            }
        };

        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }
}