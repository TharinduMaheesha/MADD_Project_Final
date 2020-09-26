package com.example.giftme;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class ListAdapter extends ArrayAdapter {

    private Activity mContext;
    List<AddRider> riderList;

    public ListAdapter(Activity mContext,List<AddRider>riderList) {
        super(mContext,R.layout.list_item,riderList);
        this.mContext =mContext;
        this.riderList =riderList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater=mContext.getLayoutInflater();
        View listItemView =inflater.inflate(R.layout.list_item,null,true);

        TextView name= listItemView.findViewById(R.id.Rname);
        TextView phone= listItemView.findViewById(R.id.Rphone);
        TextView vehicle=listItemView.findViewById(R.id.Rvehicle);

        AddRider addRider =riderList.get(position);

        name.setText(addRider.getName());
        phone.setText(addRider.getPhone());
        vehicle.setText(addRider.getBike());

        return listItemView;
    }
}
