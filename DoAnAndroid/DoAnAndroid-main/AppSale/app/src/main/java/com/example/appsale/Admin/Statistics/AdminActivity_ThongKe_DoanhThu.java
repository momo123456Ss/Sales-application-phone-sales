package com.example.appsale.Admin.Statistics;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.appsale.ObjectClass.Server;
import com.example.appsale.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AdminActivity_ThongKe_DoanhThu extends AppCompatActivity {
    TextView btnThang,btnQuy,btnNam, tvMonth, tvYear, tvRevenue;
    int month, year, quarter;
    ImageView btnback;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_thongke_doanhthu);
        anhXa();
        tvMonth.setText("");
        tvYear.setText("");
        tvRevenue.setText("");
        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminActivity_ThongKe_DoanhThu.this, AdminActivity_ThongKe.class));
            }
        });
        btnThang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMonthPopupMenu(v);
            }
        });
//        btnQuy.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                showQuyPopupMenu(v);            }
//        });
        btnNam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showYearPopupMenu(v);
            }
        });
    }
    public void anhXa(){
        btnThang=findViewById(R.id.btnThongKeDoanhThuTheoThang);
//        btnQuy=findViewById(R.id.btnThongKeDoanhThuTheoQuy);
        btnNam=findViewById(R.id.btnThongKeDoanhThuTheoNam);
        btnback=findViewById(R.id.backBtn);
        tvMonth = findViewById(R.id.tvMonth);
        tvYear = findViewById(R.id.tvYear);
        tvRevenue = findViewById(R.id.tvRevenue);
    }
    private void showMonthPopupMenu(View view) {
        PopupMenu popupMenu = new PopupMenu(this, view);
        popupMenu.getMenuInflater().inflate(R.menu.month_menu, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Pattern pattern = Pattern.compile("\\d+");
                Matcher matcher = pattern.matcher(item.getTitle().toString());
                while (matcher.find()) {
                    month = Integer.parseInt(matcher.group());
                    Log.w("month", String.valueOf(month));
                }
                tvMonth.setText(String.valueOf(month));
                return true;
            }
        });

        popupMenu.show();
    }
//    private void showQuyPopupMenu(View view) {
//        PopupMenu popupMenu = new PopupMenu(this, view);
//        popupMenu.getMenuInflater().inflate(R.menu.quy_menu, popupMenu.getMenu());
//
//        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
//            @Override
//            public boolean onMenuItemClick(MenuItem item) {
//                // Xử lý khi một quý được chọn
//                int selectedQuy = item.getItemId(); // Lấy ID của quý được chọn
//                // Thực hiện các xử lý liên quan đến quý được chọn
//                return true;
//            }
//        });
//        popupMenu.show();
//    }
    private void showYearPopupMenu(View view) {
        PopupMenu popupMenu = new PopupMenu(this, view);
        popupMenu.getMenuInflater().inflate(R.menu.year_menu, popupMenu.getMenu());

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Pattern pattern = Pattern.compile("\\d+");
                Matcher matcher = pattern.matcher(item.getTitle().toString());
                while (matcher.find()) {
                    year = Integer.parseInt(matcher.group());
                    Log.w("month", String.valueOf(year));
                }
                tvYear.setText(String.valueOf(year));
                getTotalRevenue();
                return true;
            }
        });
        popupMenu.show();
    }
    private void getTotalRevenue(){
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                Server.getRevenueByMonth + month + "/" + year,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            tvRevenue.setText(response.getString("totalRevenue") + "VNĐ");
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(jsonObjectRequest);
    }
}
