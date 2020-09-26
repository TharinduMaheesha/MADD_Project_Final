package com.example.giftme;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignUp extends AppCompatActivity {

    EditText txt_email,txt_pwd,txt_cpwd;
    Button ButtonSignup;
    ProgressBar progressBar;
    private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        txt_email=(EditText)findViewById(R.id.mail);
        txt_pwd=(EditText)findViewById(R.id.pass);
        txt_cpwd=(EditText)findViewById(R.id.confirm);
        ButtonSignup=(Button)findViewById(R.id.btnsignup);
        progressBar=(ProgressBar)findViewById(R.id.progressBar);

        firebaseAuth=FirebaseAuth.getInstance();

        ButtonSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email= txt_email.getText().toString().trim();
                String password=txt_pwd.getText().toString().trim();
                String confirm=txt_cpwd.getText().toString().trim();

                if(TextUtils.isEmpty(email)){
                    Toast.makeText(SignUp.this,"Enter email !",Toast.LENGTH_LONG).show();
                    return;
                }
                if (TextUtils.isEmpty(password)){
                    Toast.makeText(SignUp.this,"Enter a strong password !",Toast.LENGTH_LONG).show();
                    return;
                }
                if(TextUtils.isEmpty(confirm)){
                    Toast.makeText(SignUp.this,"Confirm password !",Toast.LENGTH_LONG).show();
                    return;
                }


                progressBar.setVisibility(View.VISIBLE);

                if(password.equals(confirm)){
                    firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(SignUp.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        progressBar.setVisibility(View.GONE);
                                       startActivity(new Intent(getApplicationContext(),MainActivity.class));
                                        Toast.makeText(SignUp.this,"Complete!",Toast.LENGTH_SHORT).show();

                                    } else {
                                        progressBar.setVisibility(View.GONE);
                                        Toast.makeText(SignUp.this,"Authentication failed!",Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                }

            }
        });

    }
}