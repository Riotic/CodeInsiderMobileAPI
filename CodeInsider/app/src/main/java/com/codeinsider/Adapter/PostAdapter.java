package com.codeinsider.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.codeinsider.Holder.PostViewHolder;
import com.codeinsider.Interface.PostSelectListener;
import com.codeinsider.Model.Post;
import com.codeinsider.R;

import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostViewHolder>{
    private Context context;
    private List<Post> list;
    private PostSelectListener listener;
    private String user_type;

    public PostAdapter(Context context, List<Post> list, PostSelectListener listener, String user_type) {
        this.context = context;
        this.list = list;
        this.listener = listener;
        this.user_type = user_type;
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PostViewHolder(LayoutInflater.from(context).inflate(R.layout.card_post, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {
        holder.cardPostName.setText(list.get(position).getJob_name());
        holder.cardPostDesc.setText(list.get(position).getDescription());
        holder.cardPostJobtype.setText(list.get(position).getContract_type());

        if(user_type.equals("etudiant")){
            holder.cardPostDeletebtn.setVisibility(View.GONE);
            holder.cardPostEditbtn.setVisibility(View.GONE);
            holder.cardPostLikebtn.setVisibility(View.VISIBLE);
        }
        else if(user_type.equals("entreprise")){
            holder.cardPostDeletebtn.setVisibility(View.VISIBLE);
            holder.cardPostEditbtn.setVisibility(View.VISIBLE);
            holder.cardPostLikebtn.setVisibility(View.GONE);
        }

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemPostClicked(list.get(holder.getAdapterPosition()));
            }
        });

        holder.cardPostLikebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO LIKE
            }
        });

        holder.cardPostEditbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemEditClicked(list.get(holder.getAdapterPosition()));
            }
        });

        holder.cardPostDeletebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemDeleteClicked(list.get(holder.getAdapterPosition()));
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void filterList(List<Post> filteredList) {
        list = filteredList;
        notifyDataSetChanged();
    }
}
