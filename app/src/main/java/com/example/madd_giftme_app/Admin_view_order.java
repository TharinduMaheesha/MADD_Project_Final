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

import com.example.madd_giftme_app.Model.OrderItems;
import com.example.madd_giftme_app.Model.Products;
import com.example.madd_giftme_app.ViewHolder.AdminProductsViewHolder;
import com.example.madd_giftme_app.ViewHolder.OrderItemsViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class Admin_view_order extends AppCompatActivity {

    private DatabaseReference ref ;
    private RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    String name;
    Products prod;
    TextView order_name , id , user,date , total ,payment;
    Button accept , reject;
    ImageButton home , account , products , orders;
    String token;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_view_order);
        accept = findViewById(R.id.BTN_VIEW_ORDER_ACCEPT);
        reject = findViewById(R.id.BTN_VIEW_ORDER__REJECT);




        if(getIntent().getStringExtra("token").equalsIgnoreCase("1")){
            accept.setVisibility(View.GONE);
            reject.setText("Add To Delivery");
        }
        else if(getIntent().getStringExtra("token").equalsIgnoreCase("2")){
            accept.setVisibility(View.GONE);
            reject.setVisibility(View.GONE);
        }



        home = findViewById(R.id.btnHome);
        account = findViewById(R.id.btnAccount);
        products = findViewById(R.id.btnProducts);
        orders = findViewById(R.id.btnOrders);

        ref = FirebaseDatabase.getInstance().getReference().child("OrderItems");
        recyclerView = findViewById(R.id.itemsRecycler);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        id = findViewById(R.id.TV_VIEW_ORDER_ID);
        date = findViewById(R.id.TV_VIEW_ORDER_DATE);
        user = findViewById(R.id.TV_VIEW_ORDER_USER);
        total = findViewById(R.id.TV_VIEW_ORDER_TOTAL);
        payment = findViewById(R.id.TV_VIEW_ORDER_PAYMENT);

        id.setText("\tOrder ID : "+getIntent().getStringExtra("id"));
        date.setText("\tDate : "+getIntent().getStringExtra("date"));
        user.setText("\tUser : "+getIntent().getStringExtra("email"));
        total.setText("\tTotal : "+getIntent().getStringExtra("total"));
        payment.setText("\tPayment Status : "+getIntent().getStringExtra("payment"));


        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference newref = FirebaseDatabase.getInstance().getReference().child("Orders");
                newref.child(getIntent().getStringExtra("id")).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        snapshot.getRef().child("order_status").setValue("accepted");
                        Toast.makeText(getApplicationContext(),"Order confirmed" , Toast.LENGTH_LONG).show();


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

                Intent i = new Intent(Admin_view_order.this,Admin_order_home.class);
                startActivity(i);
            }
        });

        reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference newref = FirebaseDatabase.getInstance().getReference().child("Orders");
                newref.child(getIntent().getStringExtra("id")).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        snapshot.getRef().child("order_status").setValue("declined");
                        Toast.makeText(getApplicationContext(),"Order confirmed" , Toast.LENGTH_LONG).show();


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

                Intent i = new Intent(Admin_view_order.this,Admin_order_home.class);
                startActivity(i);
            }
        });



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
                startActivity(i);            }
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

        FirebaseRecyclerOptions<OrderItems> options  = new FirebaseRecyclerOptions.Builder<OrderItems>().setQuery(ref , OrderItems.class).build();

        final FirebaseRecyclerAdapter<OrderItems , OrderItemsViewHolder> adapter = new FirebaseRecyclerAdapter<OrderItems, OrderItemsViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull final OrderItemsViewHolder holder, int position, @NonNull final OrderItems model) {

                DatabaseReference dat = FirebaseDatabase.getInstance().getReference().child("Products").child(model.getPid());
                dat.orderByChild("product_name").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                       prod = snapshot.getValue(Products.class);
                       name = prod.getProduct_name();
                        holder.text1.setText("Product : "+ model.getPid()+" - "+name);


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


                holder.text2.setText("Quantity : "+model.getQuantity());





            }

            @NonNull
            @Override
            public OrderItemsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.orderitems , parent, false);
                OrderItemsViewHolder holder = new OrderItemsViewHolder(view);
                return holder;

            }
        };

        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }
}