package com.example.madd_giftme_app.ViewHolder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.madd_giftme_app.R;

public class OrderItemsViewHolder extends RecyclerView.ViewHolder{

    public TextView text1 , text2;
    public OrderItemsViewHolder(@NonNull View itemView) {
        super(itemView);

        text1=itemView.findViewById(R.id.test1);
        text2=itemView.findViewById(R.id.test2);

    }
}
