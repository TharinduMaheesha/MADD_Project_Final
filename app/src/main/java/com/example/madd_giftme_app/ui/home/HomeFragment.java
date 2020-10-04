package com.example.madd_giftme_app.ui.home;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.madd_giftme_app.Model.Products;
import com.example.madd_giftme_app.R;
import com.example.madd_giftme_app.ViewHolder.ProductViewHolder;
import com.example.madd_giftme_app.IT19162706.ViewProductDetails;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class HomeFragment extends Fragment{

    private View homeView ;
    private RecyclerView productsList ;
    private DatabaseReference ProductsRef ;
    private EditText input;
    private FloatingActionButton cancel , show;
    private String inputext;
    private Button search;
    private LinearLayout LN;

    public HomeFragment(){


    }



    @SuppressLint("RestrictedApi")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        homeView = inflater.inflate(R.layout.fragment_home, container, false);

        productsList = (RecyclerView) homeView.findViewById(R.id.recycler_menu_fr_home) ;
        productsList.setHasFixedSize(true);
        productsList.setLayoutManager(new LinearLayoutManager(getContext()));

        ProductsRef = FirebaseDatabase.getInstance().getReference().child("Products");

        input = homeView.findViewById(R.id.EDHOMEINPUT);
        search = homeView.findViewById(R.id.BTNHOMESEARCH);
        show = homeView.findViewById(R.id.HOMESEARCHFLOAT);
        cancel = homeView.findViewById(R.id.HOMESEARCCANCEL);
        LN = homeView.findViewById(R.id.LNSEARCHOME);
        cancel.setVisibility(View.INVISIBLE);
        LN.setVisibility(View.INVISIBLE);

        show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                show.setVisibility(View.INVISIBLE);
                LN.setVisibility(View.VISIBLE);
                cancel.setVisibility(View.VISIBLE);

            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancel.setVisibility(View.INVISIBLE);
                LN.setVisibility(View.INVISIBLE);
                show.setVisibility(View.VISIBLE);

            }
        });

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inputext = input.getText().toString();
                onStart();
            }
        });
        return homeView;
    }

    @Override
    public void onStart() {

        super.onStart();

        FirebaseRecyclerOptions<Products> options =
                new FirebaseRecyclerOptions.Builder<Products>()
                        .setQuery(ProductsRef.orderByChild("product_name").startAt(inputext), Products.class)
                        .build();

        FirebaseRecyclerAdapter<Products, ProductViewHolder> adapter =
                new FirebaseRecyclerAdapter<Products, ProductViewHolder>(options) {

                    @Override
                    protected void onBindViewHolder(@NonNull ProductViewHolder holder, int position, @NonNull final Products model) {

                        holder.txtProductName.setText(model.getProduct_name());
                        holder.txtProductPrice.setText("Price : " + model.getProduct_price() + " LKR");
                        holder.txtProductDescription.setText("Event : " + model.getProduct_event());
                        Picasso.get().load(model.getProduct_image()).into(holder.cardImageView);

                        holder.itemView.setOnClickListener(new View.OnClickListener() {
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

                    @NonNull
                    @Override
                    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_items_layout, parent, false);
                        ProductViewHolder holder = new ProductViewHolder(view);
                        return holder;
                    }
                };

        productsList.setAdapter(adapter);
        adapter.startListening();
    }


}

