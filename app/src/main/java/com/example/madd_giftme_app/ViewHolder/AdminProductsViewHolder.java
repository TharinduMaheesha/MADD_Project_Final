package com.example.madd_giftme_app.ViewHolder;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.madd_giftme_app.R;
import com.example.madd_giftme_app.Service.ItemClickListener;

public class AdminProductsViewHolder extends RecyclerView.ViewHolder implements  View.OnClickListener{

    public Button edit , remove;
    public TextView prod_name , event , availability;
    public ImageView image;
    private ItemClickListener listener;

    public AdminProductsViewHolder(@NonNull View itemView) {
        super(itemView);

        edit = itemView.findViewById(R.id.btn_admin_edit);
        remove = itemView.findViewById(R.id.btn_admin_remove);
        prod_name = itemView.findViewById(R.id.TV_ADMIN_PRODUCT_NAME);
        event = itemView.findViewById(R.id.TV_ADMIN_PRODUCT_EVENT);
        image = itemView.findViewById(R.id.IV_ADMIN_PRODUCT_IMAGE);
        availability = itemView.findViewById(R.id.TV_ADMIN_AVAILABILITY);


    }
    public void setItemClickListener(ItemClickListener listener){
        this.listener = listener;
    }


    @Override
    public void onClick(View view) {

    }
}
