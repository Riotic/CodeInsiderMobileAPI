package com.codeinsider.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.codeinsider.Holder.EtudiantViewHolder;
import com.codeinsider.Interface.EtudiantSelectListener;
import com.codeinsider.Model.Etudiant;
import com.codeinsider.R;

import java.util.List;

public class EtudiantAdapter extends RecyclerView.Adapter<EtudiantViewHolder> {
    private Context context;
    private List<Etudiant> list;
    private EtudiantSelectListener listener;

    public EtudiantAdapter(Context context, List<Etudiant> list, EtudiantSelectListener listener) {
        this.context = context;
        this.list = list;
        this.listener = listener;
    }

    @NonNull
    @Override
    public EtudiantViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new EtudiantViewHolder(LayoutInflater.from(context).inflate(R.layout.card_etudiant, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull EtudiantViewHolder holder, int position) {
        holder.etudiantCardName.setText(list.get(position).getName());
        holder.etudiantCardSkill.setText(list.get(position).getSkills());
        holder.etudiantCardContract.setText(list.get(position).getContract_type());

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemEtudiantClicked(list.get(holder.getAdapterPosition()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void filterList(List<Etudiant> filteredList) {
        list = filteredList;
        notifyDataSetChanged();
    }
}
