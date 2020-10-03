package com.example.madd_giftme_app;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.madd_giftme_app.Model.Products;
import com.example.madd_giftme_app.Model.Riders;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class Admin_Add_rider extends AppCompatActivity {

    private Spinner spinner;
    private EditText name , license , plate ,phone;
    private CircleImageView image;
    private Button save;
    private DatabaseReference ref;
    private static final int GALLERY_PICTURE = 1;
    private Uri ImageUri;
    private String rider_name , rider_plate , rider_phone , rider_license , type , imageLink;
    private StorageReference storageReference;
    private ProgressDialog loadingBar;
    private DatabaseReference dataReff;
    private long maxID=0;
    private Riders rider;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin__add_rider);

        spinner = findViewById(R.id.spinnerVehicletye);
        loadingBar = new ProgressDialog(this);
        storageReference = FirebaseStorage.getInstance().getReference().child("Rider Images");
        dataReff = FirebaseDatabase.getInstance().getReference().child("Riders");


        ArrayAdapter<String> test = new ArrayAdapter<>(getApplicationContext() , android.R.layout.simple_list_item_1,
                getResources().getStringArray(R.array.vehicles));
        test.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(test);
        spinner.setSelection(0);

        name = findViewById(R.id.ED_ADDRIDER_NAME);
        license = findViewById(R.id.ED_ADDRIDER_LICENSE);
        phone = findViewById(R.id.ED_ADDRIDER_PHONE);
        plate = findViewById(R.id.ED_ADDRIDER_PLATE);
        save = findViewById(R.id.BTN_ADD_RIDER_SAVE);
        image = findViewById(R.id.ADDRIDERIMAGE);

        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext() , "some" , Toast.LENGTH_LONG).show();
                openGallery();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateProductData();
            }
        });

        dataReff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                maxID=snapshot.getChildrenCount();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        ImageButton home , account , products , orders;

        home = findViewById(R.id.btnHome);
        account = findViewById(R.id.btnAccount);
        products = findViewById(R.id.btnProducts);
        orders = findViewById(R.id.btnOrders);

        products.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),Admin_products.class);
                startActivity(i);
            }
        });
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),Admin_home.class);
                startActivity(i);
            }
        });
        orders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),Admin_order_home.class);
                startActivity(i);
            }
        });
        account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),Admin_account.class);
                startActivity(i);
            }
        });

    }

    private void openGallery() {

        Intent intentGallery = new Intent();
        intentGallery.setAction(Intent.ACTION_GET_CONTENT);
        intentGallery.setType("image/*");
        startActivityForResult(intentGallery, GALLERY_PICTURE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GALLERY_PICTURE && resultCode == RESULT_OK && data != null) {

            ImageUri = data.getData();
            image.setImageURI(ImageUri);
        }
    }


    private void validateProductData() {

        rider_name = name.getText().toString().trim();
        rider_license = license.getText().toString().trim();
        rider_phone = phone.getText().toString().trim();
        rider_plate = plate.getText().toString().trim();
        type = spinner.getSelectedItem().toString().trim();

        if(ImageUri == null){
            Toast.makeText(getApplicationContext(),"Please add the product image",Toast.LENGTH_LONG).show();
        }
        else if(TextUtils.isEmpty(rider_name)){
            Toast.makeText(getApplicationContext(),"Please enter product name",Toast.LENGTH_LONG).show();

        }
        else if(TextUtils.isEmpty(rider_license)){
            Toast.makeText(getApplicationContext(),"Please enter product price",Toast.LENGTH_LONG).show();

        }
        else if(TextUtils.isEmpty(rider_phone)){
            Toast.makeText(getApplicationContext(),"Please enter product description",Toast.LENGTH_LONG).show();

        }
        else if(TextUtils.isEmpty(rider_plate)){
            Toast.makeText(getApplicationContext(),"Please enter product description",Toast.LENGTH_LONG).show();

        }
        else if(TextUtils.isEmpty(type)){
            Toast.makeText(getApplicationContext(),"Please enter product description",Toast.LENGTH_LONG).show();

        }
        else{

            loadingBar.setTitle("Adding Rider");
            loadingBar.setMessage("Please wait while details are being added to the database");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();
            StoreProductInfo();


        }
    }

    private void StoreProductInfo() {

        final StorageReference filePath = storageReference.child(ImageUri.getLastPathSegment() + "IMG01.jpg");

        final UploadTask uploadTask = filePath.putFile(ImageUri);

        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                String message = e.toString();
                Toast.makeText(getApplicationContext(),"Error : " + message,Toast.LENGTH_LONG).show();
                loadingBar.dismiss();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(getApplicationContext(),"Image Added Successfully",Toast.LENGTH_LONG).show();

                Task<Uri> uriTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                        if(!task.isSuccessful()){
                            throw task.getException();

                        }

                        imageLink = filePath.getDownloadUrl().toString();
                        return filePath.getDownloadUrl();

                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if(task.isSuccessful()){

                            imageLink = task.getResult().toString();
                            Toast.makeText(getApplicationContext(),"Product Image Updated Successfully",Toast.LENGTH_LONG).show();

                            SaveProductInfoToDatabase();
                        }

                    }
                });
            }
        });

    }

    private void SaveProductInfoToDatabase() {

        maxID = maxID + 1;
        String id = "RIDER0"+ maxID;

        rider = new Riders();
        rider.setName(rider_name);
        rider.setLicense(rider_license);
        rider.setPhone(rider_phone);
        rider.setPlatenumber(rider_plate);
        rider.setImage(imageLink);
        rider.setRiderid(id);
        rider.setVehicle(type);


        dataReff.child(id).setValue(rider).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if(task.isSuccessful()){

                    Intent i = new Intent(getApplicationContext(),Admin_view_riders.class);
                    startActivity(i);
                    loadingBar.dismiss();

                    Toast.makeText(getApplicationContext(),"Rider  updated successfully",Toast.LENGTH_LONG).show();

                }
                else{
                    loadingBar.dismiss();

                    String msg = task.getException().toString();
                    Toast.makeText(getApplicationContext(),"Error" + msg,Toast.LENGTH_LONG).show();



                }
            }
        });
    }
}