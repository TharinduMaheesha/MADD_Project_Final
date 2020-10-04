package com.example.madd_giftme_app.IT19210902;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.madd_giftme_app.Home;
import com.example.madd_giftme_app.R;
import com.example.madd_giftme_app.Prevalent.Prevalent;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class CancelOrder extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private Spinner spinner ;
    private String mainReason, orderID, finalReason, requestID, saveCurrentDate, saveCurrentTime ;
    private String status = "Pending" ;
    private String space = ". " ;
    private EditText otherReasons ;
    private Button confirmCancellation, home ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cancel_order);

        orderID = getIntent().getStringExtra("oid");

        spinner = (Spinner) findViewById(R.id.cancel_order_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.reasons, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        otherReasons = (EditText) findViewById(R.id.cancel_order_other_reasons);
        confirmCancellation = (Button) findViewById(R.id.cancel_order_button_confirm);
        home = (Button) findViewById(R.id.cancel_order_button_go_to_home);

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(CancelOrder.this, Home.class);
                startActivity(intent);

            }
        });

        confirmCancellation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               cancelOrder();

            }
        });

    }

    private void cancelOrder() {

        final DatabaseReference orderListRef = FirebaseDatabase.getInstance().getReference().child("Orders");

        requestRefund();

        orderListRef
                .child(orderID)
                .removeValue()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if(task.isSuccessful()){

                            Toast.makeText(CancelOrder.this, "Your order is removed successfully!", Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(CancelOrder.this, Home.class);
                            startActivity(intent);
                        }
                    }
                });
    }


    private void requestRefund() {

        Calendar calForDate = Calendar.getInstance();

        SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd, yyyy");
        saveCurrentDate = currentDate.format(calForDate.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentTime.format(calForDate.getTime());

        finalReason = mainReason + space + otherReasons.getText().toString() ;
        requestID = saveCurrentDate + saveCurrentTime + "REQUEST";

        final DatabaseReference refundRequestList = FirebaseDatabase.getInstance().getReference();

        final HashMap<String , Object> requestMap = new HashMap<>();
        requestMap.put("orderid", orderID);
        requestMap.put("email",Prevalent.currentOnlineUser.getEmail());
        requestMap.put("reason", finalReason);
        requestMap.put("requestid", requestID);
        requestMap.put("status", status);

        refundRequestList
                .child("Refunds")
                .child(requestID)
                .updateChildren(requestMap)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if(task.isSuccessful()){

                            Toast.makeText(CancelOrder.this, "Your request is saved. Money will be returned soon.", Toast.LENGTH_SHORT).show();

                        }
                    }
                });

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

        mainReason = adapterView.getItemAtPosition(i).toString();

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}