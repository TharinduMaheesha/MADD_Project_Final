package com.example.madd_giftme_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.RectF;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.madd_giftme_app.Model.Products;
import com.example.madd_giftme_app.Model.Refunds;
import com.example.madd_giftme_app.ViewHolder.AdminProductsViewHolder;
import com.example.madd_giftme_app.ViewHolder.RefundViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class Payment_refund_requests extends AppCompatActivity {

    private DatabaseReference ref ;
    private RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    ImageButton home , account , products , orders;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_payment_refund_requests);
        home = findViewById(R.id.btnHome);
        account = findViewById(R.id.btnAccount);
        products = findViewById(R.id.btnProducts);
        orders = findViewById(R.id.btnOrders);

        ref = FirebaseDatabase.getInstance().getReference().child("Refunds");
        recyclerView = findViewById(R.id.refundrecycler);
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
        FirebaseRecyclerOptions<Refunds> options  = new FirebaseRecyclerOptions.Builder<Refunds>().setQuery(ref , Refunds.class).build();

        final FirebaseRecyclerAdapter<Refunds , RefundViewHolder> adapter = new FirebaseRecyclerAdapter<Refunds, RefundViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull RefundViewHolder holder, int position, @NonNull final Refunds model) {


                if(model.getStatus().equalsIgnoreCase("pending")) {
                    holder.oid.setText("Request : "+model.getRequestid());
                    holder.mail.setText("User : "+model.getEmail());
                    holder.card.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent i = new Intent(getApplicationContext(),view_refund.class);
                            i.putExtra("rid" , model.getRequestid());
                            i.putExtra("oid" , model.getOrderid());
                            i.putExtra("user" , model.getEmail());
                            i.putExtra("reason" , model.getReason());
                            i.putExtra("status" , model.getStatus());
                            startActivity(i);


                        }
                    });
                }
                else{
                    holder.card.setVisibility(View.GONE);
                }





            }

            @NonNull
            @Override
            public RefundViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.refunds_laout , parent, false);
                RefundViewHolder holder = new RefundViewHolder(view);
                return holder;

            }
        };

        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }
}