package com.example.madd_giftme_app.ui.occasions;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.madd_giftme_app.BlankFragment;
import com.example.madd_giftme_app.Customer_Occassion_products;
import com.example.madd_giftme_app.R;

public class OccasionsFragment extends Fragment {


    Button occasion1 , occasion2,occasion3 , occasion4 , occasion5 , occasion6 , other;
    String event;

    private OccasionsViewModel occasionsViewModel;

    public View onCreateView(@NonNull final LayoutInflater inflater,
                             final ViewGroup container, final Bundle savedInstanceState) {
        occasionsViewModel =
                ViewModelProviders.of(this).get(OccasionsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_occasions, container, false);
        //final TextView textView = root.findViewById(R.id.text_occasions);
        occasionsViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
              //  textView.setText(s);
            }
        });

        occasion1 = root.findViewById(R.id.btn_cus_Valentines);
        occasion2 = root.findViewById(R.id.btn_cus_Birthday);
        occasion3 = root.findViewById(R.id.btn_cus_Wedding);
        occasion4 = root.findViewById(R.id.btn_cus_Promotions);
        occasion5 = root.findViewById(R.id.btn_cus_Party);
        occasion6 = root.findViewById(R.id.btn_cus_Bridal);
        other = root.findViewById(R.id.btn_cus_Other);

        occasion1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                BlankFragment frag = new BlankFragment();
                Bundle bundle = new Bundle();
                bundle.putString("event" , "valentines Day");
                frag.setArguments(bundle);
                FragmentManager manager = getFragmentManager();
                manager.beginTransaction().replace(R.id.nav_host_fragment,frag).commit();


            }
        });
        occasion2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                BlankFragment frag = new BlankFragment();
                Bundle bundle = new Bundle();
                bundle.putString("event" , "birthdays");
                frag.setArguments(bundle);

                FragmentManager manager = getFragmentManager();
                manager.beginTransaction().replace(R.id.nav_host_fragment,frag).commit();


            }
        });
        occasion3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                BlankFragment frag = new BlankFragment();
                Bundle bundle = new Bundle();
                bundle.putString("event" , "Weddings");
                frag.setArguments(bundle);

                FragmentManager manager = getFragmentManager();
                manager.beginTransaction().replace(R.id.nav_host_fragment,frag).commit();


            }
        });
        occasion4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                BlankFragment frag = new BlankFragment();
                Bundle bundle = new Bundle();
                bundle.putString("event" , "promotions");
                frag.setArguments(bundle);

                FragmentManager manager = getFragmentManager();
                manager.beginTransaction().replace(R.id.nav_host_fragment,frag).commit();


            }
        });
        occasion5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                BlankFragment frag = new BlankFragment();
                Bundle bundle = new Bundle();
                bundle.putString("event" , "parties");
                frag.setArguments(bundle);

                FragmentManager manager = getFragmentManager();
                manager.beginTransaction().replace(R.id.nav_host_fragment,frag).commit();


            }
        });
        occasion6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                BlankFragment frag = new BlankFragment();
                Bundle bundle = new Bundle();
                bundle.putString("event" , "Bridal Shower");
                frag.setArguments(bundle);

                FragmentManager manager = getFragmentManager();
                manager.beginTransaction().replace(R.id.nav_host_fragment,frag).commit();


            }
        });
        other.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                BlankFragment frag = new BlankFragment();
                Bundle bundle = new Bundle();
                bundle.putString("event" , "Other");
                frag.setArguments(bundle);

                FragmentManager manager = getFragmentManager();
                manager.beginTransaction().replace(R.id.nav_host_fragment,frag).commit();


            }
        });

        return root;

    }

    private void something(String event) {

        Intent intent = new Intent(getContext(), Customer_Occassion_products.class);
        intent.putExtra("event" , event);
        startActivity(intent);


    }


}