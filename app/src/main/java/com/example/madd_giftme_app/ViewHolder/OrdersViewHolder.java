package com.example.madd_giftme_app.ViewHolder;

import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.madd_giftme_app.R;


public class OrdersViewHolder extends RecyclerView.ViewHolder {

    public TextView userName, userAddress, userPhone, totalPrice, dateTime, message ;
    public RelativeLayout showOrdersProducts ;

    public OrdersViewHolder(@NonNull View itemView) {
        super(itemView);

        userName = itemView.findViewById(R.id.order_user_name);
        userAddress = itemView.findViewById(R.id.order_user_address);
        userPhone = itemView.findViewById(R.id.order_phone);
        totalPrice = itemView.findViewById(R.id.order_total_price);
        dateTime = itemView.findViewById(R.id.order_date_time);
        showOrdersProducts = itemView.findViewById(R.id.display_orders_list);
        message = itemView.findViewById(R.id.order_payment_status);

    }
}
