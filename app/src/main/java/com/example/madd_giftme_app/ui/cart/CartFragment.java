package com.example.madd_giftme_app.ui.cart;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.madd_giftme_app.CheckoutFragment;
import com.example.madd_giftme_app.Home;
import com.example.madd_giftme_app.Model.AddProducts;
import com.example.madd_giftme_app.Prevalent.Prevalent;
import com.example.madd_giftme_app.ViewHolder.AddProductsViewHolder;
import com.example.madd_giftme_app.ViewProductDetails;
import com.example.madd_giftme_app.display_product_test;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import com.example.madd_giftme_app.R;

public class CartFragment extends Fragment {

    private View cartView ;
    private RecyclerView recyclerView ;
    private Button nextProcessButton ;
    private TextView txtTotalAmount, textmsg1, textmsg2 ;
    public Double totalPrice = 0.00 ;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        cartView = inflater.inflate(R.layout.fragment_cart, container, false);

        recyclerView = (RecyclerView)cartView.findViewById(R.id.cart_list);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        nextProcessButton = (Button)cartView.findViewById(R.id.next_process_button);
        txtTotalAmount = (TextView) cartView.findViewById(R.id.total_price_of_cart_list);
        textmsg1 = (TextView) cartView.findViewById(R.id.cart_message);

        nextProcessButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txtTotalAmount.setText("Total price : " + String.valueOf(totalPrice));

                if(totalPrice!=0){

                    CheckoutFragment checkoutFragment = new CheckoutFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("totalPrice", String.valueOf(totalPrice));
                    checkoutFragment.setArguments(bundle);
                    FragmentManager manager = getFragmentManager();
                    manager.beginTransaction().replace(R.id.nav_host_fragment, checkoutFragment).addToBackStack(null).commit();

                }
                else
                    Toast.makeText(getActivity(), "Please add products to your cart to proceed..!", Toast.LENGTH_SHORT).show();


            }
        });

        return cartView;
    }

    @Override
    public void onStart() {
        super.onStart();

        checkOrderState();

        final DatabaseReference cartListRef = FirebaseDatabase.getInstance().getReference().child("AddProducts");

        FirebaseRecyclerOptions<AddProducts> options =
                new FirebaseRecyclerOptions.Builder<AddProducts>()
                        .setQuery(cartListRef.child("User View")
                                .child(Prevalent.currentOnlineUser.getEmail()).child("Products"), AddProducts.class)
                        .build();

        FirebaseRecyclerAdapter<AddProducts, AddProductsViewHolder> adapter
                = new FirebaseRecyclerAdapter<AddProducts, AddProductsViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull AddProductsViewHolder holder, int position, @NonNull final AddProducts model) {

                holder.txtProductQuantity.setText("Quantity = "+model.getQuantity());
                holder.txtProductName.setText(model.getPname());
                holder.txtProductPrice.setText("Price = " + model.getPrice() + " LKR");
                Picasso.get().load(model.getImage()).into(holder.cartImage);

                Double oneTypeProductTotalPrice = ((Double.valueOf(model.getPrice()))) * Double.valueOf(model.getQuantity()) ;

                calculateTotalOrderPrice(oneTypeProductTotalPrice);

                holder.itemView.setOnClickListener(new View.OnClickListener() {
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

                                    ViewProductDetails viewProductDetails = new ViewProductDetails();
                                    Bundle bundle = new Bundle();
                                    bundle.putString("pid", model.getPid());
                                    viewProductDetails.setArguments(bundle);
                                    FragmentManager manager = getFragmentManager();
                                    manager.beginTransaction().replace(R.id.nav_host_fragment, viewProductDetails).addToBackStack(null).commit();

                                }
                                if(i==1){
                                    cartListRef.child("User View")
                                            .child(Prevalent.currentOnlineUser.getEmail())
                                            .child("Products")
                                            .child(model.getPid())
                                            .removeValue()
                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {

                                                    if(task.isSuccessful()){

                                                        Toast.makeText(getActivity(), "Item removed successfully!", Toast.LENGTH_SHORT).show();

                                                        Intent intent = new Intent(getActivity(), Home.class);
                                                        startActivity(intent);
                                                    }
                                                }
                                            });
                                }
                            }

                        });builder.show();

                    }
                });

            }

            @NonNull
            @Override
            public AddProductsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_items_layout, parent, false);
                AddProductsViewHolder holder = new AddProductsViewHolder(view);
                return holder ;
            }
        };

        recyclerView.setAdapter(adapter);
        adapter.startListening();

    }

    private void checkOrderState(){

        DatabaseReference orderRef ;
        orderRef = FirebaseDatabase.getInstance().getReference().child("Orders").child(Prevalent.currentOnlineUser.getEmail());

        orderRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(dataSnapshot.exists()){

                    String shippingState = dataSnapshot.child("delivery_status").getValue().toString();

                    if(shippingState.equals("Shipped")){

                        txtTotalAmount.setText("Your order has shipped successfully!");
                        recyclerView.setVisibility(View.GONE);
                        textmsg1.setVisibility(View.VISIBLE);
                        nextProcessButton.setVisibility(View.GONE);

                        Toast.makeText(getActivity(), "Shipped", Toast.LENGTH_SHORT).show();
                    }
                    else if(shippingState.equals("Pending")){

                        txtTotalAmount.setText("Delivery state of your order is pending.");
                        recyclerView.setVisibility(View.GONE);
                        textmsg2.setVisibility(View.VISIBLE);
                        nextProcessButton.setVisibility(View.GONE);

                        Toast.makeText(getActivity(), "Pending", Toast.LENGTH_SHORT).show();

                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public double calculateTotalOrderPrice(double oneProductPrice){

        return totalPrice = totalPrice + oneProductPrice ;

    }
}