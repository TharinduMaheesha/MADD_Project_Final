
/*N.I.T.Hewage_IT19220116
 * MADD mini project 2020_Y2S2_GIFT_ODERIN_GAPP
 * Delivery managment
 * */
package com.example.giftme;

import android.os.Bundle;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


/*
* class for view all riders who have already an registered employee
* */
public class ViewAllRiders extends AppCompatActivity {
    ListView myListView;
    List<AddRider> riderList;
    DatabaseReference mRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_all_riders);
        getSupportActionBar().setTitle("GiftME Delivery Rider Team");

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
                ListAdapter adapter = new ListAdapter(ViewAllRiders.this,riderList);
                myListView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
