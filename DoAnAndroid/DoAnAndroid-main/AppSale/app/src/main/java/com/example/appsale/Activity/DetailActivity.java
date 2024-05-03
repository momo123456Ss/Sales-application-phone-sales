package com.example.appsale.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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
import com.bumptech.glide.Glide;
import com.example.appsale.Activity.Cart.CartActivity;
import com.example.appsale.Adapter.CommentListAdapter;
import com.example.appsale.Activity.Cart.Helper.ManagmentCart;
import com.example.appsale.ObjectClass.Comment;
import com.example.appsale.ObjectClass.Product;
import com.example.appsale.ObjectClass.Server;
import com.example.appsale.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class DetailActivity extends AppCompatActivity {
    private Button addToCartBtn, buyNow;
    private TextView titleTxt, feeTxt, reviewTxt, scoreTxt;
    private TextView tvSize, tvWeight, tvChipset,tvComment, tvRam, tvStorage, tvBattery, tvVideo, tvCharging;
    private ImageView picItem, backBtn;
    private Product object;
    private int numberOrder = 1;
    private ManagmentCart managmentCart;
    private RecyclerView.Adapter adapterComment;
    private RecyclerView recyclerViewComment;
    private LinearLayout lỉnearComment;
    int visible = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        managmentCart = new ManagmentCart(this);
        initView();

        tvComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lỉnearComment.setVisibility(View.VISIBLE);
                visible ++;
                if(visible > 1){
                    visible = 0;
                    lỉnearComment.setVisibility(View.GONE);
                }
            }
        });
        getBundle();
        getProduct();
        getComment();
    }
    private void getComment(){
        object = (Product) getIntent().getSerializableExtra("object");
        ArrayList<Comment> items = new ArrayList<>();
        StringRequest sReq = new StringRequest(Request.Method.GET, Server.getAllCommentOfProductByProductId + object.getId(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray array = new JSONArray(response);
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject commentObject = array.getJSONObject(i);
                        Comment comment = new Comment(
                                commentObject.getString("content"),
                                commentObject.getString("user_name"),
                                commentObject.getInt("star")
                        );
                        items.add(comment);
                    }
                    Log.w("Load manufacturer", String.valueOf(items));

                    recyclerViewComment.setLayoutManager(new GridLayoutManager(DetailActivity.this, 1));
                    adapterComment = new CommentListAdapter(items);
                    recyclerViewComment.setAdapter(adapterComment);
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
    private void getProduct(){
        object = (Product) getIntent().getSerializableExtra("object");
        StringRequest sReq = new StringRequest(Request.Method.GET, Server.getProductInfoById + object.getId(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(!response.equals("") || !response.isEmpty()) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        tvSize.setText(jsonObject.getString("size"));
                        tvWeight.setText(jsonObject.getString("weight"));
                        tvChipset.setText(jsonObject.getString("chipset"));
                        tvRam.setText(jsonObject.getString("ram"));
                        tvStorage.setText(jsonObject.getString("storage"));
                        tvBattery.setText(jsonObject.getString("battery"));
                        tvCharging.setText(jsonObject.getString("charging"));
                        tvVideo.setText(jsonObject.getString("video"));
                    } catch (
                            JSONException e) {
                        Log.w("Load manufacturer", "Error: " + e.getMessage());
                    }
                }
                else{
                    Toast.makeText(DetailActivity.this, "Sản phẩm lỗi", Toast.LENGTH_SHORT).show();
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
    private void getBundle() {
        object = (Product) getIntent().getSerializableExtra("object");

        Glide.with(DetailActivity.this).load(object.getImage())
                .into(picItem);
        titleTxt.setText(object.getName());
        feeTxt.setText(String.valueOf(object.getPrice()));
        reviewTxt.setText(String.valueOf(object.getNum()));
        scoreTxt.setText(String.valueOf(object.getAverageRating()));

        tvRam.setText(object.getRam());
        tvWeight.setText(String.valueOf(object.getWeight()));
        tvStorage.setText(String.valueOf(object.getStorage()));
        tvVideo.setText(object.getVideo());
        tvChipset.setText(object.getChipset());
        tvSize.setText(String.valueOf(object.getSize()));
        tvCharging.setText(String.valueOf(object.getCharging()));
        tvBattery.setText(String.valueOf(object.getBattery()));

        addToCartBtn.setOnClickListener(v -> {
            object.setNumberInCart(numberOrder);
            managmentCart.insertFood(object);
        });

        buyNow.setOnClickListener(v -> {
            object.setNumberInCart(numberOrder);
            managmentCart.insertFood(object);
            startActivity(new Intent(DetailActivity.this, CartActivity.class));
        });

        backBtn.setOnClickListener(v -> finish());
    }

    private void initView() {
        addToCartBtn = findViewById(R.id.addToCartBtn);
        buyNow = findViewById(R.id.buyNow);
        feeTxt = findViewById(R.id.priceTxt);
        titleTxt = findViewById(R.id.titleTxt);
        picItem = findViewById(R.id.itemPic);
        reviewTxt = findViewById(R.id.reviewTxt);
        scoreTxt = findViewById(R.id.scoreTxt);
        backBtn = findViewById(R.id.backBtn);

        tvSize = findViewById(R.id.tvSize);
        tvBattery = findViewById(R.id.tvBattery);
        tvCharging = findViewById(R.id.tvCharging);
        tvRam = findViewById(R.id.tvRam);
        tvChipset = findViewById(R.id.tvChipset);
        tvVideo = findViewById(R.id.tvVideo);
        tvStorage = findViewById(R.id.tvStorage);
        tvWeight = findViewById(R.id.tvWeight);

        tvComment = findViewById(R.id.textView22);
        lỉnearComment = findViewById(R.id.lỉnearComment);
        recyclerViewComment = findViewById(R.id.binhLuanSP);
    }
}