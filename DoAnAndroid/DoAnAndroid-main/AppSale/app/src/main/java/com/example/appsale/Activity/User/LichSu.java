package com.example.appsale.Activity.User;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.appsale.Activity.ProfileActivity;
import com.example.appsale.Adapter.OrderListAdapter;
import com.example.appsale.ObjectClass.Order;
import com.example.appsale.ObjectClass.Server;
import com.example.appsale.ObjectClass.StaticClass;
import com.example.appsale.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class LichSu extends AppCompatActivity {
    private ImageView backBtn;
    private RecyclerView.Adapter  adapterOrder;
    private RecyclerView recyclerViewListOrder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lich_su);

        initView();
        initRecyclerviewListOrder();
        backBtn.setOnClickListener(v -> startActivity(new Intent(LichSu.this, ProfileActivity.class)));
    }

    private void initView() {
        backBtn = findViewById(R.id.backBtn);
    }


    private void initRecyclerviewListOrder() {
        ArrayList<Order> items = new ArrayList<>();
        StringRequest sReq = new StringRequest(Request.Method.GET
                , Server.getAllOrderOfUserByUserId + StaticClass.user.getId()
                , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray array = new JSONArray(response);
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject orderObject = array.getJSONObject(i);
                        Order order = new Order(
                                orderObject.getInt("order_id"),
                                orderObject.getLong("total_price"),
                                orderObject.getString("created_date")
                        );
                        items.add(order);
                    }
                    recyclerViewListOrder = findViewById(R.id.lichSuMuaHang);
                    recyclerViewListOrder.setLayoutManager(new GridLayoutManager(LichSu.this, 1));
                    adapterOrder = new OrderListAdapter(items);
                    recyclerViewListOrder.setAdapter(adapterOrder);
                } catch (
                        JSONException e) {
                    Log.w("Load manufacturer", "Error: " + e.getMessage());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(sReq);
    }
}