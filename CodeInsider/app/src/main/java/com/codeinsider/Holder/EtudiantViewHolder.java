package com.codeinsider.Holder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.codeinsider.R;

public class EtudiantViewHolder extends RecyclerView.ViewHolder {

    public TextView etudiantCardName, etudiantCardContract, etudiantCardSkill;
    public CardView cardView;

    public EtudiantViewHolder(@NonNull View itemView) {
        super(itemView);
        cardView = itemView.findViewById(R.id.etudiant_card_container);
        etudiantCardName = itemView.findViewById(R.id.StudentCardName);
        etudiantCardContract = itemView.findViewById(R.id.StudentCardContractType);
        etudiantCardSkill = itemView.findViewById(R.id.StudentCardSkill);
    }
}
