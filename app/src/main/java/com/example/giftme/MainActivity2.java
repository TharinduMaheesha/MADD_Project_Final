package com.example.giftme;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity2 extends AppCompatActivity {

    Button button1,button2,button3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        button1=(Button)findViewById(R.id.btnAdd);
        button2=(Button)findViewById(R.id.btnView);
        button3=(Button)findViewById(R.id.btn3);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                openAddRider();
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewRiders();
            }
        });
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                goback();
            }
        });
    }

    public void openAddRider(){
        Intent intent4= new Intent(this,MainActivity4.class);
        startActivity(intent4);
        Toast.makeText(MainActivity2.this, "Enter new employee details here",Toast.LENGTH_LONG).show();
    }
    public void viewRiders(){
        Intent intent2=new Intent(this,MainActivity6.class);
        startActivity(intent2);
        Toast.makeText(MainActivity2.this,"All riders are here",Toast.LENGTH_LONG).show();
    }
    public void goback(){
        Intent intent= new Intent(this,MainActivity.class);
        startActivity(intent);
        Toast.makeText(MainActivity2.this, "<< One page back ", Toast.LENGTH_LONG).show();
    }

}

