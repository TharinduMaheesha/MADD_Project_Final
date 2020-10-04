package com.example.madd_giftme_app.ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.madd_giftme_app.R;
import com.example.madd_giftme_app.R;
import com.example.madd_giftme_app.Service.ItemClickListener;

public class OrderProductsViewHolder extends RecyclerView.ViewHolder implements RecyclerView.OnClickListener {

    public TextView txtOrderProductName, txtOrderProductPrice, txtOrderProductQuantity;
    private ItemClickListener itemClickListener ;
    public ImageView OrderProductImage ;
    public RelativeLayout OrdersProductsProduct ;


    public OrderProductsViewHolder(@NonNull View itemView) {
        super(itemView);

        txtOrderProductName = itemView.findViewById(R.id.order_product_name);
        txtOrderProductPrice = itemView.findViewById(R.id.order_product_price);
        txtOrderProductQuantity = itemView.findViewById(R.id.order_product_quantity);
        OrderProductImage = itemView.findViewById(R.id.order_products_image);
        OrdersProductsProduct = itemView.findViewById(R.id.order_products_product);

    }

    @Override
    public void onClick(View view) {

        itemClickListener.onClick(view, getAdapterPosition(),false);

    }

    public void setItemClickListener(ItemClickListener itemClickListener) {

        this.itemClickListener = itemClickListener;
    }

}
