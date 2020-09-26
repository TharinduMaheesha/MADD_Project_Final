package com.example.giftme;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;

public class MainActivity6 extends AppCompatActivity {
    ListView myListView;
    List<AddRider> riderList;
    DatabaseReference mRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main6);
        getSupportActionBar().setTitle("GiftME Delivery Administrator");

        myListView = findViewById(R.id.listview1);
        riderList = new ArrayList<>();

        mRef = FirebaseDatabase.getInstance().getReference("DeliveryEmployees");

        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                riderList.clear();
                for (DataSnapshot riderDataSnap : snapshot.getChildren()) {
                    AddRider addRider = riderDataSnap.getValue(AddRider.class);
                    riderList.add(addRider);
                }
                ListAdapter adapter = new ListAdapter(MainActivity6.this, riderList);
                myListView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
