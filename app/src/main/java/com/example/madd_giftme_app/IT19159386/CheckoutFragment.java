package com.example.madd_giftme_app.IT19159386;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.madd_giftme_app.Home;
import com.example.madd_giftme_app.Prevalent.Prevalent;
import com.example.madd_giftme_app.R;
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

public class CheckoutFragment extends Fragment {

    private EditText nameEditText, phoneEditText, addressEditText, cityEditText, postalCodeEditText;
    private TextView totalAmount ;
    private Button checkoutConfirmOrderButton ;
    private Double totalCheckoutAmount = 0.00 ;




    public CheckoutFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Bundle bundle = getArguments();
        if(bundle!=null){

            totalCheckoutAmount = Double.valueOf(bundle.getString("totalPrice"));

        }

//        Toast.makeText(getActivity(), "Total amount is : " + totalCheckoutAmount, Toast.LENGTH_SHORT).show();

        View v = inflater.inflate(R.layout.fragment_checkout, container, false) ;


        checkoutConfirmOrderButton = (Button) v.findViewById(R.id.checkout_order_btn);
        nameEditText = (EditText) v.findViewById(R.id.shipping_name);
        phoneEditText = (EditText) v.findViewById(R.id.checkout_user_phone);
        addressEditText = (EditText) v.findViewById(R.id.shipping_address);
        cityEditText = (EditText) v.findViewById(R.id.shipping_city);
        postalCodeEditText = (EditText) v.findViewById(R.id.city_postal_code);
        totalAmount = (TextView) v.findViewById(R.id.checkout_total_amount);

        totalAmount.setText(String.valueOf(totalCheckoutAmount)+" LKR");

        checkoutConfirmOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Check() ;

            }
        });

        return v;
    }

    private void Check() {

        if(TextUtils.isEmpty(nameEditText.getText().toString()))
            Toast.makeText(getActivity(), "Please provide the receiver's name..", Toast.LENGTH_SHORT).show();
        else if(TextUtils.isEmpty(phoneEditText.getText().toString()))
            Toast.makeText(getActivity(), "Please provide the receiver's phone number..", Toast.LENGTH_SHORT).show();
        else if(TextUtils.getTrimmedLength(phoneEditText.getText().toString())!=10)
            Toast.makeText(getActivity(), "Your phone number is incorrect. Enter 10 digits number..", Toast.LENGTH_SHORT).show();
        else if(TextUtils.isEmpty(addressEditText.getText().toString()))
            Toast.makeText(getActivity(), "Please provide the receiver's address..", Toast.LENGTH_SHORT).show();
        else if(TextUtils.isEmpty(cityEditText.getText().toString()))
            Toast.makeText(getActivity(), "Please provide the receiver's city name..", Toast.LENGTH_SHORT).show();
        else if(TextUtils.isEmpty(postalCodeEditText.getText().toString()))
            Toast.makeText(getActivity(), "Please provide the receiver's city postal code..", Toast.LENGTH_SHORT).show();
        else if(TextUtils.getTrimmedLength(postalCodeEditText.getText().toString())!=4)
            Toast.makeText(getActivity(), "Provided postal code is incorrect..", Toast.LENGTH_SHORT).show();
        else {

            ConfirmOrder() ;
        }

    }

    private void ConfirmOrder() {



        final String saveCurrentDate, saveCurrentTime;

        Calendar calForDate = Calendar.getInstance();

        SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd, yyyy");
        saveCurrentDate = currentDate.format(calForDate.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentTime.format(calForDate.getTime());

        final String orderid = saveCurrentDate + saveCurrentTime + "ORDER";
        final String deliveryid = saveCurrentDate + saveCurrentTime + "DELIVERY";

        final DatabaseReference orderRef = FirebaseDatabase.getInstance().getReference()
                .child("Orders").child(orderid);

        HashMap<String, Object> orderMap = new HashMap<>();

        orderMap.put("orderid", orderid);
        orderMap.put("deliveryid", deliveryid);
        orderMap.put("email", Prevalent.currentOnlineUser.getEmail());
        orderMap.put("date", saveCurrentDate);
        orderMap.put("time", saveCurrentTime);
        orderMap.put("delivery_status", "Pending");
        orderMap.put("order_status", "Pending");
        orderMap.put("payment_status", "Pending");
        orderMap.put("total", String.valueOf(totalCheckoutAmount));
        orderMap.put("discount", "");

        DatabaseReference test = FirebaseDatabase.getInstance().getReference().child("OrderItems");


        final HashMap test1 = (HashMap) getArguments().getSerializable("items");

        Iterator it = test1.entrySet().iterator();
        while (it.hasNext()) {

            Map.Entry pair = (Map.Entry)it.next();
            test.child(pair.getKey()+orderid).child("pid").setValue(pair.getKey());
            test.child(pair.getKey()+orderid).child("quantity").setValue(pair.getValue());
            test.child(pair.getKey()+orderid).child("orderid").setValue(orderid);

            //  System.out.println(pair.getKey() + " = " + pair.getValue());
            it.remove(); // avoids a ConcurrentModificationException
        }

        orderRef.updateChildren(orderMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if (task.isSuccessful()) {

                    FirebaseDatabase.getInstance().getReference()
                            .child("AddProducts")
                            .child("User View")
                            .child(Prevalent.currentOnlineUser.getEmail())
                            .removeValue()
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    if (task.isSuccessful()) {

                                        Toast.makeText(getActivity(), "Your final order has been placed successfully!", Toast.LENGTH_SHORT).show();

                                        Intent intent = new Intent(getActivity(), Home.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        startActivity(intent);
                                    }

                                }
                            });

                    final DatabaseReference deliveryRef = FirebaseDatabase.getInstance().getReference()
                            .child("Delivery").child(deliveryid);

                    HashMap<String, Object> orderDeliveryMap = new HashMap<>();

                    orderDeliveryMap.put("orderid", orderid);
                    orderDeliveryMap.put("deliveryid", deliveryid);
                    orderDeliveryMap.put("customerid", Prevalent.currentOnlineUser.getEmail());
                    orderDeliveryMap.put("name", nameEditText.getText().toString());
                    orderDeliveryMap.put("phone", phoneEditText.getText().toString());
                    orderDeliveryMap.put("email", Prevalent.currentOnlineUser.getEmail());
                    orderDeliveryMap.put("address", addressEditText.getText().toString());
                    orderDeliveryMap.put("city", cityEditText.getText().toString());
                    orderDeliveryMap.put("postalcode", postalCodeEditText.getText().toString());
                    orderDeliveryMap.put("delivery_status", "Pending");
                    orderDeliveryMap.put("rider", "not assigned");

                    deliveryRef.updateChildren(orderDeliveryMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if (task.isSuccessful()) {

                                Toast.makeText(getActivity(), "Delivery details saved!", Toast.LENGTH_SHORT).show();

                            }
                        }
                    });
                }
            }

        });
    }

}