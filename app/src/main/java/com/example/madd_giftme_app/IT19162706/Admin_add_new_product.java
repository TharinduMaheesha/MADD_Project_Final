package com.example.madd_giftme_app.IT19162706;

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
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.madd_giftme_app.Admin_account;
import com.example.madd_giftme_app.Admin_home;
import com.example.madd_giftme_app.IT19210902.Admin_order_home;
import com.example.madd_giftme_app.Model.Products;
import com.example.madd_giftme_app.R;
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

public class Admin_add_new_product extends AppCompatActivity {

    private Spinner spinner1 ,spinner2;
    private EditText name , price , description;
    private String productName , productPrice , productDesc , prod_availability , event;

    ImageView prod_image;
    private String prod_image_url;

    private static final int GALLERY_PICTURE = 1;
    private Uri ImageUri;
    private StorageReference storageReference;
    private ProgressDialog loadingBar;
    private DatabaseReference dataReff;
    private long maxID=0;
    private Products product;
    Button btn_save;
    ImageButton home , account , products , orders;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add_new_product);



        home = findViewById(R.id.btnHome);
        account = findViewById(R.id.btnAccount);
        products = findViewById(R.id.btnProducts);
        orders = findViewById(R.id.btnOrders);

        products.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Admin_add_new_product.this,Admin_products.class);
                startActivity(i);
            }
        });
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Admin_add_new_product.this, Admin_home.class);
                startActivity(i);
            }
        });

        orders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Admin_add_new_product.this, Admin_order_home.class);
                startActivity(i);
            }
        });
        account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), Admin_account.class);
                startActivity(i);
            }
        });

        spinner1 = findViewById(R.id.spinnerAddProductOccasion);
        spinner2 = findViewById(R.id.spinnerAddProductAvailabiity);
        name = findViewById(R.id.ED_PRODUCT_NAME);
        price = findViewById(R.id.ED_PRODUCT_PRICE);
        description = findViewById(R.id.ED_PRODUCT_DESCRIPTION);
        btn_save = findViewById(R.id.btn_save_add_product);
        prod_image= findViewById(R.id.IV_ADMIN_ADD_PRODUCT_IMAGE);


        ArrayAdapter<String> test = new ArrayAdapter<>(Admin_add_new_product.this , android.R.layout.simple_list_item_1,
                getResources().getStringArray(R.array.events));
        test.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(test);

        ArrayAdapter<String> test2 = new ArrayAdapter<String>(Admin_add_new_product.this , android.R.layout.simple_list_item_1,
                getResources().getStringArray(R.array.prodStatus));
        test.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(test2);


        storageReference = FirebaseStorage.getInstance().getReference().child("Product Images");
        dataReff = FirebaseDatabase.getInstance().getReference().child("Products");
        loadingBar = new ProgressDialog(this);
        dataReff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                maxID=snapshot.getChildrenCount();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        prod_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                openGallery();

            }
        });

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                validateProductData();
            }
        });




    }

    private void validateProductData() {
        productName = name.getText().toString().trim();
        productPrice = price.getText().toString().trim();
        productDesc = description.getText().toString().trim();
        prod_availability = spinner2.getSelectedItem().toString().trim();
        event = spinner1.getSelectedItem().toString().trim();

        if(ImageUri == null){
            Toast.makeText(getApplicationContext(),"Please add the product image",Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(productName)){
            Toast.makeText(getApplicationContext(),"Please enter product name",Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(productPrice)){
            Toast.makeText(getApplicationContext(),"Please enter product price",Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(productDesc)){
            Toast.makeText(getApplicationContext(),"Please enter product description",Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(prod_availability)){
            Toast.makeText(getApplicationContext(),"Please select product Availability",Toast.LENGTH_SHORT).show();
        }
        else {
            loadingBar.setTitle("Add Product");
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
                Toast.makeText(getApplicationContext(),"Error : " + message,Toast.LENGTH_SHORT).show();
                loadingBar.dismiss();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(getApplicationContext(),"Image Added Successfully",Toast.LENGTH_SHORT).show();

                Task<Uri> uriTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                        if(!task.isSuccessful()){
                            throw task.getException();

                        }

                        prod_image_url = filePath.getDownloadUrl().toString();
                        return filePath.getDownloadUrl();

                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if(task.isSuccessful()){

                            prod_image_url = task.getResult().toString();
                            Toast.makeText(getApplicationContext(),"Product Image added to database Successfully",Toast.LENGTH_SHORT).show();

                            SaveProductInfoToDatabase();
                        }

                    }
                });
            }
        });
    }

    private void SaveProductInfoToDatabase() {

        maxID = maxID + 1;
        String id = "PID"+ maxID;


        product = new Products();
        product.setProduct_name(productName);
        product.setProduct_id(id);
        product.setProduct_description(productDesc);
        product.setProduct_price(productPrice);
        product.setProduct_event(event);
        product.setProduct_image(prod_image_url);
        product.setProduct_availability(prod_availability);

        dataReff.child(id).setValue(product).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if(task.isSuccessful()){

                    Intent i = new Intent(Admin_add_new_product.this,Admin_products.class);
                    startActivity(i);
                    loadingBar.dismiss();

                    Toast.makeText(getApplicationContext(),"Product  added to database succesfully",Toast.LENGTH_SHORT).show();

                }
                else{
                    loadingBar.dismiss();

                    String msg = task.getException().toString();
                    Toast.makeText(getApplicationContext(),"Error" + msg,Toast.LENGTH_SHORT).show();



                }
            }
        });


    }

    private void openGallery() {

        Intent intentGallery = new Intent();
        intentGallery.setAction(Intent.ACTION_GET_CONTENT);
        intentGallery.setType("image/*");
        startActivityForResult(intentGallery,GALLERY_PICTURE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == GALLERY_PICTURE && resultCode == RESULT_OK && data != null){

            ImageUri = data.getData();
            prod_image.setImageURI(ImageUri);
        }
    }
}