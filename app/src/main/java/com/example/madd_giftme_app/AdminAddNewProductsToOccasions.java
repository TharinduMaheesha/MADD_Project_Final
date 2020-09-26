package com.example.madd_giftme_app;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class AdminAddNewProductsToOccasions extends AppCompatActivity {

    private String eventName, Name, Price, Count, Description, saveCurrentDate, getSaveCurrentTime;
    private Button addNewProduct ;
    private ImageView productImage ;
    private EditText productName, price, productCount, description ;
    private static final int GalleryPick = 1 ;
    private Uri ImageUri ;
    private String productRandomKey, downloadImageUrl ; ;
    private StorageReference ProductImagesRef ;
    private DatabaseReference productRef ;
    private ProgressDialog loadingBar ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_admin_add_new_products_to_occasions);

        Toast.makeText(this, "event is : ", Toast.LENGTH_SHORT).show();

        eventName = getIntent().getExtras().get("event").toString();
        Toast.makeText(this, "event is : " + eventName, Toast.LENGTH_SHORT).show();

        ProductImagesRef = FirebaseStorage.getInstance().getReference().child("Product images");
        productRef = FirebaseDatabase.getInstance().getReference().child("Products");

        addNewProduct = (Button) findViewById(R.id.add_admin_new_product);
        productImage = (ImageView) findViewById(R.id.select_admin_product_image);
        productName = (EditText) findViewById(R.id.select_admin_product_name);
        price = (EditText) findViewById(R.id.select_admin_product_price);
        productCount = (EditText) findViewById(R.id.select_admin_product_count);
        description = (EditText) findViewById(R.id.select_admin_product_description);
        loadingBar = new ProgressDialog(this);

        productImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                OpenGallery();

            }
        });

        addNewProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ValidateProductData();
            }
        });

    }

    private void OpenGallery() {

        Intent galleryIntent = new Intent();
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent, GalleryPick);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==GalleryPick && resultCode==RESULT_OK && data!=null){

            ImageUri = data.getData();
            productImage.setImageURI(ImageUri);

        }
        else
            Toast.makeText(this, "No image url", Toast.LENGTH_SHORT).show();

    }

    private void ValidateProductData(){

        Name = productName.getText().toString();
        Price = price.getText().toString();
        Count = productCount.getText().toString();
        Description = description.getText().toString();

        if(ImageUri == null)
            Toast.makeText(this, "Product image is required!", Toast.LENGTH_SHORT).show();

        else if(TextUtils.isEmpty(Name))
            Toast.makeText(this, "Product name is required", Toast.LENGTH_SHORT).show();

        else if(TextUtils.isEmpty(Price))
            Toast.makeText(this, "Product price is required", Toast.LENGTH_SHORT).show();

        else if(TextUtils.isEmpty(Count))
            Toast.makeText(this, "No of products is required", Toast.LENGTH_SHORT).show();

        else if(TextUtils.isEmpty(Description))
            Toast.makeText(this, "Product description is required", Toast.LENGTH_SHORT).show();

        else
            StoreProductInformation();
    }

    private void StoreProductInformation() {

        loadingBar.setTitle("Add new product");
        loadingBar.setMessage("Please wait, while we are adding the new product.");
        loadingBar.setCanceledOnTouchOutside(false);
        loadingBar.show();

        Calendar calendar = Calendar.getInstance();

        SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd, yyy");
        saveCurrentDate = currentDate.format(calendar.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        getSaveCurrentTime = currentTime.format(calendar.getTime());

        productRandomKey = saveCurrentDate + getSaveCurrentTime ;

        final StorageReference filePath = ProductImagesRef.child(ImageUri.getLastPathSegment() + productRandomKey + ".jpg") ;

        final UploadTask uploadTask = filePath.putFile(ImageUri);

        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                String message = e.toString();
                Toast.makeText(AdminAddNewProductsToOccasions.this, "Error: "+ message, Toast.LENGTH_SHORT).show();
                loadingBar.dismiss();

            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                Toast.makeText(AdminAddNewProductsToOccasions.this, "Product image uploaded successfully!", Toast.LENGTH_SHORT).show();

                Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {

                        if(!task.isSuccessful()){

                            throw  task.getException();

                        }

                        downloadImageUrl = filePath.getDownloadUrl().toString();
                        return filePath.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {

                        if(task.isSuccessful()){

                            Toast.makeText(AdminAddNewProductsToOccasions.this, "Got the product image url successfully..", Toast.LENGTH_SHORT).show();

                            SaveProductInfoToDatabase() ;

                        }
                    }
                });
            }
        });
    }

    private void SaveProductInfoToDatabase() {

        HashMap<String, Object> productMap = new HashMap<>();
        productMap.put("pid", productRandomKey);
        productMap.put("date", saveCurrentDate);
        productMap.put("time", getSaveCurrentTime);
        productMap.put("name", Name);
        productMap.put("image", downloadImageUrl);
        productMap.put("event", eventName);
        productMap.put("price", Price);
        productMap.put("count", Count);
        productMap.put("description", Description);

        productRef.child(productRandomKey).updateChildren(productMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if(task.isSuccessful()){

                    Intent intent = new Intent(AdminAddNewProductsToOccasions.this, AdminCategory.class);
                    startActivity(intent);
                    loadingBar.dismiss();
                    Toast.makeText(AdminAddNewProductsToOccasions.this, "Product is added successfully..", Toast.LENGTH_SHORT).show();

                }
                else{

                    loadingBar.dismiss();
                    String message = task.getException().toString();
                    Toast.makeText(AdminAddNewProductsToOccasions.this, "Error: " + message, Toast.LENGTH_SHORT).show();

                }
            }
        });
    }

}