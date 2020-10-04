package com.example.madd_giftme_app.ui.logout;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.madd_giftme_app.R;
import com.example.madd_giftme_app.StartingActivity;
import com.example.madd_giftme_app.ui.home.HomeFragment;

public class LogoutFragment extends Fragment {

    private LogoutViewModel llogoutViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        llogoutViewModel =
                ViewModelProviders.of(this).get(LogoutViewModel.class);
        View root = inflater.inflate(R.layout.fragment_logout, container, false);

        HomeFragment homeFragment = new HomeFragment();
        FragmentManager manager = getFragmentManager();
        manager.beginTransaction().replace(R.id.nav_host_fragment, homeFragment).commit();

        AlertDialog.Builder yes = new AlertDialog.Builder(getContext());
        yes.setMessage("Do you really want to logout?");

        yes.setNeutralButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                Intent intent = new Intent(getActivity(), StartingActivity.class);
                startActivity(intent);
                Toast.makeText(getContext(), "Thank you for joining with us!", Toast.LENGTH_SHORT).show();

            }
        });
        yes.show();

        return root;
    }

}
