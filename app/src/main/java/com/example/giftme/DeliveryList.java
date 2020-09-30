/*N.I.T.Hewage_IT19220116
 * MADD mini project 2020_Y2S2_GIFT_ODERIN_GAPP
 * Delivery managment
 * */
package com.example.giftme;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import androidx.annotation.NonNull;

import com.example.giftme.R;
import com.example.giftme.testClass;

import java.util.List;

public class DeliveryList extends ArrayAdapter<testClass> {
    private Activity context;
    private List<testClass> deliveryList;

    public DeliveryList( Activity context,List<testClass> deliveryList) {
        super(context, R.layout.delivery_list_layout,deliveryList);
        this.context=context;
        this.deliveryList=deliveryList;
    }
    @NonNull
    @Override
    public View getView(int position, View convertView,ViewGroup parent) {
        LayoutInflater inflater =context.getLayoutInflater();
        View deliveryviewitem =inflater.inflate(R.layout.delivery_list_layout,null,true);

        TextView textViewName =(TextView)deliveryviewitem.findViewById(R.id.name);
        TextView textViewAddress=(TextView)deliveryviewitem.findViewById(R.id.address);

        testClass delivery = deliveryList.get(position);

        textViewName.setText(delivery.getName());
        textViewAddress.setText(delivery.getAddress());

        return deliveryviewitem;

    }
}
