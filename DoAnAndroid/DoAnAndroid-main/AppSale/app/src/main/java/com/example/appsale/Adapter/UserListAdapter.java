package com.example.appsale.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appsale.Admin.User.ActiveUser;
import com.example.appsale.ObjectClass.StaticClass;
import com.example.appsale.ObjectClass.User;
import com.example.appsale.R;

import java.util.ArrayList;
import java.util.List;

public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.Viewholder> {
    ArrayList<User> items;
    Context context;
    public UserListAdapter(@NonNull Context context, List<User> manufacturerList) {
        super();
        this.items = (ArrayList<User>) manufacturerList;
        this.context = context;
    }

    public UserListAdapter(ArrayList<User> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_user_list, parent, false);
        context = parent.getContext();
        return new Viewholder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
        holder.textUserId.setText(String.valueOf(items.get(position).getId()));
        holder.textName.setText(items.get(position).getLastName());
        holder.textUsername.setText(items.get(position).getUsername());
        holder.textEmail.setText(items.get(position).getEmail());
        holder.textRole.setText(items.get(position).getRoleName());
        if (items.get(position).isActive() == true){
            holder.txtLock.setVisibility(View.GONE);
        }else {
            holder.txtLock.setVisibility(View.VISIBLE);
        }

        holder.itemView.setOnClickListener(v -> {
            StaticClass.userEdit = items.get(position);
            holder.itemView.getContext().startActivity(new Intent(holder.itemView.getContext(),
                    ActiveUser.class));
        });

    }


    @Override
    public int getItemCount() {
        return items.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {


        TextView textUserId, textName, textUsername, textEmail, textRole, txtLock;
        public Viewholder(@NonNull View itemView) {
            super(itemView);
            textUserId = itemView.findViewById(R.id.textUserId);
            textName = itemView.findViewById(R.id.textName);
            textUsername = itemView.findViewById(R.id.textUsername);
            textEmail = itemView.findViewById(R.id.textEmail);
            textRole = itemView.findViewById(R.id.textRole);
            txtLock = itemView.findViewById(R.id.txtLock);
        }
    }
}
