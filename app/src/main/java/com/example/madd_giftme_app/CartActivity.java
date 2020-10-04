package com.example.madd_giftme_app;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.madd_giftme_app.IT19162706.ViewProductDetails;
import com.example.madd_giftme_app.IT19162706.display_product_test;
import com.example.madd_giftme_app.Model.AddProducts;
import com.example.madd_giftme_app.ViewHolder.AddProductsViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.Serializable;
import java.util.HashMap;

public class CartActivity extends AppCompatActivity {

    private RecyclerView recyclerView ;
    private RecyclerView.LayoutManager layoutManager ;
    private Button nextProcessButton ;
    private TextView txtTotalAmount ;
    public Double totalPrice = 0.00 ;
    public HashMap items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        items= new HashMap();

        recyclerView = (RecyclerView) findViewById(R.id.cart_list);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        nextProcessButton = (Button) findViewById(R.id.next_process_button);
        txtTotalAmount = (TextView) findViewById(R.id.total_price_of_cart_list);

        nextProcessButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txtTotalAmount.setText("Total price : " + String.valueOf(totalPrice));

                Intent intent = new Intent(CartActivity.this, ConfirmFinalOrder.class);
                intent.putExtra("Total price", String.valueOf(totalPrice));
                Bundle args = new Bundle();
                args.putSerializable("items" , (Serializable)items);
                intent.putExtra("bundle" , args);
                startActivity(intent);
                finish();

            }
        });

    }

    @Override
    protected void onStart() {

        super.onStart();

        final DatabaseReference cartListRef = FirebaseDatabase.getInstance().getReference().child("AddProducts");

        FirebaseRecyclerOptions<AddProducts> options =
                new FirebaseRecyclerOptions.Builder<AddProducts>()
                .setQuery(cartListRef.child("User View")
                    .child("test").child("Products"), AddProducts.class)
                        .build();

        FirebaseRecyclerAdapter<AddProducts, AddProductsViewHolder> adapter
                = new FirebaseRecyclerAdapter<AddProducts, AddProductsViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull final AddProductsViewHolder holder, int position, @NonNull final AddProducts model) {

                holder.txtProductQuantity.setText("Quantity = "+model.getQuantity());
                holder.txtProductName.setText(model.getPname());
                holder.txtProductPrice.setText("Price = " + model.getPrice() + "LKR");

                holder.box.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                        if(holder.box.isChecked()){

                            items.put(model.getPid(), model.getQuantity());
                        }
                        else if(!holder.box.isChecked()){

                            items.remove(model.getPid());
                        }
                    }
                });

             //   Double oneTypeProductTotalPrice = ((Double.valueOf(model.getPrice()))) * Double.valueOf(model.getQuantity()) ;
            //      totalPrice = totalPrice + oneTypeProductTotalPrice ;

                totalPrice = 1000.0;

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        CharSequence options [] = new CharSequence[]
                        {
                            "Edit",
                                "Remove"

                        };
                        AlertDialog.Builder builder = new AlertDialog.Builder(CartActivity.this);
                        builder.setTitle("Cart options : ");

                        builder.setItems(options, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                if(i == 0){
                                    Intent intent = new Intent(CartActivity.this, ViewProductDetails.class);
                                    intent.putExtra("pid", model.getPid());
                                    startActivity(intent);

                                }
                                if(i==1){
                                    cartListRef.child("User View")
                                            .child("test")
                                            .child(model.getPid())
                                            .removeValue()
                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {

                                                    if(task.isSuccessful()){

                                                        Toast.makeText(CartActivity.this, "Item removed successfully!", Toast.LENGTH_SHORT).show();

                                                        Intent intent = new Intent(CartActivity.this, display_product_test.class);
                                                        startActivity(intent);
                                                    }
                                                }
                                            });                                }
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
}