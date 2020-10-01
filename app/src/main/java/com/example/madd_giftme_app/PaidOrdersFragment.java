package com.example.madd_giftme_app;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.madd_giftme_app.Model.Delivery;
import com.example.madd_giftme_app.Model.Orders;
import com.example.madd_giftme_app.ViewHolder.OrdersViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class PaidOrdersFragment extends Fragment {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    private String paymentStatus, deliveryId ;
    private View orderView ;
    private RecyclerView recyclerView ;
    private DatabaseReference deliveryRef, orderRef ;

    public PaidOrdersFragment() {
        // Required empty public constructor
    }

    public static PaidOrdersFragment newInstance(String param1, String param2) {
        PaidOrdersFragment fragment = new PaidOrdersFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        orderView = inflater.inflate(R.layout.fragment_paid_orders, container, false);

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
            protected void onBindViewHolder(@NonNull final OrdersViewHolder holder, int i, @NonNull final Orders model) {

                paymentStatus = model.getPayment_status();
                if(paymentStatus.equals("Paid")){

                    holder.message.setText(paymentStatus);
                    holder.totalPrice.setText("Total price : " + model.getTotal() + " LKR");
                    holder.dateTime.setText("Ordered at : " + model.getDate() + " " + model.getTime());
                    deliveryId = model.getDid();

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

                }else if(paymentStatus.equals("Pending")){

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