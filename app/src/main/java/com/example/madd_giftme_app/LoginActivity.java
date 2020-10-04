package com.example.madd_giftme_app;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.madd_giftme_app.Model.Users;
import com.example.madd_giftme_app.Prevalent.Prevalent;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rey.material.widget.CheckBox;

import io.paperdb.Paper;

public class LoginActivity extends AppCompatActivity {


    private EditText inputUserEmail, inputPassword ;
    private Button loginButton ;
    private ProgressDialog loadingBar ;
    private String parentDbName = "Users" ;
    private TextView adminAccount, notAdminAccount ;
    private CheckBox checkBoxRememberMe ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginButton = (Button) findViewById(R.id.login_btn);
        inputUserEmail = (EditText) findViewById(R.id.login_email_input);
        inputPassword = (EditText) findViewById(R.id.login_password_input);
        adminAccount = (TextView) findViewById(R.id.admin_panel_link);
        notAdminAccount= (TextView) findViewById(R.id.not_admin_panel_link);

        loadingBar = new ProgressDialog(this);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                LoginUser();

            }
        });

        adminAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                loginButton.setText("Login admin");
                adminAccount.setVisibility(View.INVISIBLE);
                notAdminAccount.setVisibility(View.VISIBLE);
                parentDbName = "Admins";
            }

        });

        notAdminAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                loginButton.setText("Login");
                adminAccount.setVisibility(View.VISIBLE);
                notAdminAccount.setVisibility(View.INVISIBLE);
                parentDbName = "Users";
            }
        });

        checkBoxRememberMe = (CheckBox) findViewById(R.id.rememberMe_checkBox);
        Paper.init(this);

    }

    private void LoginUser() {

        String email = inputUserEmail.getText().toString();
        String password = inputPassword.getText().toString();

        if(TextUtils.isEmpty(email))
            Toast.makeText(this, "User email is required!", Toast.LENGTH_SHORT).show();
        else if(TextUtils.isEmpty(password))
            Toast.makeText(this, "User password is required!", Toast.LENGTH_SHORT).show();
        else{
            loadingBar.setTitle("Login account");
            loadingBar.setMessage("Please wait, while we are checking the credentials");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();

            AllowAccessToAccount(email, password);

        }
    }

    private void AllowAccessToAccount(final String email, final String password) {

        if(checkBoxRememberMe.isChecked()){

            Paper.book().write(Prevalent.userEmailKey, email);
            Paper.book().write(Prevalent.userPasswordKey, password);
        }

        final DatabaseReference RootRef ;
        RootRef = FirebaseDatabase.getInstance().getReference();

        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(dataSnapshot.child(parentDbName).child(email).exists()){

                    Users usersData = dataSnapshot.child(parentDbName).child(email).getValue(Users.class);

                    if(usersData.getEmail().equals(email)){

                        if(usersData.getPassword().equals(password)){

                            if(parentDbName.equals("Admins")){

                                Toast.makeText(LoginActivity.this, "Logged in successfully!", Toast.LENGTH_SHORT).show();
                                loadingBar.dismiss();

                                Intent intent = new Intent(LoginActivity.this, Admin_home.class);
                                Prevalent.currentOnlineUser= usersData ;
                                startActivity(intent);
                            }
                            else if(parentDbName.equals("Users")){

                                Toast.makeText(LoginActivity.this, "Logged in successfully!", Toast.LENGTH_SHORT).show();
                                loadingBar.dismiss();

                                Intent intent = new Intent(LoginActivity.this, Home.class);
                                Prevalent.currentOnlineUser= usersData ;
                                startActivity(intent);

                            }
                        }
                        else {

                            Toast.makeText(LoginActivity.this, "Entered password is incorrect.", Toast.LENGTH_SHORT).show();
                            loadingBar.dismiss();
                        }
                    }

                }
                else {

                    Toast.makeText(LoginActivity.this, "Account with this " + email + "email is not exist.", Toast.LENGTH_SHORT).show();
                    loadingBar.dismiss();
                    Toast.makeText(LoginActivity.this, "Please create an account to proceed.", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }


}