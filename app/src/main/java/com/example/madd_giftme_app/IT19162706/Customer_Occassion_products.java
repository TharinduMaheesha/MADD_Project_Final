package com.example.madd_giftme_app.IT19162706;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.madd_giftme_app.Model.Products;
import com.example.madd_giftme_app.R;
import com.example.madd_giftme_app.ViewHolder.OccasionProcductViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class Customer_Occassion_products extends Fragment {

    private TextView occasion;
    private String event;
    private DatabaseReference ref ;
    private RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    private View view ;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       view= inflater.inflate(R.layout.activity_customer__occassion_products, container, false);

        ref = FirebaseDatabase.getInstance().getReference().child("Products");
        recyclerView = view.findViewById(R.id.ocasionProductsRecycler);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);


        Bundle bundle = this.getArguments();
        if (bundle != null) {
             event = bundle.getString("event");
        }
        event="Promotions";

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseRecyclerOptions<Products> options  = new FirebaseRecyclerOptions.Builder<Products>().setQuery(ref , Products.class).build();

        final FirebaseRecyclerAdapter<Products , OccasionProcductViewHolder> adapter = new FirebaseRecyclerAdapter<Products, OccasionProcductViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull OccasionProcductViewHolder holder, int position, @NonNull final Products model) {

                if(!model.getProduct_event().equalsIgnoreCase("Promotions") || model.getProduct_availability().equalsIgnoreCase("Not Available")) {

                    holder.name.setVisibility(View.GONE);
                    holder.price.setVisibility(View.GONE);
                    holder.description.setVisibility(View.GONE);
                    holder.image.setVisibility(View.GONE);
                    holder.card.setVisibility(View.GONE);

                }
                else{
                    holder.name.setText(model.getProduct_name());
                    holder.price.setText(model.getProduct_price() + " LKR");
                    holder.description.setText(model.getProduct_description());
                    Picasso.get().load(model.getProduct_image()).into(holder.image);
//                    holder.image.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
//                            Intent i = new Intent(Customer_Occassion_products.this , ViewProductDetails.class);
//                            i.putExtra("pid" , model.getProduct_id());
//                            i.putExtra("price", model.getProduct_price());
//
//                            startActivity(i);
//
//                        }
//                    });

                }

            }

            @NonNull
            @Override
            public OccasionProcductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.customer_occasion_products_layout , parent, false);
                OccasionProcductViewHolder holder = new OccasionProcductViewHolder(view);
                return holder;

            }
        };

        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }
}