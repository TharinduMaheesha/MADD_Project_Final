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
import android.widget.Toast;

import com.example.madd_giftme_app.Model.Payments;
import com.example.madd_giftme_app.Model.Products;
import com.example.madd_giftme_app.ViewHolder.AdminProductsViewHolder;
import com.example.madd_giftme_app.ViewHolder.PaymentsViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class View_payments extends AppCompatActivity {

    private DatabaseReference ref ;
    private RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    Button addnew;
    ImageButton home , account , products , orders;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_payments);


        home = findViewById(R.id.btnHome);
        account = findViewById(R.id.btnAccount);
        products = findViewById(R.id.btnProducts);
        orders = findViewById(R.id.btnOrders);

        ref = FirebaseDatabase.getInstance().getReference().child("Payments");
        recyclerView = findViewById(R.id.RVPAY);
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
    }


    @Override
    protected void onStart() {
        super.onStart();
        FirebaseRecyclerOptions<Payments> options  = new FirebaseRecyclerOptions.Builder<Payments>().setQuery(ref , Payments.class).build();

        final FirebaseRecyclerAdapter<Payments , PaymentsViewHolder> adapter = new FirebaseRecyclerAdapter<Payments, PaymentsViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull PaymentsViewHolder holder, int position, @NonNull final Payments model) {

                holder.payid.setText("Payment ID : "+model.getPayid());
                holder.total.setText("Total : "+model.getAmount());
                holder.mail.setText("Email : "+model.getEmail());
                holder.oid.setText("Order ID : "+model.getOrderid());
                holder.date.setText("Date of payment : "+model.getDate());

                holder.btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent i = new Intent(View_payments.this,Admin_view_order.class);
                        i.putExtra("email" , model.getEmail());
                        i.putExtra("date" , model.getDate());
                        i.putExtra("total" , model.getAmount());
                        i.putExtra("payment" , "Paid");
                        i.putExtra("id" , model.getOrderid());
                        i.putExtra("token" , "2");




                        startActivity(i);
                    }
                });




            }

            @NonNull
            @Override
            public PaymentsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.payments_layout , parent, false);
                PaymentsViewHolder holder = new PaymentsViewHolder(view);
                return holder;

            }
        };

        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }
}