package com.example.appsale.Activity.Cart;

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
import com.example.appsale.Activity.User.LichSu;
import com.example.appsale.Adapter.OrderDetailsListAdapter;
import com.example.appsale.ObjectClass.Order;
import com.example.appsale.ObjectClass.OrderDetails;
import com.example.appsale.ObjectClass.Server;
import com.example.appsale.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class OrderDetailsActivity extends AppCompatActivity {
    private ImageView backBtn;
    private RecyclerView.Adapter  adapterOrderDetails;
    private RecyclerView recyclerViewListOrderDetails;
    private Order object;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);
        initView();
        initRecyclerviewListOrderDetails();
        backBtn.setOnClickListener(v -> startActivity(new Intent(OrderDetailsActivity.this, LichSu.class)));
    }
    private void initView() {
        backBtn = findViewById(R.id.backBtn);
    }
    private void initRecyclerviewListOrderDetails() {
        object = (Order) getIntent().getSerializableExtra("object");
        ArrayList<OrderDetails> items = new ArrayList<>();
        StringRequest sReq = new StringRequest(Request.Method.GET
                , Server.getAllProductInOrderById + object.getId()
                , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray array = new JSONArray(response);
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject orderDetailsObject = array.getJSONObject(i);
                        OrderDetails orderDetails = new OrderDetails(
                                orderDetailsObject.getString("product_name"),
                                orderDetailsObject.getInt("quantity"),
                                orderDetailsObject.getLong("total_price")
                        );
                        items.add(orderDetails);
                    }
                    recyclerViewListOrderDetails = findViewById(R.id.orderDetails);
                    recyclerViewListOrderDetails.setLayoutManager(new GridLayoutManager(OrderDetailsActivity.this, 1));
                    adapterOrderDetails = new OrderDetailsListAdapter(items);
                    recyclerViewListOrderDetails.setAdapter(adapterOrderDetails);
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