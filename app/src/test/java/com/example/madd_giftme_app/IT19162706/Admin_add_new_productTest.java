package com.example.madd_giftme_app.IT19162706;

import androidx.annotation.NonNull;

import com.example.madd_giftme_app.Model.Products;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class Admin_add_new_productTest {

    public static int maxID;
    public static Products product;
    public static int result;
    public static DatabaseReference ref;

    @BeforeClass
    public static void initMethod() {


        ref = FirebaseDatabase.getInstance().getReference().child("Products");

        maxID = 0;


        result = 0;
    }


    @Before
    public void setUp() throws Exception {
        product = new Products();
        maxID = 11002;
        result = 0;




    }

    @Test
    public void testingStoreData() {

        maxID = maxID + 1;
        String id = "PID" + maxID;

        product.setProduct_name("Test Product");
        product.setProduct_id(id);
        product.setProduct_description("Test Description");
        product.setProduct_price("Test Price");
        product.setProduct_event("Test Event");
        product.setProduct_image("\"https://firebasestorage.googleapis.com/v0/b/madd-51e6a.appspot.com/o/Product%20Images%2Fimage%3A72912IMG01.jpg?alt=media&token=97e15fb9-f474-4458-a6d0-42fd90270ca1\"");
        product.setProduct_availability("Testing availability");

        ref.child(id).setValue(product).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if (task.isSuccessful()) {

                    result = 1;
                } else {

                    result = -1;

                }

            }
        });

        assertEquals(1,result);

    }

    @After
    public void tearDown() throws Exception {

        product = null;
    }



    @AfterClass
    public static void FiniMethod(){

        product = null;
    }
}