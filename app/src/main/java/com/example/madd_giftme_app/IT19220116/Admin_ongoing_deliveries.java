package com.example.madd_giftme_app.IT19220116;

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

import com.example.madd_giftme_app.Admin_account;
import com.example.madd_giftme_app.Admin_home;
import com.example.madd_giftme_app.IT19210902.Admin_order_home;
import com.example.madd_giftme_app.IT19162706.Admin_products;
import com.example.madd_giftme_app.Model.Delivery;
import com.example.madd_giftme_app.Prevalent.Prevalent;
import com.example.madd_giftme_app.R;
import com.example.madd_giftme_app.ViewHolder.OngoingDeliveryViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

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
                Intent i = new Intent(getApplicationContext(), Admin_products.class);
                startActivity(i);
            }
        });
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), Admin_home.class);
                startActivity(i);
            }
        });
        orders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), Admin_order_home.class);
                startActivity(i);
            }
        });
        account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), Admin_account.class);
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
                                        Toast.makeText(getApplicationContext(),"Added to shipping ",Toast.LENGTH_LONG).show();
                                        Intent inte = new Intent(getApplicationContext(),Admin_ongoing_deliveries.class);
                                        startActivity(inte);

                                    }
                                    if (i == 1) {

                                        DatabaseReference temp = FirebaseDatabase.getInstance().getReference().child("Orders").child(model.getOrderid());
                                        temp.addValueEventListener(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                String amount = snapshot.child("total").getValue().toString();
                                                addToPay(model.getOrderid() , model.getEmail() , amount);
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError error) {

                                            }
                                        });
                                        dataref.child("delivery_status").setValue("delivered");
                                        dataref.child("payment_status").setValue("paid");
                                        temp.child("payment_status").setValue("paid");
                                        Toast.makeText(getApplicationContext(),"Confirmed order delivered",Toast.LENGTH_LONG).show();
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

    private void addToPay(String orderid , String email , String amount) {


        final String saveCurrentDate, saveCurrentTime;

        Calendar calForDate = Calendar.getInstance();

        SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd, yyyy");
        saveCurrentDate = currentDate.format(calForDate.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentTime.format(calForDate.getTime());

        final String payid = saveCurrentDate + saveCurrentTime + "ORDER";


        final DatabaseReference orderRef = FirebaseDatabase.getInstance().getReference()
                .child("Payments").child(orderid);

        HashMap<String, Object> orderMap = new HashMap<>();

        orderMap.put("orderid", orderid);
        orderMap.put("amount", amount);
        orderMap.put("email", email);
        orderMap.put("date", saveCurrentDate);
        orderMap.put("payid", payid);


        DatabaseReference test = FirebaseDatabase.getInstance().getReference().child("OrderItems");

        orderRef.updateChildren(orderMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(getApplicationContext() , "Payment received " , Toast.LENGTH_LONG).show();
            }
        });
    }
}