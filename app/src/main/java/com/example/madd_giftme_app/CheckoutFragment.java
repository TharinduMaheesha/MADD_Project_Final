package com.example.madd_giftme_app;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.madd_giftme_app.Prevalent.Prevalent;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class CheckoutFragment extends Fragment {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    private EditText nameEditText, phoneEditText, addressEditText, cityEditText, postalCodeEditText ;
    Button checkoutConfirmOrderButton ;
    private Double totalCheckoutAmount = 0.00 ;

    public CheckoutFragment() {
        // Required empty public constructor
    }

    public static CheckoutFragment newInstance(String param1, String param2) {
        CheckoutFragment fragment = new CheckoutFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        Bundle bundle = getArguments();
        if(bundle!=null){

            totalCheckoutAmount = Double.valueOf(bundle.getString("totalPrice"));

        }

        Toast.makeText(getActivity(), "Total amount is : " + totalCheckoutAmount, Toast.LENGTH_SHORT).show();

        View v = inflater.inflate(R.layout.fragment_checkout, container, false) ;

        checkoutConfirmOrderButton = (Button) v.findViewById(R.id.checkout_order_btn);
        nameEditText = (EditText) v.findViewById(R.id.shipping_name);
        phoneEditText = (EditText) v.findViewById(R.id.checkout_user_phone);
        addressEditText = (EditText) v.findViewById(R.id.shipping_address);
        cityEditText = (EditText) v.findViewById(R.id.shipping_city);
        postalCodeEditText = (EditText) v.findViewById(R.id.city_postal_code);

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
            Toast.makeText(getActivity(), "Please provide your name..", Toast.LENGTH_SHORT).show();
        else if(TextUtils.isEmpty(phoneEditText.getText().toString()))
            Toast.makeText(getActivity(), "Please provide your phone number..", Toast.LENGTH_SHORT).show();
        else if(TextUtils.isEmpty(addressEditText.getText().toString()))
            Toast.makeText(getActivity(), "Please provide your address..", Toast.LENGTH_SHORT).show();
        else if(TextUtils.isEmpty(cityEditText.getText().toString()))
            Toast.makeText(getActivity(), "Please provide your city name..", Toast.LENGTH_SHORT).show();
        else if(TextUtils.isEmpty(postalCodeEditText.getText().toString()))
            Toast.makeText(getActivity(), "Please provide your postal code..", Toast.LENGTH_SHORT).show();
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
        orderMap.put("totalAmount", String.valueOf(totalCheckoutAmount));
        orderMap.put("discount", "");

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
                    orderDeliveryMap.put("customerid", Prevalent.currentOnlineUser.getEmail());
                    orderDeliveryMap.put("name", nameEditText.getText().toString());
                    orderDeliveryMap.put("phone", phoneEditText.getText().toString());
                    orderDeliveryMap.put("email", Prevalent.currentOnlineUser.getEmail());
                    orderDeliveryMap.put("address", addressEditText.getText().toString());
                    orderDeliveryMap.put("city", cityEditText.getText().toString());
                    orderDeliveryMap.put("postalCode", postalCodeEditText.getText().toString());
                    orderDeliveryMap.put("delivery_status", "Pending");
//                    orderDeliveryMap.put("totalPrice", String.valueOf(totalCheckoutAmount));
//                    orderDeliveryMap.put("placed_date", saveCurrentDate);
//                    orderDeliveryMap.put("placed_time", saveCurrentTime);

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