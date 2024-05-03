package com.example.appsale.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.GranularRoundedCorners;
import com.example.appsale.Activity.DetailActivity;
import com.example.appsale.ObjectClass.Product;
import com.example.appsale.ObjectClass.StaticClass;
import com.example.appsale.R;

import java.util.ArrayList;

public class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.Viewholder> {
ArrayList<Product> items;
    Context context;

    public ProductListAdapter(ArrayList<Product> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public ProductListAdapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_pop_list, parent, false);
        context = parent.getContext();
        return new ProductListAdapter.Viewholder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductListAdapter.Viewholder holder, int position) {
        holder.tvNameProduct.setText(items.get(position).getName());
        holder.tvPriceProduct.setText(items.get(position).getPrice().toString() +"VNĐ");
        holder.tvStar.setText(items.get(position).getAverageRating().toString());
        holder.tvNumComment.setText(String.valueOf(items.get(position).getNumComment()));
        Glide.with(context).load(items.get(position).getImage())
                .transform(new GranularRoundedCorners(30,30,0,0))
                .into(holder.imgProduct);
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(holder.itemView.getContext(), DetailActivity.class);
            intent.putExtra("object",items.get(position));
            intent.putExtra("kind", "edit");
            holder.itemView.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder{
        TextView tvNameProduct, tvPriceProduct, tvStar, tvNumComment;
        ImageView imgProduct;

        public Viewholder(@NonNull View itemView) {
            super(itemView);
            tvNumComment = itemView.findViewById(R.id.tvNumComment);
            tvNameProduct = itemView.findViewById(R.id.tvNameProduct);
            tvPriceProduct = itemView.findViewById(R.id.tvPriceProduct);
            tvStar = itemView.findViewById(R.id.tvStar);
            imgProduct = itemView.findViewById(R.id.imgProduct);
        }
    }
}
