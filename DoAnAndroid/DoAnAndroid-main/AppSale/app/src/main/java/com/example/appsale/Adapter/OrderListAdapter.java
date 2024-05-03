package com.example.appsale.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appsale.Activity.Cart.OrderDetailsActivity;
import com.example.appsale.ObjectClass.Order;
import com.example.appsale.R;

import java.util.ArrayList;

public class OrderListAdapter extends RecyclerView.Adapter<OrderListAdapter.Viewholder> {
    ArrayList<Order> items;
    Context context;

    public OrderListAdapter(ArrayList<Order> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public OrderListAdapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_order_list, parent, false);
        context = parent.getContext();
        return new Viewholder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderListAdapter.Viewholder holder, int position) {

//        Glide.with(context).load(items.get(position).getImage())
//                .transform(new GranularRoundedCorners(30,30,0,0))
//                .into(holder.imgProduct);
        holder.txtOrderId.setText(String.valueOf(items.get(position).getId()));
        holder.txtCreatedDate.setText(items.get(position).getCreated_date());
        holder.txtTotalPrice.setText(String.valueOf(items.get(position).getTotalprice()));

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(holder.itemView.getContext(), OrderDetailsActivity.class);
            intent.putExtra("object",items.get(position));
            holder.itemView.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder{
        TextView txtOrderId,txtCreatedDate,txtTotalPrice;
        public Viewholder(@NonNull View itemView) {
            super(itemView);
            txtOrderId = itemView.findViewById(R.id.txtOrderId);
            txtCreatedDate = itemView.findViewById(R.id.txtCreatedDate);
            txtTotalPrice = itemView.findViewById(R.id.txtTotalPrice);

        }
    }
}
