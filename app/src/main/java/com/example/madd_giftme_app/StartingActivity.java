package com.example.madd_giftme_app;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.madd_giftme_app.Model.Users;
import com.example.madd_giftme_app.Prevalent.Prevalent;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import io.paperdb.Paper;

public class StartingActivity extends AppCompatActivity {

    private Button joinNowButton, loginButton ;
    private ProgressDialog loadingBar ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_starting);


        joinNowButton = (Button) findViewById(R.id.main_join_now_btn);
        loginButton = (Button) findViewById(R.id.main_login_btn);
        loadingBar = new ProgressDialog(this);


        Paper.init(this);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(StartingActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        joinNowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(StartingActivity.this, RegisterActivity.class) ;
                startActivity(intent);
            }
        });

        String emailKey = Paper.book().read(Prevalent.userEmailKey);
        String passwordKey = Paper.book().read(Prevalent.userPasswordKey);

        if(emailKey != null && passwordKey != null){

            if(TextUtils.isEmpty(emailKey) && TextUtils.isEmpty(passwordKey)){

                AllowAccess(emailKey, passwordKey);

                loadingBar.setTitle("Already logged in!");
                loadingBar.setMessage("Please wait..");
                loadingBar.setCanceledOnTouchOutside(false);
                loadingBar.show();

            }
        }
    }

    private void AllowAccess(final String email, final String password) {

        final DatabaseReference RootRef ;
        RootRef = FirebaseDatabase.getInstance().getReference();

        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(dataSnapshot.child("Users").child(email).exists()){

                    Users usersData = dataSnapshot.child("Users").child(email).getValue(Users.class);

                    if(usersData.getEmail().equals(email)){

                        if(usersData.getPassword().equals(password)){

                            Toast.makeText(StartingActivity.this, "Please wait, you are already logged in!", Toast.LENGTH_SHORT).show();
                            loadingBar.dismiss();

                            Intent intent = new Intent(StartingActivity.this, Home.class);
                            Prevalent.currentOnlineUser= usersData ;
                            startActivity(intent);
                        }
                        else {

                            Toast.makeText(StartingActivity.this, "Password is incorrect.", Toast.LENGTH_SHORT).show();
                            loadingBar.dismiss();
                        }
                    }

                }
                else {

                    Toast.makeText(StartingActivity.this, "Account with this " + email + "email is not exist.", Toast.LENGTH_SHORT).show();
                    loadingBar.dismiss();
                    Toast.makeText(StartingActivity.this, "Please create an account to proceed.", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
    public void onBackPressed()
    {
        super.onBackPressed();
        Intent i = new Intent(getApplicationContext(), StartingActivity.class);
        startActivity(i);


    }
}