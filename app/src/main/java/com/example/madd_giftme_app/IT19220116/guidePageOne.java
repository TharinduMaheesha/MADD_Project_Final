/*N.I.T.Hewage_IT19220116
 * MADD mini project 2020_Y2S2_GIFT_ODERIN_GAPP
 * Delivery managment
 * */
package com.example.madd_giftme_app.IT19220116;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.madd_giftme_app.R;

/*
* this fragment is for rider control guide
* */
public class guidePageOne extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.guide_page_one, container, false);
    }
}