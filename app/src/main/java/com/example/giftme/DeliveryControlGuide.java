/*N.I.T.Hewage_IT19220116
 * MADD mini project 2020_Y2S2_GIFT_ODERIN_GAPP
 * Delivery managment
 * */
package com.example.giftme;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

/*
* class for view a guide to the first time landed user
* */
public class DeliveryControlGuide extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guidehead2);
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