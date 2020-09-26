package com.example.madd_giftme_app.ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.madd_giftme_app.R;
import com.example.madd_giftme_app.Service.ItemClickListener;

public class ProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView txtProductName, txtProductDescription, txtProductPrice ;
    public ImageView cardImageView ;
    public ItemClickListener listener ;

    public ProductViewHolder(@NonNull View itemView) {
        super(itemView);

        cardImageView = (ImageView) itemView.findViewById(R.id.product_image_card);
        txtProductName = (TextView) itemView.findViewById(R.id.product_name_card);
        txtProductPrice = (TextView) itemView.findViewById(R.id.product_price_card);
        txtProductDescription = (TextView) itemView.findViewById(R.id.product_description_card);
    }

    public void setItemClickListener(ItemClickListener listener){

        this.listener = listener ;

    }

    @Override
    public void onClick(View view) {

        listener.onClick(view, getAdapterPosition(), false);

    }
}
