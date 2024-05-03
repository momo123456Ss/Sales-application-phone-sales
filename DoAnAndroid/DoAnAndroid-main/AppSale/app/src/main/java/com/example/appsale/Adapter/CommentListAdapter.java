package com.example.appsale.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appsale.ObjectClass.Comment;
import com.example.appsale.R;

import java.util.ArrayList;

public class CommentListAdapter extends RecyclerView.Adapter<CommentListAdapter.Viewholder> {
    ArrayList<Comment> items;
    Context context;

    public CommentListAdapter(ArrayList<Comment> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public CommentListAdapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_binhluansp, parent, false);
        context = parent.getContext();
        return new Viewholder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentListAdapter.Viewholder holder, int position) {
        holder.txtUsername.setText(items.get(position).getUsername()+": ");
        holder.txtContent.setText(items.get(position).getContent());
        holder.txtStar.setText("Rating: "+ items.get(position).getStar());


        holder.itemView.setOnClickListener(v -> {
            Toast.makeText(context, "HELLO", Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder{
        TextView txtContent, txtUsername, txtStar;

        public Viewholder(@NonNull View itemView) {
            super(itemView);

            txtContent = itemView.findViewById(R.id.txtContent);
            txtUsername = itemView.findViewById(R.id.txtUsername);
            txtStar = itemView.findViewById(R.id.txtStar);
        }
    }
}
