package com.example.madd_giftme_app;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.madd_giftme_app.Prevalent.Prevalent;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ConfirmFinalOrder extends AppCompatActivity {

    private EditText nameEditText, phoneEditText, addressEditText, cityEditText, postalCodeEditText ;
    Button checkoutConfirmOrderButton ;
    private String totalAmount ;
    HashMap some = new HashMap();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_final_order);
        
        Intent intent = getIntent();
        Bundle args = intent.getBundleExtra("bundle");
         final HashMap test1 = (HashMap) args.getSerializable("items");




        totalAmount = getIntent().getStringExtra("Total price");
        Toast.makeText(this, "Total price : " + totalAmount + "LKR", Toast.LENGTH_SHORT).show();

        checkoutConfirmOrderButton = (Button) findViewById(R.id.checkout_order_btn);
        nameEditText = (EditText) findViewById(R.id.shipping_name);
        phoneEditText = (EditText) findViewById(R.id.checkout_user_phone);
        addressEditText = (EditText) findViewById(R.id.shipping_address);
        cityEditText = (EditText) findViewById(R.id.shipping_city);
        postalCodeEditText = (EditText) findViewById(R.id.city_postal_code);

        checkoutConfirmOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Check() ;
                DatabaseReference test = FirebaseDatabase.getInstance().getReference().child("OrderItems");
                String oid = "OID05";

                Iterator it = test1.entrySet().iterator();
                while (it.hasNext()) {

                    Map.Entry pair = (Map.Entry)it.next();
                    test.child(pair.getKey()+oid).child("pid").setValue(pair.getKey());
                    test.child(pair.getKey()+oid).child("quantity").setValue(pair.getValue());
                    test.child(pair.getKey()+oid).child("orderid").setValue(pair.getValue());

                    //  System.out.println(pair.getKey() + " = " + pair.getValue());
                    it.remove(); // avoids a ConcurrentModificationException
                }

            }
        });

    }

    private void Check() {

        if(TextUtils.isEmpty(nameEditText.getText().toString()))
            Toast.makeText(this, "Please provide your name..", Toast.LENGTH_SHORT).show();
        else if(TextUtils.isEmpty(phoneEditText.getText().toString()))
            Toast.makeText(this, "Please provide your phone number..", Toast.LENGTH_SHORT).show();
        else if(TextUtils.isEmpty(addressEditText.getText().toString()))
            Toast.makeText(this, "Please provide your address..", Toast.LENGTH_SHORT).show();
        else if(TextUtils.isEmpty(cityEditText.getText().toString()))
            Toast.makeText(this, "Please provide your city name..", Toast.LENGTH_SHORT).show();
        else if(TextUtils.isEmpty(postalCodeEditText.getText().toString()))
            Toast.makeText(this, "Please provide the postal code of your city..", Toast.LENGTH_SHORT).show();
        else {

            ConfirmOrder() ;
        }

    }

    private void ConfirmOrder() {

        String saveCurrentDate, saveCurrentTime ;

        Calendar calForDate = Calendar.getInstance();

        SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd, yyyy");
        saveCurrentDate = currentDate.format(calForDate.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentDate.format(calForDate.getTime());

        final DatabaseReference orderRef = FirebaseDatabase.getInstance().getReference()
                .child(Prevalent.currentOnlineUser.getEmail());


        HashMap<String, Object> orderMap = new HashMap<>();

        orderMap.put("totalAmount", totalAmount);
        orderMap.put("name", nameEditText.getText().toString());
        orderMap.put("phone",phoneEditText.getText().toString());
        orderMap.put("address", addressEditText.getText().toString());
        orderMap.put("city", cityEditText.getText().toString());
        orderMap.put("postalCode", postalCodeEditText.getText().toString());
        orderMap.put("date", saveCurrentDate);
        orderMap.put("time", saveCurrentTime);
        orderMap.put("state", "not shipped");

        orderRef.updateChildren(orderMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if(task.isSuccessful()){

                    FirebaseDatabase.getInstance().getReference()
                            .child("AddProducts")
                            .child("User View")
                            .child(Prevalent.currentOnlineUser.getEmail())
                            .removeValue()
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    if(task.isSuccessful()){

                                        Toast.makeText(ConfirmFinalOrder.this, "Your final has been placed successfully!", Toast.LENGTH_SHORT).show();

                                        Intent intent = new Intent(ConfirmFinalOrder.this, display_product_test.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK) ;
                                        startActivity(intent);
                                        finish();
                                    }

                                }
                            });
                }
            }
        });
    }
}