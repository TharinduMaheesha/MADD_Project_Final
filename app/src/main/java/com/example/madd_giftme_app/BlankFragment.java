package com.example.madd_giftme_app;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.madd_giftme_app.Model.Products;
import com.example.madd_giftme_app.ViewHolder.OccasionProcductViewHolder;
import com.example.madd_giftme_app.ui.occasions.OccasionsViewModel;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class BlankFragment extends Fragment {
    private TextView occasion;
    private String event;
    private DatabaseReference ref ;
    private RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    private View view ;

    private OccasionsViewModel occasionsViewModel;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_blank, container, false);

        ref = FirebaseDatabase.getInstance().getReference().child("Products");
        recyclerView =(RecyclerView) view.findViewById(R.id.ocasionProductsRecycler);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        Bundle mBundle ;
        mBundle = getArguments();
        event =  mBundle.getString("event");


        return view;
    }


    @Override
    public void onStart() {
        super.onStart();
        FirebaseRecyclerOptions<Products> options  = new FirebaseRecyclerOptions.Builder<Products>().setQuery(ref , Products.class).build();

        final FirebaseRecyclerAdapter<Products , OccasionProcductViewHolder> adapter = new FirebaseRecyclerAdapter<Products, OccasionProcductViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull OccasionProcductViewHolder holder, int position, @NonNull final Products model) {

                if(!model.getProduct_event().equalsIgnoreCase(event) || model.getProduct_availability().equalsIgnoreCase("Not Available")) {

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
                    holder.image.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {


                            ViewProductDetails frag = new ViewProductDetails();
                            Bundle bundle = new Bundle();
                            bundle.putString("pid" , model.getProduct_id());
                            bundle.putString("price", model.getProduct_price());
                            frag.setArguments(bundle);

                            FragmentManager manager = getFragmentManager();
                            manager.beginTransaction().replace(R.id.nav_host_fragment,frag).commit();

                        }
                    });

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