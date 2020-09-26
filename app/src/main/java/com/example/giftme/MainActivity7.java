package com.example.giftme;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity7 extends AppCompatActivity {

    EditText txtUsername, txtPassword;
    Button btn_login;
    private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main7);

        txtUsername=(EditText)findViewById(R.id.password);
        txtPassword=(EditText)findViewById(R.id.username);
        btn_login=(Button)findViewById(R.id.login);

        firebaseAuth=FirebaseAuth.getInstance();

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email=txtUsername.getText().toString().trim();
                String pwd=txtPassword.getText().toString().trim();

                if(TextUtils.isEmpty(email)){
                    Toast.makeText(MainActivity7.this,"please enter email ",Toast.LENGTH_LONG).show();
                    return;
                }
                if(TextUtils.isEmpty(pwd)){
                    Toast.makeText(MainActivity7.this,"please enter password",Toast.LENGTH_LONG).show();
                    return;
                }
                if(pwd.length()<6){
                    Toast.makeText(MainActivity7.this,"password too short",Toast.LENGTH_LONG).show();
                }

                firebaseAuth.signInWithEmailAndPassword(email,pwd).addOnCompleteListener(MainActivity7.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        }
                        else {
                                Toast.makeText(MainActivity7.this,"Login is failed try again",Toast.LENGTH_LONG).show();
                        }
                    }
                });

            }
        });

    }
}