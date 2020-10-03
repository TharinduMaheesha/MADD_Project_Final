package com.example.madd_giftme_app.ViewHolder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.madd_giftme_app.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class RiderVewHolder extends RecyclerView.ViewHolder{

    public TextView riderid , ridername , plate , vehicle;
    public CardView card;
    public CircleImageView image;



    public RiderVewHolder(@NonNull View itemView) {
        super(itemView);

        card = itemView.findViewById(R.id.CARDRIDERVIEW);
        riderid = itemView.findViewById(R.id.TVRIDERID);
        plate = itemView.findViewById(R.id.TVRIDERPLATENUMBER);
        ridername = itemView.findViewById(R.id.TVRIDRNAME);
        image = itemView.findViewById(R.id.IVRIDERIMAGE);
        vehicle=itemView.findViewById(R.id.TVRIDERVEHICLETYPE);
    }
}
