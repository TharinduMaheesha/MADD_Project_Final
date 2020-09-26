package com.example.madd_giftme_app;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {

    private Button createAccountButton ;
    private EditText inputUserName, inputUserEmail, inputUserPassword ;
    private ProgressDialog loadingBar ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        createAccountButton = (Button) findViewById(R.id.register_btn);
        inputUserName = (EditText) findViewById(R.id.register_userName_input);
        inputUserEmail = (EditText) findViewById(R.id.register_email_input);
        inputUserPassword = (EditText) findViewById(R.id.register_password_input);
        loadingBar = new ProgressDialog(this);

        createAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                CreateAccount() ;

            }
        });

    }

    private void CreateAccount() {

        String name = inputUserName.getText().toString();
        String email = inputUserEmail.getText().toString();
        String password = inputUserPassword.getText().toString();

        if(TextUtils.isEmpty(name))
            Toast.makeText(this, "User name is required!", Toast.LENGTH_SHORT).show();
        else if(TextUtils.isEmpty(email))
            Toast.makeText(this, "User email is required!", Toast.LENGTH_SHORT).show();
        else if(TextUtils.isEmpty(password))
            Toast.makeText(this, "User password is required!", Toast.LENGTH_SHORT).show();
        else{
            loadingBar.setTitle("Create account");
            loadingBar.setMessage("Please wait, while we are checking the credentials");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();

            ValidateEmail(name, email, password) ;
        }
    }

    private void ValidateEmail(final String name, final String email, final String password) {

        final DatabaseReference RootRef ;
        RootRef = FirebaseDatabase.getInstance().getReference();

        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(!(dataSnapshot.child("Users").child(email).exists())){

                    HashMap<String, Object> userDataMap = new HashMap<>();
                    userDataMap.put("name", name);
                    userDataMap.put("email", email);
                    userDataMap.put("password", password);

                    RootRef.child("Users").child(email).updateChildren(userDataMap)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if(task.isSuccessful()){

                                Toast.makeText(RegisterActivity.this, "Welcome to GiftME!", Toast.LENGTH_SHORT).show();
                                loadingBar.dismiss();

                                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                startActivity(intent);

                            }
                            else {

                                loadingBar.dismiss();
                                Toast.makeText(RegisterActivity.this, "Network error please try again later...", Toast.LENGTH_SHORT).show();

                            }
                        }
                    });
                }
                else{

                    Toast.makeText(RegisterActivity.this, "This" + email + "already has an account!", Toast.LENGTH_SHORT).show();
                    loadingBar.dismiss();
                    Toast.makeText(RegisterActivity.this, "Please try using another email address..", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(RegisterActivity.this, StartingActivity.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}