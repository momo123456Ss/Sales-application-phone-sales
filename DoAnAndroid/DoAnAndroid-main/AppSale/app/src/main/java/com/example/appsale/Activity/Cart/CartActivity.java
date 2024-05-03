package com.example.appsale.Activity.Cart;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.appsale.Activity.User.LichSu;
import com.example.appsale.Activity.MainActivity;
import com.example.appsale.Activity.ProfileActivity;
import com.example.appsale.Adapter.CartListAdapter;
import com.example.appsale.Activity.User.Login;
import com.example.appsale.Activity.Cart.Helper.ChangeNumberItemsListener;
import com.example.appsale.Activity.Cart.Helper.ManagmentCart;
import com.example.appsale.ObjectClass.Product;
import com.example.appsale.ObjectClass.Server;
import com.example.appsale.ObjectClass.StaticClass;
import com.example.appsale.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class CartActivity extends AppCompatActivity {
    double percentTax = 0.08;
    double delivery;
    private RecyclerView.Adapter adapter;
    private EditText edtAddress;
    private RecyclerView recyclerView;
    private ManagmentCart managmentCart;
    private Button btnOrderNow;
    private TextView totalFeeTxt, taxTxt, deliveryTxt, totalTxt, emptyTxt;
    private ScrollView scrollView;
    private Long totalTmp = Long.valueOf(0);
    ChangeNumberItemsListener changeNumberItemsListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        initView();
        if (managmentCart.getListCart().isEmpty()) {
            emptyTxt.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
            btnOrderNow.setVisibility(View.GONE);
            delivery = 0;
        } else {
            emptyTxt.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
            btnOrderNow.setVisibility(View.VISIBLE);
            delivery = 180;
        }
        initList();
        calcualteCart();
        bottom_navigation();
        btnOrderNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (StaticClass.user == null) {
                    startActivity(new Intent(CartActivity.this, Login.class));
                } else {
                    if (StaticClass.user != null) {
                        try {
                            StaticClass.products = managmentCart.getListCart();
                            ThemMoiDonHang();
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            }
        });
    }
    private void ThemMoiChiTietDonHang(int id,ArrayList<Product> items) throws JSONException {
        for (Product p : items) {
            try {
                JSONObject jsonObject = new JSONObject();

                jsonObject.put("orderId", id);
                jsonObject.put("productId", p.getId());
                jsonObject.put("num", p.getNumberInCart());

                Log.d("jsonObject", String.valueOf(jsonObject));

                RequestQueue queue = Volley.newRequestQueue(this);
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PUT,
                        Server.addOrderDetail,
                        jsonObject,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                Log.d("Response", response.toString());
                                finish();
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.w("onErrorResponse", error.toString());
                                Toast.makeText(getApplicationContext(), "Lỗi " + error.toString(), Toast.LENGTH_SHORT).show();
                            }
                        });
                queue.add(jsonObjectRequest);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
    private void ThemMoiDonHang() throws JSONException {
        String address = edtAddress.getText().toString();
        if (address.equals("") || address.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Vui lòng điền Địa chỉ", Toast.LENGTH_SHORT).show();
        } else {
            try {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("userId", StaticClass.user.getId());
                jsonObject.put("totalPrice", totalTmp);
                jsonObject.put("address", address);
                Log.w("jsonObject", String.valueOf(jsonObject));
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PUT,
                        Server.addOrder,
                        jsonObject,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                Log.d("Response", response.toString());
                                try {
                                    if (response.getString("result").equals("Add successful")) {
                                        Toast.makeText(getApplicationContext(), "Đã đặt hàng", Toast.LENGTH_SHORT).show();
                                        finish();
                                        startActivity(new Intent(CartActivity.this, LichSu.class));
                                    } else {
                                        Log.d("Response", response.toString());
                                        Toast.makeText(getApplicationContext(), "đặt hàng thất bại", Toast.LENGTH_SHORT).show();
                                    }
                                } catch (JSONException e) {
                                    throw new RuntimeException(e);
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.w("onErrorResponse", error.toString());
                                Toast.makeText(getApplicationContext(), "Lỗi " + error.toString(), Toast.LENGTH_SHORT).show();
                            }
                        });
                RequestQueue queue = Volley.newRequestQueue(this);
                queue.add(jsonObjectRequest);
                getNewOrderId();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }
    public int getNewOrderId() throws JSONException {
        StringRequest sReq = new StringRequest(Request.Method.GET,
                Server.getAllOrderOfUserByUserId + StaticClass.user.getId(),
                new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray array = new JSONArray(response);
                    int num = 0;
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject orderObject = array.getJSONObject(i);
                        num = orderObject.getInt("order_id");
                        Log.w("orderid", String.valueOf(num));
                    }
                    StaticClass.newOrderId = num;
                    Log.w("StaticClass.newOrderId", String.valueOf(StaticClass.newOrderId));
                    ThemMoiChiTietDonHang(StaticClass.newOrderId,StaticClass.products);
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
        RequestQueue queue = Volley.newRequestQueue(CartActivity.this);
        queue.add(sReq);
        return   StaticClass.newOrderId;
    }
    private void initView() {
        managmentCart = new ManagmentCart(this);
        totalFeeTxt = findViewById(R.id.totalFeeTxt);
        taxTxt = findViewById(R.id.taxTxt);
        deliveryTxt = findViewById(R.id.deliveryTxt);
        totalTxt = findViewById(R.id.totalTxt);
        recyclerView = findViewById(R.id.view3);
        scrollView = findViewById(R.id.scrollView3);
        emptyTxt = findViewById(R.id.tvCartEmpty);
        btnOrderNow = (Button) findViewById(R.id.btnOrderNow);
        edtAddress = (EditText) findViewById(R.id.edtAddress);
    }
    private void initList() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new CartListAdapter(managmentCart.getListCart(), this, new ChangeNumberItemsListener() {
            @Override
            public void change() {
                if (managmentCart.getListCart().isEmpty()) {
                    emptyTxt.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);
                    btnOrderNow.setVisibility(View.GONE);
                    delivery = 0;
                    calcualteCart();
                } else {
                    emptyTxt.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                    btnOrderNow.setVisibility(View.VISIBLE);
                    delivery = 180;
                    calcualteCart();
                }
            }
        });
        recyclerView.setAdapter(adapter);
    }
    private void calcualteCart() {
        Long total = Math.round((managmentCart.getTotalFee() + Math.round((managmentCart.getTotalFee() * percentTax * 100.0)) / 100.0 + delivery) * 100) / 100;
        Long itemTotal = Math.round(managmentCart.getTotalFee() * 100) / 100;
        totalTmp = total;
        totalFeeTxt.setText(itemTotal + "VNĐ");
        taxTxt.setText(Math.round((managmentCart.getTotalFee() * percentTax * 100.0)) / 100.0 + "VNĐ");
        deliveryTxt.setText(delivery + "VNĐ");
        totalTxt.setText(total + "VNĐ");
    }


    private void bottom_navigation() {
        LinearLayout homeBtn = findViewById(R.id.homeBtn);
        LinearLayout profileBtn = findViewById(R.id.profileBtn);
        homeBtn.setOnClickListener(v -> startActivity(new Intent(CartActivity.this, MainActivity.class)));
        profileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (StaticClass.user == null) {
                    startActivity(new Intent(CartActivity.this, Login.class));
                } else {
                    startActivity(new Intent(CartActivity.this, ProfileActivity.class));
                }
            }
        });
    }


}