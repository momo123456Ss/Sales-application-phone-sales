package com.example.appsale.Admin.User;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.appsale.Admin.AdminActivity;
import com.example.appsale.ObjectClass.Server;
import com.example.appsale.ObjectClass.StaticClass;
import com.example.appsale.R;

import org.json.JSONException;
import org.json.JSONObject;

public class ActiveUser extends AppCompatActivity {
    private TextView editUSER, txtLock, txtActive;
    ImageView btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acive_user);
        anhXa();
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ActiveUser.this, AdminActivity.class));
            }
        });
        String activeinfo;
        if (StaticClass.userEdit.isActive() == true){
            activeinfo = "";
        }else {
            activeinfo = "Locked";
        }
        editUSER.setText(StaticClass.userEdit.getId() + "-" +
                StaticClass.userEdit.getUsername() + "\n" +
                activeinfo);
        txtLock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lock();
            }
        });
        txtActive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                active();
            }
        });
    }
    private void active(){
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PUT,
                Server.adminUpdateUserActiveInfoById + StaticClass.userEdit.getId(), null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("Response", response.toString());
                        try {
                            if (response.getString("result").equals("Update successful")) {
                                Toast.makeText(getApplicationContext(), "Kích hoạt thành công", Toast.LENGTH_SHORT).show();
                                finish();
                                startActivity(new Intent(ActiveUser.this, ListUsers.class));
                            } else {
                                Log.d("Response", response.toString());
                                Toast.makeText(getApplicationContext(), "Kích hoạt thất bại", Toast.LENGTH_SHORT).show();
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
                    }
                });
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(jsonObjectRequest);
    }
    private void lock() {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PUT,
                Server.userUpdateUserActiveInfoById + StaticClass.userEdit.getId(),
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("Response", response.toString());
                        try {
                            if (response.getString("result").equals("Update successful")) {
                                Toast.makeText(getApplicationContext(), "Khóa thành công", Toast.LENGTH_SHORT).show();
                                finish();
                                startActivity(new Intent(ActiveUser.this, ListUsers.class));
                            } else {
                                Log.d("Response", response.toString());
                                Toast.makeText(getApplicationContext(), "Khóa thất bại", Toast.LENGTH_SHORT).show();
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

    }
    private void anhXa() {
        btnBack = findViewById(R.id.backBtn);
        txtLock = findViewById(R.id.txtLock);
        txtActive = findViewById(R.id.txtActive);
        editUSER = findViewById(R.id.editUSER);
    }
}