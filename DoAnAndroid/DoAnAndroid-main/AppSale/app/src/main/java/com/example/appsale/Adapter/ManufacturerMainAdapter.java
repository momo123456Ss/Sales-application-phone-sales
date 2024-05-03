package com.example.appsale.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.GranularRoundedCorners;
import com.example.appsale.Activity.MainActivity;
import com.example.appsale.ObjectClass.Manufacturer;
import com.example.appsale.R;

import java.util.ArrayList;
import java.util.List;

public class ManufacturerMainAdapter extends RecyclerView.Adapter<ManufacturerMainAdapter.Viewholder> {
    ArrayList<Manufacturer> items;
    Context context;
    public ManufacturerMainAdapter(@NonNull Context context, List<Manufacturer> manufacturerList) {
        super();
        this.items = (ArrayList<Manufacturer>) manufacturerList;
        this.context = context;
    }
    public ManufacturerMainAdapter(ArrayList<Manufacturer> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_manufacturer, parent, false);
        context = parent.getContext();
        return new ManufacturerMainAdapter.Viewholder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ManufacturerMainAdapter.Viewholder holder, int position) {
        Glide.with(context).load(items.get(position).getImage())
                .transform(new GranularRoundedCorners(0,0,0,0))
                .into(holder.imageManufacturer);
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(holder.itemView.getContext(), MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.putExtra("object", items.get(position));
            holder.itemView.getContext().startActivity(intent);
            Toast.makeText(context, items.get(position).getName(), Toast.LENGTH_SHORT).show();
        });
    }
    @Override
    public int getItemCount() {
        return items.size();
    }
    public class Viewholder extends RecyclerView.ViewHolder{
        ImageView imageManufacturer;

        public Viewholder(@NonNull View itemView) {
            super(itemView);
            imageManufacturer = itemView.findViewById(R.id.itemPic);
        }
    }
}
