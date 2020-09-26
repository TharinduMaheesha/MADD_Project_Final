package com.example.giftme;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class updateDelete extends AppCompatActivity {

    Button buttonRemove, buttonUpdate,buttonBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_delete);

        buttonRemove=(Button)findViewById(R.id.btnRemove);
        buttonUpdate=(Button)findViewById(R.id.btnUpdate);
        buttonBack=(Button)findViewById(R.id.btnBack);

    }
}