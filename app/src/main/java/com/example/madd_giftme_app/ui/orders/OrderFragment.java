package com.example.madd_giftme_app.ui.orders;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

import com.example.madd_giftme_app.Adapter.NestedFragmentAdapter;
import com.example.madd_giftme_app.OrdersToPayFragment;
import com.example.madd_giftme_app.PaidOrdersFragment;
import com.example.madd_giftme_app.R;
import com.google.android.material.tabs.TabLayout;

public class OrderFragment extends Fragment {

    private OrderViewModel orderViewModel;
    View myFragment ;
    TabLayout tabLayout ;
    ViewPager viewPager ;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        orderViewModel =
                ViewModelProviders.of(this).get(OrderViewModel.class);
        myFragment = inflater.inflate(R.layout.fragment_orders, container, false);

        viewPager = myFragment.findViewById(R.id.viewPager_orders);
        tabLayout = myFragment.findViewById(R.id.tabLayout_orders);

        return myFragment;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        setUpViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    private void setUpViewPager(ViewPager viewPager) {

        NestedFragmentAdapter nestedFragmentAdapter = new NestedFragmentAdapter(getChildFragmentManager());
        nestedFragmentAdapter.addFragment(new OrdersToPayFragment(), "To pay");
        nestedFragmentAdapter.addFragment(new PaidOrdersFragment(), "Paid");

        viewPager.setAdapter(nestedFragmentAdapter);

    }
}
