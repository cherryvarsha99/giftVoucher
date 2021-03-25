package com.example.project.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project.R;
import com.makeramen.roundedimageview.RoundedImageView;

public class GiftAdapter extends RecyclerView.Adapter<GiftAdapter.MyViewHolder>{

    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull GiftAdapter.MyViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
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
