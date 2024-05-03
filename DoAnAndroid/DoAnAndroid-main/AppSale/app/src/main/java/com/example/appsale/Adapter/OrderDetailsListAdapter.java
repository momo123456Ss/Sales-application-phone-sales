package com.example.appsale.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appsale.ObjectClass.OrderDetails;
import com.example.appsale.R;

import java.util.ArrayList;

public class OrderDetailsListAdapter extends RecyclerView.Adapter<OrderDetailsListAdapter.Viewholder> {
    ArrayList<OrderDetails> items;
    Context context;

    public OrderDetailsListAdapter(ArrayList<OrderDetails> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public OrderDetailsListAdapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_orderdetails_list, parent, false);
        context = parent.getContext();
        return new Viewholder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderDetailsListAdapter.Viewholder holder, int position) {

//        Glide.with(context).load(items.get(position).getImage())
//                .transform(new GranularRoundedCorners(30,30,0,0))
//                .into(holder.imgProduct);
        holder.txtProduct.setText(items.get(position).getProductName());
        holder.txtSoLuong.setText(String.valueOf(items.get(position).getQuanity()));
        holder.txtTotalPrice.setText(String.valueOf(items.get(position).getTotal()));

        holder.itemView.setOnClickListener(v -> {
//            Intent intent = new Intent(holder.itemView.getContext(), DetailActivity.class);
//            intent.putExtra("object",items.get(position));
//            holder.itemView.getContext().startActivity(intent);
//            Toast.makeText(context, "HELLO", Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder{
        TextView txtProduct,txtTotalPrice,txtSoLuong;
        public Viewholder(@NonNull View itemView) {
            super(itemView);
            txtProduct = itemView.findViewById(R.id.txtProduct);
            txtTotalPrice = itemView.findViewById(R.id.txtTotalPrice);
            txtSoLuong = itemView.findViewById(R.id.txtSoLuong);

        }
    }
}
