package com.example.madd_giftme_app.ViewHolder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.madd_giftme_app.R;

public class RefundViewHolder extends RecyclerView.ViewHolder  {

    public TextView oid , mail;
    public CardView card;
    public RefundViewHolder(@NonNull View itemView) {
        super(itemView);

        oid = itemView.findViewById(R.id.refundoid);
        mail = itemView.findViewById(R.id.refundemail);
        card = itemView.findViewById(R.id.cardrefnd);
    }
}
