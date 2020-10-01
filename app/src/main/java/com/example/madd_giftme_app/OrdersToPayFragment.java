package com.example.madd_giftme_app;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.madd_giftme_app.Model.Delivery;
import com.example.madd_giftme_app.Model.Orders;
import com.example.madd_giftme_app.Prevalent.Prevalent;
import com.example.madd_giftme_app.ViewHolder.OrdersViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class OrdersToPayFragment extends Fragment {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String paymentStatus, deliveryId, cID ;
    private View orderView, productsList;
    private RecyclerView recyclerView ;
    private DatabaseReference deliveryRef, orderRef ;
    private String mParam1;
    private String mParam2;

    public OrdersToPayFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        orderView = inflater.inflate(R.layout.fragment_orders_to_pay, container, false);
        productsList = (View) orderView.findViewById(R.id.display_orders_list);

        orderRef = FirebaseDatabase.getInstance().getReference().child("Orders");
        deliveryRef = FirebaseDatabase.getInstance().getReference().child("Delivery");

        recyclerView = (RecyclerView)orderView.findViewById(R.id.order_list);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        return orderView;
    }

    @Override
    public void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<Orders> options =
                new FirebaseRecyclerOptions.Builder<Orders>()
                        .setQuery(orderRef, Orders.class)
                        .build();

        FirebaseRecyclerAdapter<Orders, OrdersViewHolder> adapter = new FirebaseRecyclerAdapter<Orders, OrdersViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull final OrdersViewHolder holder, final int i, @NonNull final Orders model) {

                paymentStatus = model.getPayment_status();
                if(paymentStatus.equals("Pending")){

                    holder.message.setText(paymentStatus);
                    holder.totalPrice.setText("Total price : " + model.getTotal() + " LKR");
                    holder.dateTime.setText("Ordered at : " + model.getDate() + " " + model.getTime());
                    deliveryId = model.getDid();
                    cID = model.getEmail();

                    holder.showOrdersProducts.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            CharSequence options [] = new CharSequence[]
                                    {
                                            "Edit",
                                            "Remove"

                                    };
                            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                            builder.setTitle("Cart options : ");

                            builder.setItems(options, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                    if(i == 0){
                                        Intent intent = new Intent(getActivity(), ViewProductDetails.class);
                                        intent.putExtra("pid", model.getEmail());
                                        startActivity(intent);

                                    }
                                    if(i==1){
                                        orderRef.child("User View")
                                                .child(Prevalent.currentOnlineUser.getName())
                                                .child(model.getEmail())
                                                .removeValue()
                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {

                                                        if(task.isSuccessful()){

                                                            Toast.makeText(getActivity(), "Item removed successfully!", Toast.LENGTH_SHORT).show();

                                                            Intent intent = new Intent(getActivity(), display_product_test.class);
                                                            startActivity(intent);
                                                        }
                                                    }
                                                });
                                    }
                                }

                            });builder.show();

                        }
                    });

                    deliveryRef.child(deliveryId).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            if(dataSnapshot.exists()){

                                Delivery delivery = dataSnapshot.getValue(Delivery.class);
                                holder.userName.setText("Name : " + delivery.getName());
                                holder.userAddress.setText("Shipping address : " + delivery.getAddress() + " , " + delivery.getCity());
                                holder.userPhone.setText("Phone : " + delivery.getPhone());

                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                }else if(paymentStatus.equals("Paid")){

                    holder.userName.setVisibility(View.GONE);
                    holder.userPhone.setVisibility(View.GONE);
                    holder.userAddress.setVisibility(View.GONE);
                    holder.totalPrice.setVisibility(View.GONE);
                    holder.dateTime.setVisibility(View.GONE);
                    holder.showOrdersProducts.setVisibility(View.GONE);
                    holder.message.setVisibility(View.GONE);

                }

            }

            @NonNull
            @Override
            public OrdersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.orders_layout, parent, false);
                OrdersViewHolder holder = new OrdersViewHolder(view);
                return holder;
            }
        };
        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }
}