package com.example.madd_giftme_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.madd_giftme_app.Model.Admin;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rey.material.widget.SnackBar;

public class Admin_reset_password extends AppCompatActivity {

    private EditText oldpwd , newpwd , repwd;
    private Button save ,cancel;
    private DatabaseReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_reset_password);

        ref = FirebaseDatabase.getInstance().getReference().child("Admins");

        oldpwd = findViewById(R.id.ed_OLDPASSWORD);
        newpwd = findViewById(R.id.ed_NEWPASSOWRD);
        repwd = findViewById(R.id.ed_REPEATPASSWORD);

        save = findViewById(R.id.BTN_SAVE_ADMIN_PASSWORD);
        cancel = findViewById(R.id.BTN_ADMIN_CANCEL_RESET);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetPasword();
            }
        });
    }

    private void resetPasword() {

        if(TextUtils.isEmpty(oldpwd.getText())){
            Snackbar.make(findViewById(android.R.id.content), "Please enter your old password", Snackbar.LENGTH_LONG).show();
        }
        else if(TextUtils.isEmpty(newpwd.getText())){
            Snackbar.make(findViewById(android.R.id.content), "Please enter your new password", Snackbar.LENGTH_LONG).show();
        }
        else if(TextUtils.isEmpty(repwd.getText())){
            Snackbar.make(findViewById(android.R.id.content), "Please repeat your new password", Snackbar.LENGTH_LONG).show();
        }
        else if(!oldpwd.getText().toString().equals(getIntent().getStringExtra("password"))){
            Snackbar.make(findViewById(android.R.id.content), "Incorrect password entered for the current password", Snackbar.LENGTH_LONG).show();

        }
        else if(!newpwd.getText().toString().equals(repwd.getText().toString())){
            Snackbar.make(findViewById(android.R.id.content), "Entered new password and repeat password  does not match", Snackbar.LENGTH_LONG).show();

        }
        else if(newpwd.getText().toString().equals(oldpwd.getText().toString())){
            Snackbar.make(findViewById(android.R.id.content), "New password cannot be the same password as before", Snackbar.LENGTH_LONG).show();

        }
        else{



            ref.child(getIntent().getStringExtra("email")).child("password").setValue(newpwd.getText().toString().trim());
            Intent i = new Intent(getApplicationContext(),Admin_account.class);
            startActivity(i);
            Toast.makeText(getApplicationContext(),"Password changed sccessfully " , Toast.LENGTH_LONG).show();
        }

    }
}