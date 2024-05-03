package com.example.appsale.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.appsale.Activity.Cart.CartActivity;
import com.example.appsale.Adapter.ManufacturerMainAdapter;
import com.example.appsale.Adapter.ProductListAdapter;
import com.example.appsale.Activity.User.Login;
import com.example.appsale.ObjectClass.Manufacturer;
import com.example.appsale.ObjectClass.Product;
import com.example.appsale.ObjectClass.Server;
import com.example.appsale.ObjectClass.StaticClass;
import com.example.appsale.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    TextView tvUserName;
    private RecyclerView.Adapter adapterPopular,adapterLogoPhone;
    private RecyclerView recyclerViewPopular,recyclerViewListPhone;
    int visible = 0;
    ArrayList<Product> items = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvUserName = findViewById(R.id.tvUserName);
        checkLogin();
        searchOnClick();
        initRecyclerviewListManufacturer();
        initRecyclerview((Manufacturer) getIntent().getSerializableExtra("object"));
        bottom_navigation();
    }
    private void searchOnClick(){
        ImageView ic_search = findViewById(R.id.ic_search);
        EditText editSearch = findViewById(R.id.editSearch);
        ic_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editSearch.setVisibility(View.VISIBLE);
                visible ++;
                if(visible > 1){
                    visible = 0;
                    editSearch.setVisibility(View.GONE);
                }
            }
        });
    }
    private void bottom_navigation() {
        LinearLayout homeBtn = findViewById(R.id.homeBtn);
        LinearLayout cartBtn = findViewById(R.id.cartBtn);
        LinearLayout profileBtn = findViewById(R.id.profileBtn);
        homeBtn.setOnClickListener(v -> startActivity(new Intent(MainActivity.this,MainActivity.class)
                .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK)));
        cartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    startActivity(new Intent(MainActivity.this, CartActivity.class));
            }
        });
        profileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(StaticClass.user == null){
                    startActivity(new Intent(MainActivity.this, Login.class));
                }
                else{
                    startActivity(new Intent(MainActivity.this, ProfileActivity.class));
                }
            }
        });
    }
    private void initRecyclerviewListManufacturer() {
        ArrayList<Manufacturer> items = new ArrayList<>();
        StringRequest sReq = new StringRequest(Request.Method.GET, Server.getAllManufacturer, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray array = new JSONArray(response);
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject manufacturerObject = array.getJSONObject(i);
                        Manufacturer manufacturer = new Manufacturer(
                                manufacturerObject.getInt("id"),
                                manufacturerObject.getString("name"),
                                manufacturerObject.getString("image")
                        );
                        items.add(manufacturer);
                    }
                    Log.w("Load manufacturer", String.valueOf(items));

                    recyclerViewListPhone = findViewById(R.id.recycleListPhone);
                    recyclerViewListPhone.setLayoutManager(new GridLayoutManager(MainActivity.this, 4));
                    adapterLogoPhone = new ManufacturerMainAdapter(items);
                    recyclerViewListPhone.setAdapter(adapterLogoPhone);
                } catch (
                        JSONException e) {
                    Log.w("Load manufacturer", "Error: " + e.getMessage());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.w("initRecyclerviewListPhone_onErrorResponse", error.getMessage());
            }
        });
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(sReq);
    }
    private void initRecyclerview(Manufacturer manufacturer) {
        items.clear();
        if(manufacturer == null) {
            StringRequest sReq = new StringRequest(Request.Method.GET, Server.getAllProduct,
                    new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONArray array = new JSONArray(response);
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject productObject = array.getJSONObject(i);
                            Product p = new Product(
                                    productObject.getInt("product_id"),
                                    productObject.getString("product_name"),
                                    productObject.getLong("price"),
                                    productObject.getString("image"),
                                    productObject.getString("manufacturer_name"),
                                    productObject.getDouble("averageRating"),
                                    productObject.getInt("numComment")
                            );
                            items.add(p);
                        }
                        recyclerViewPopular = findViewById(R.id.view1);
                        recyclerViewPopular.setLayoutManager(new GridLayoutManager(MainActivity.this, 2));
                        adapterPopular = new ProductListAdapter(items);
                        recyclerViewPopular.setAdapter(adapterPopular);
                    } catch (
                            JSONException e) {
                        Log.w("Load Phone", e.getMessage());
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.w("initRecyclerview_onErrorResponse", error.getMessage());
                }
            });
            RequestQueue queue = Volley.newRequestQueue(this);
            queue.add(sReq);
        }
        else{
            ArrayList<Product> items = new ArrayList<>();
            StringRequest sReq = new StringRequest(Request.Method.GET, Server.getAllProduct,
                    new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONArray array = new JSONArray(response);
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject productObject = array.getJSONObject(i);
                            if(manufacturer.getName().equals(productObject.getString("manufacturer_name"))) {
                                Product p = new Product(
                                        productObject.getInt("product_id"),
                                        productObject.getString("product_name"),
                                        productObject.getLong("price"),
                                        productObject.getString("image"),
                                        productObject.getString("manufacturer_name"),
                                        productObject.getDouble("averageRating"),
                                        productObject.getInt("numComment")
                                );
                                items.add(p);
                            }
                        }
                        recyclerViewPopular = findViewById(R.id.view1);
                        recyclerViewPopular.setLayoutManager(new GridLayoutManager(MainActivity.this, 2));
                        adapterPopular = new ProductListAdapter(items);
                        recyclerViewPopular.setAdapter(adapterPopular);
                    } catch (
                            JSONException e) {
                        Toast.makeText(MainActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        Log.w("Load Phone", "Error: " + e.getMessage());
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
    private void checkLogin(){
        if(StaticClass.user != null){
            tvUserName.setText(StaticClass.user.getUsername());
        }
        else{
            tvUserName.setVisibility(View.GONE);
        }
    }
}