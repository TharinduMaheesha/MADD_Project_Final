package com.example.madd_giftme_app.ViewHolder;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.madd_giftme_app.R;
import com.example.madd_giftme_app.Service.ItemClickListener;


public class OccasionProcductViewHolder extends RecyclerView.ViewHolder implements  View.OnClickListener {


    public TextView name , description , price;
    public ImageView image;
    public ItemClickListener listener;
    public RelativeLayout relativeLayout;
    public CardView card;

    public Button btn1 , btn2 , btn3;
    public OccasionProcductViewHolder(@NonNull View itemView) {
        super(itemView);

        name = (TextView) itemView.findViewById(
                R.id.tv_product_name_customer);
        price = (TextView) itemView.findViewById(R.id.tv_product_price_customer);
        description = (TextView) itemView.findViewById(R.id.tv_product_description_customer);
        image = (ImageView) itemView.findViewById(R.id.iv_product_image_customer);
        relativeLayout = (RelativeLayout) itemView.findViewById(R.id.RVSOME);
        card = (CardView) itemView.findViewById(R.id.cv_1);




    }

    public void setItemClickListener(ItemClickListener listener){
        this.listener = listener;
    }

    @Override
    public void onClick(View view) {

        listener.onClick(view , getAdapterPosition() , false);
    }
}
