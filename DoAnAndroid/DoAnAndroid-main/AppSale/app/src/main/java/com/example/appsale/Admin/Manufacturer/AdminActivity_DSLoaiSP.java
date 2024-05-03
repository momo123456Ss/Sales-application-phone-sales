package com.example.appsale.Admin.Manufacturer;

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
import com.example.appsale.Adapter.ManufacturerListAdapter;
import com.example.appsale.Admin.AdminActivity;
import com.example.appsale.ObjectClass.Manufacturer;
import com.example.appsale.ObjectClass.Server;
import com.example.appsale.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class AdminActivity_DSLoaiSP extends AppCompatActivity {
    ImageView btnBack;
    TextView btnThem,btnSua,btnXoa;
    private RecyclerView.Adapter  adapterManufacturerPhone;
    private RecyclerView recyclerViewListPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_ds_loai_dienthoai);
        anhXa();initRecyclerviewListManufacturer();
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminActivity_DSLoaiSP.this, AdminActivity.class));
            }
        });
        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminActivity_DSLoaiSP.this, Admin_Activity_DSLoaiSP_Edit.class));
            }
        });
    }
    public void anhXa(){
        btnBack=findViewById(R.id.backBtn);
        btnThem=findViewById(R.id.btnThemLoaiDienThoai);
        btnSua=findViewById(R.id.btnSuaLoaiDienThoai);
        btnXoa=findViewById(R.id.btnSuaLoaiDienThoai);
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
                    recyclerViewListPhone = findViewById(R.id.DanhSachLoaiDienThoai);
                    recyclerViewListPhone.setLayoutManager(new GridLayoutManager(AdminActivity_DSLoaiSP.this, 1));
                    adapterManufacturerPhone = new ManufacturerListAdapter(items);
                    recyclerViewListPhone.setAdapter(adapterManufacturerPhone);
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

