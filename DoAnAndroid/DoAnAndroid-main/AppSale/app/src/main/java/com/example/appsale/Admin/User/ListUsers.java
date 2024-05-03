package com.example.appsale.Admin.User;

import android.os.Bundle;
import android.util.Log;
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
import com.example.appsale.Adapter.UserListAdapter;
import com.example.appsale.ObjectClass.Server;
import com.example.appsale.ObjectClass.User;
import com.example.appsale.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ListUsers extends AppCompatActivity {
    ImageView btnBack;
    TextView btnSua,btnXoa,btnThemNguoiDung;
    private RecyclerView.Adapter  adapterUser;
    private RecyclerView recyclerViewListUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_ds_users);
        btnBack=findViewById(R.id.backBtn);
        initRecyclerviewListUser();
        btnBack.setOnClickListener(v -> finish());
    }
    private void initRecyclerviewListUser() {
        ArrayList<User> items = new ArrayList<>();
        StringRequest sReq = new StringRequest(Request.Method.GET,
                Server.getAllUser,
                new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray array = new JSONArray(response);
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject manufacturerObject = array.getJSONObject(i);
                        User user = new User(
                                manufacturerObject.getInt("user_id"),
                                manufacturerObject.getString("last_name"),
                                manufacturerObject.getString("email"),
                                manufacturerObject.getString("username"),
                                manufacturerObject.getString("role_name"),
                                manufacturerObject.getBoolean("active")
                        );
                        items.add(user);
                    }
                    recyclerViewListUser = findViewById(R.id.DanhSachNguoiDung);
                    recyclerViewListUser.setLayoutManager(new GridLayoutManager(ListUsers.this, 1));
                    adapterUser = new UserListAdapter(items);
                    recyclerViewListUser.setAdapter(adapterUser);
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

