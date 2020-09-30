/*N.I.T.Hewage_IT19220116
 * MADD mini project 2020_Y2S2_GIFT_ODERIN_GAPP
 * Delivery managment
 * */
package com.example.giftme;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;
/*
 * class for viewing whole delivery history
 * */
public class Viewdelivery extends AppCompatActivity {
    EditText name,address;
    Button buttonAdd;
    ListView  listView;
    DatabaseReference dbreff;
    List<testClass> deliveryList;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewdelivery);

        dbreff= FirebaseDatabase.getInstance().getReference("Delivery2");

        name=findViewById(R.id.name);
        address=findViewById(R.id.address);
        buttonAdd=findViewById(R.id.buttonAdd);
        listView=findViewById(R.id.listViewDelivery);

        deliveryList =new ArrayList<>();
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addDelivery();
            }
        });
    }
    @Override
    protected void onStart() {
        super.onStart();

        dbreff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                deliveryList.clear();
                for(DataSnapshot deliverySnapshot : dataSnapshot.getChildren()){
                    testClass del = deliverySnapshot.getValue(testClass.class);
                    deliveryList.add(del);
                }
                DeliveryList adapter = new DeliveryList(Viewdelivery.this,deliveryList);
                listView.setAdapter(adapter);
            }
            @Override
            public void onCancelled(DatabaseError error) { }
        });
    }

    private void addDelivery(){
        String namee=name.getText().toString().trim();
        String addressee=address.getText().toString().trim();

        if(!TextUtils.isEmpty(namee)) {
            String id = dbreff.push().getKey();
            testClass delivery = new testClass(id, namee, addressee);

            dbreff.child(id).setValue(delivery);
            Toast.makeText(this, "adding success", Toast.LENGTH_LONG).show();
        }
        else{
            Toast.makeText(this,"you should enter a name",Toast.LENGTH_LONG).show();
        }
    }
}