package com.example.madd_giftme_app.ViewHolder;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.madd_giftme_app.R;

public class PaymentsViewHolder extends RecyclerView.ViewHolder  {

    public TextView payid , mail , total , oid , date;
    public Button btn;
    public PaymentsViewHolder(@NonNull View itemView) {
        super(itemView);

        payid=itemView.findViewById(R.id.TV_PAYID);
        mail=itemView.findViewById(R.id.TV_UID);
        total=itemView.findViewById(R.id.TV_AMOUNT);
        oid=itemView.findViewById(R.id.TV_OID);
        date=itemView.findViewById(R.id.TVDATEPAY);
        btn=itemView.findViewById(R.id.BTN_PAY_VIEW);
    }
}
