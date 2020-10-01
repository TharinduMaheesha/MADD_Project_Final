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
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.madd_giftme_app.Model.Products;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class Admin_edit_product extends AppCompatActivity {


    int value = 0;
    TextView ID;
    EditText name , price , description , event;
    Button btn;
    ImageView im;
    private ProgressDialog loadingBar;
    private StorageReference storageReference;
    private static final int GALLERY_PICTURE = 1;

    String pid , prodName , desc, prodPrice , image ;
    private Uri ImageUri;
    private String prod_name , prod_price , prod_description , prod_event , prod_availability;
    private String prod_image_url;
    private Products product;
    private DatabaseReference dataReff;
    private Uri imageUri;
    Spinner spinner , spinner2;
    ImageButton home , account , products , orders;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_edit_product);

        home = findViewById(R.id.btnHome);
        account = findViewById(R.id.btnAccount);
        products = findViewById(R.id.btnProducts);
        orders = findViewById(R.id.btnOrders);

        spinner = findViewById(R.id.spinnerEDITProductOccasion);
        spinner2 = findViewById(R.id.spinnerEDITProductAvailabiity);
        loadingBar = new ProgressDialog(this);
        ID = findViewById(R.id.EDID);
        name = findViewById(R.id.ED_EDIT_NAME);


        price = findViewById(R.id.ED_EDITPRODUCT_PRICE);
        description = findViewById(R.id.ED_EDITPRODUCT_DESCRIPTION);
        im = findViewById(R.id.IV_ADMIN_EDIT_PRODUCT_IMAGE);
        dataReff = FirebaseDatabase.getInstance().getReference().child("Products");

        btn = findViewById(R.id.btn_save_edit_product);
        storageReference = FirebaseStorage.getInstance().getReference().child("Product Images");

        pid = getIntent().getStringExtra("pid");
        prodName = getIntent().getStringExtra("pName");
        desc = getIntent().getStringExtra("pdesc");
        prodPrice = getIntent().getStringExtra("pPrice");
        image = getIntent().getStringExtra("image");

        ID.setText(pid);
        name.setText(prodName);
        price.setText(prodPrice);
        description.setText(desc);
        Picasso.get().load(image).into(im);
        ImageUri = Uri.parse(image);

        ArrayAdapter<String> test = new ArrayAdapter<>(Admin_edit_product.this , android.R.layout.simple_list_item_1,
                getResources().getStringArray(R.array.events));
        test.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(test);
        setValueForSelection(getIntent().getStringExtra("occasion"));

        ArrayAdapter<String> spinnerAdpater = new ArrayAdapter<>(Admin_edit_product.this , android.R.layout.simple_list_item_1,
                getResources().getStringArray(R.array.prodStatus));
        test.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(spinnerAdpater);
        setValueForSelection2(getIntent().getStringExtra("availability"));


        im.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                value = 1;
                openGallery();

            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadingBar.setTitle("Edit Product");
                loadingBar.setMessage("Please wait while details are being added to the database");
                loadingBar.setCanceledOnTouchOutside(false);
                loadingBar.show();
                validateProductData();


            }
        });

        products.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Admin_edit_product.this,Admin_products.class);
                startActivity(i);
            }
        });
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Admin_edit_product.this,Admin_home.class);
                startActivity(i);
            }
        });
        orders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Admin_edit_product.this,Admin_order_home.class);
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


    private void setValueForSelection2(String availablity) {

        if(availablity.equalsIgnoreCase("Available")){
            spinner2.setSelection(0);

        }
        else if(availablity.equalsIgnoreCase("Not Available")){
            spinner2.setSelection(1);

        }
        else {
            spinner2.setSelection(0);

        }
    }

    public void setValueForSelection(String occsaion){

        if(occsaion.equalsIgnoreCase("valentines Day")){
            spinner.setSelection(0);

        }
        else if(occsaion.equalsIgnoreCase("birthdays")){
            spinner.setSelection(1);

        }
        else if(occsaion.equalsIgnoreCase("Promotions")){
            spinner.setSelection(2);

        }
        else if(occsaion.equalsIgnoreCase("Parties")){
            spinner.setSelection(3);

        }
        else if(occsaion.equalsIgnoreCase("Bridal Shower")){
            spinner.setSelection(4);

        }
        else if(occsaion.equalsIgnoreCase("Weddings")){
            spinner.setSelection(5);

        }
        else if(occsaion.equalsIgnoreCase("Other")){
            spinner.setSelection(6);

        }


    }



    private void validateProductData() {

        prod_name = name.getText().toString().trim();
        prod_price = price.getText().toString().trim();
        prod_description = description.getText().toString().trim();
        prod_event = spinner.getSelectedItem().toString();
        prod_availability = spinner2.getSelectedItem().toString().trim();

        if(ImageUri == null){
            Toast.makeText(getApplicationContext(),"Please add the product image",Toast.LENGTH_LONG).show();
        }
        else if(TextUtils.isEmpty(prod_name)){
            Toast.makeText(getApplicationContext(),"Please enter product name",Toast.LENGTH_LONG).show();

        }
        else if(TextUtils.isEmpty(prod_price)){
            Toast.makeText(getApplicationContext(),"Please enter product price",Toast.LENGTH_LONG).show();

        }
        else if(TextUtils.isEmpty(prod_description)){
            Toast.makeText(getApplicationContext(),"Please enter product description",Toast.LENGTH_LONG).show();

        }
        else{

            if(value == 1) {
                StoreProductInfo();
            }
            else{
                SaveProductInfoToDatabase();
            }

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

                        image = filePath.getDownloadUrl().toString();
                        return filePath.getDownloadUrl();

                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if(task.isSuccessful()){

                            image = task.getResult().toString();
                            Toast.makeText(getApplicationContext(),"Product Image Updated Successfully",Toast.LENGTH_LONG).show();

                            SaveProductInfoToDatabase();
                        }

                    }
                });
            }
        });

    }

    private void SaveProductInfoToDatabase() {


        product = new Products();
        product.setProduct_name(prod_name);
        product.setProduct_id(pid);
        product.setProduct_description(prod_description);
        product.setProduct_price(prod_price);
        product.setProduct_event(prod_event);
        product.setProduct_image(image);
        product.setProduct_availability(prod_availability);


        dataReff.child(pid).setValue(product).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if(task.isSuccessful()){

                    Intent i = new Intent(Admin_edit_product.this,Admin_products.class);
                    startActivity(i);
                    loadingBar.dismiss();

                    Toast.makeText(getApplicationContext(),"Product  updated successfully",Toast.LENGTH_LONG).show();

                }
                else{
                    loadingBar.dismiss();

                    String msg = task.getException().toString();
                    Toast.makeText(getApplicationContext(),"Error" + msg,Toast.LENGTH_LONG).show();



                }
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
            im.setImageURI(ImageUri);
        }
    }
}