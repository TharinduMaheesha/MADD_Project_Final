package com.example.madd_giftme_app.ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.madd_giftme_app.R;

public class OngoingDeliveryViewHolder extends RecyclerView.ViewHolder {

    public TextView oid , did , rname , uname , status;
    public CardView card;
    public ImageView delivered , shipped;


    public OngoingDeliveryViewHolder(@NonNull View itemView) {
        super(itemView);

        oid = itemView.findViewById(R.id.DEL_ORDERID);
        did = itemView.findViewById(R.id.DEL_DELID);
        rname = itemView.findViewById(R.id.DEL_RIDERNAME);
        uname = itemView.findViewById(R.id.DEL_CUSTOMERNAME);
        status = itemView.findViewById(R.id.DEL_STATUS);
        card = itemView.findViewById(R.id.DEL_CARD);

        shipped = itemView.findViewById(R.id.IVSGIPPEDSTAMP);

    }
}
