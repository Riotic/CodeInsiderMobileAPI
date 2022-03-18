package com.codeinsider.Holder;

import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.codeinsider.R;

public class PostViewHolder extends RecyclerView.ViewHolder {
    public TextView cardPostName, cardPostDesc, cardPostJobtype;
    public CardView cardView;
    public ImageButton cardPostLikebtn, cardPostEditbtn, cardPostDeletebtn;

    public PostViewHolder(@NonNull View itemView) {
        super(itemView);
        cardView = itemView.findViewById(R.id.company_card_container);
        cardPostName = itemView.findViewById(R.id.CardPostCardName);
        cardPostDesc = itemView.findViewById(R.id.CardPostCardDesc);
        cardPostJobtype = itemView.findViewById(R.id.CardPostJobtype);
        cardPostLikebtn = itemView.findViewById(R.id.CardPostHeartbtn);
        cardPostEditbtn = itemView.findViewById(R.id.CardPostEditbtn);
        cardPostDeletebtn = itemView.findViewById(R.id.CardPostDeletebtn);
    }
}
