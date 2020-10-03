package com.example.madd_giftme_app.ViewHolder;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.madd_giftme_app.R;
import com.example.madd_giftme_app.Service.ItemClickListener;

public class AdminOrderViewHolder extends RecyclerView.ViewHolder implements  View.OnClickListener{


    public CardView card;
    public TextView id , date , count , user;
    public ImageView added , notadded;
    public TextView status;

    private ItemClickListener listener;


    public AdminOrderViewHolder(@NonNull View itemView) {
        super(itemView);

        card = itemView.findViewById(R.id.CARD_NEW_ORDER_ADMIN);
        user = itemView.findViewById(R.id.TV_USER_NEW_ORDER_ADMIN);
        id = itemView.findViewById(R.id.TV_ORDER_ID_NEW_ORDER);
        date = itemView.findViewById(R.id.TV_DATE_NEW_ORDER_ADMIN);
        count = itemView.findViewById(R.id.TV_Items_NEW_ORDER_ADMIN);
        notadded = itemView.findViewById(R.id.IV_NOTADDED);
        added = itemView.findViewById(R.id.IV_ADDED);
        status = itemView.findViewById(R.id.status);

    }

    public void setItemClickListener(ItemClickListener listener){
        this.listener = listener;
    }


    @Override
    public void onClick(View view) {

    }
}
