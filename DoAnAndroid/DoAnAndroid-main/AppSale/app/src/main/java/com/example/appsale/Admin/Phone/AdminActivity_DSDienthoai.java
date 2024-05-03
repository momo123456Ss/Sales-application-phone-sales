package com.example.appsale.Admin.Phone;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.appsale.Adapter.ProductListAdapter;
import com.example.appsale.Admin.AdminActivity;
import com.example.appsale.ObjectClass.Product;
import com.example.appsale.ObjectClass.Server;
import com.example.appsale.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class AdminActivity_DSDienthoai extends AppCompatActivity {
    ImageView btnBack;
    TextView btnThem,btnSua,btnXoa;

    private RecyclerView.Adapter adapterDienThoai;
    private RecyclerView recyclerViewDienThoai;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_ds_dienthoai);
        anhXa();
        initRecyclerview();
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminActivity_DSDienthoai.this, AdminActivity.class));
            }
        });
        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminActivity_DSDienthoai.this, Admin_Activity_DSDienThoai_Edit.class));
            }
        });
    }
    public void anhXa(){
        btnBack=findViewById(R.id.backBtn);
        btnThem=findViewById(R.id.btnThemDienThoai);
    }
    private void initRecyclerview() {
        ArrayList<Product> items = new ArrayList<>();
        StringRequest sReq = new StringRequest(Request.Method.GET, Server.getAllProduct, new Response.Listener<String>() {
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
                                productObject.getString("manufacturer_name")
                        );
                        items.add(p);
                    }
                    recyclerViewDienThoai = findViewById(R.id.DanhSachDienThoai);
                    recyclerViewDienThoai.setLayoutManager(new GridLayoutManager(AdminActivity_DSDienthoai.this, 2));
                    adapterDienThoai = new ProductListAdapter(items);
                    recyclerViewDienThoai.setAdapter(adapterDienThoai);
                } catch (
                        JSONException e) {
                    Toast.makeText(AdminActivity_DSDienthoai.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.w("Load Phone", "Error: " + e.getMessage());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue queue = Volley.newRequestQueue(AdminActivity_DSDienthoai.this);
        queue.add(sReq);
    }
}

