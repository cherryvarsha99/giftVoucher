package com.example.project.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.project.R;
import com.example.project.fragments.Gift;
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

import java.util.List;

public class GiftAdapter extends RecyclerView.Adapter<GiftAdapter.MyViewHolder> {
    private final Context mContext;
    private LayoutInflater layoutInflater;
    private final List<Gift> giftCards;

    public GiftAdapter(Context mContext, List<Gift> giftCards) {
        this.mContext = mContext;
        this.giftCards = giftCards;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        View view = layoutInflater.inflate(R.layout.activity_gift_card__item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder myViewHolder, final int position) {
        Gift giftCardDto = giftCards.get(position);
        Picasso.get()
                .load("https://3.imimg.com/data3/JB/BT/MY-7088265/500-500x500.jpg")
                .placeholder(R.drawable.gift_pic)
                .into(myViewHolder.giftImageView);
        myViewHolder.textViewGiftCode.setText("Code: "+giftCardDto.getCode());
        myViewHolder.textViewGiftAmount.setText("Amount: $"+ giftCardDto.getAmount());
        myViewHolder.textViewGiftRedeemed.setText("Redeemed: "+ giftCardDto.getRedeemed());
    }

    @Override
    public int getItemCount() {
        return giftCards.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        RoundedImageView giftImageView;
        TextView textViewGiftCode;
        TextView textViewGiftAmount;
        TextView textViewGiftRedeemed;
        public MyViewHolder(View view) {
            super(view);
            giftImageView = view.findViewById(R.id.imgGiftCard);
            textViewGiftCode =view.findViewById(R.id.tvGiftCode);
            textViewGiftAmount=view.findViewById(R.id.tvAmount);
            textViewGiftRedeemed=view.findViewById(R.id.tvRedeemStatus);
        }
    }

}