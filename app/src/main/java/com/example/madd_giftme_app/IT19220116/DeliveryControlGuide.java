/*N.I.T.Hewage_IT19220116
 * MADD mini project 2020_Y2S2_GIFT_ODERIN_GAPP
 * Delivery managment
 * */
package com.example.madd_giftme_app.IT19220116;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.example.madd_giftme_app.IT19220116.guidePageOne;
import com.example.madd_giftme_app.IT19220116.guidePageTwo;
import com.example.madd_giftme_app.R;

/*
* class for view a guide to the first time landed user
* */
public class DeliveryControlGuide extends AppCompatActivity {
    ImageButton home , account , products , orders;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guidehead2);


        home = findViewById(R.id.btnHome);
        account = findViewById(R.id.btnAccount);
        products = findViewById(R.id.btnProducts);
        orders = findViewById(R.id.btnOrders);


    }


    /*
    * changing two fragments
    * */
    public void changeFragment(View view){
        Fragment fragment;

        if (view == findViewById(R.id. buttonFragment1 ))
        {
            fragment = new guidePageOne();
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id. fragmentDefault ,fragment);
            ft.commit();
        }

        if (view == findViewById(R.id. buttonFragment2)){
            fragment = new guidePageTwo();
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id. fragmentDefault ,fragment);
            ft.commit();
        }

    }


}