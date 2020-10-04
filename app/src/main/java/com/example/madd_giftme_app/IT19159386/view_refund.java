package com.example.madd_giftme_app.IT19159386;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.madd_giftme_app.IT19210902.Admin_order_home;
import com.example.madd_giftme_app.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class view_refund extends AppCompatActivity {

    TextView rid , oid , mail ,amount , reason;
    Button accept , reject;
    private DatabaseReference ref ;
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_refund);

        Intent i = getIntent();
        id=i.getStringExtra("rid");


        rid = findViewById(R.id.rid);
        oid = findViewById(R.id.oid);
        mail = findViewById(R.id.mail);
        amount = findViewById(R.id.amount);
        reason = findViewById(R.id.reason);
        accept = findViewById(R.id.refaccept);
        reject =findViewById(R.id.refdecline);

        rid.setText("Request ID : "+ i.getStringExtra("rid"));
        oid.setText("Order ID : "+ i.getStringExtra("oid"));
        mail.setText("User : "+ i.getStringExtra("user"));
        amount.setVisibility(View.GONE);
        reason.setText("Reason : "+ i.getStringExtra("reason"));

        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ref = FirebaseDatabase.getInstance().getReference().child("Refunds").child(id);
                ref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        snapshot.getRef().child("status").setValue("Accepted");
                        Toast.makeText(getApplicationContext(),"Refund confirmed" , Toast.LENGTH_LONG).show();


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

                Intent i = new Intent(getApplicationContext(), Admin_order_home.class);
                startActivity(i);
            }
        });

        reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ref = FirebaseDatabase.getInstance().getReference().child("Refunds").child(id);
                ref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        snapshot.getRef().child("status").setValue("Declined");
                        Toast.makeText(getApplicationContext(),"Refund declined" , Toast.LENGTH_LONG).show();


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

                Intent i = new Intent(getApplicationContext(),Admin_order_home.class);
                startActivity(i);
            }
        });
    }
}