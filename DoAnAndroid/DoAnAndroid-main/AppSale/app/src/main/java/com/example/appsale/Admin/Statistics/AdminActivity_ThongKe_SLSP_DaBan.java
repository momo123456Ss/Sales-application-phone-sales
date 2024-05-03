package com.example.appsale.Admin.Statistics;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;

import com.example.appsale.R;

public class AdminActivity_ThongKe_SLSP_DaBan extends AppCompatActivity {
    TextView btnThang,btnQuy,btnNam;
    ImageView btnback;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_thongke_sanpham_daban);
        anhXa();
        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminActivity_ThongKe_SLSP_DaBan.this, AdminActivity_ThongKe.class));
            }
        });
        btnThang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMonthPopupMenu(v); // Hiển thị menu tháng
            }
        });
        btnQuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showQuyPopupMenu(v); // Hiển thị menu Quý
            }
        });
        btnNam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showYearPopupMenu(v); // Hiển thị menu tháng
            }
        });
    }
    public void anhXa(){
        btnback=findViewById(R.id.backBtn);
        btnThang=findViewById(R.id.btnThongKeSLSPTheoThang);
        btnQuy=findViewById(R.id.btnThongKeSLSPTheoQuy);
        btnNam=findViewById(R.id.btnThongKeSLSPTheoNam);
    }
    private void showMonthPopupMenu(View view) {
        PopupMenu popupMenu = new PopupMenu(this, view);
        popupMenu.getMenuInflater().inflate(R.menu.month_menu, popupMenu.getMenu());

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                // Xử lý khi một tháng được chọn
                int selectedMonth = item.getItemId();// Lấy ID của tháng được chọn,lưu tháng đã chọn
                showYearPopupMenu(view);// Hiển thị menu năm
                // Thực hiện các xử lý liên quan đến tháng được chọn
                return true;
            }
        });

        popupMenu.show();
    }
    private void showQuyPopupMenu(View view) {
        PopupMenu popupMenu = new PopupMenu(this, view);
        popupMenu.getMenuInflater().inflate(R.menu.quy_menu, popupMenu.getMenu());

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                // Xử lý khi một quý được chọn
                int selectedMonth = item.getItemId(); // Lấy ID của quý được chọn
                // Thực hiện các xử lý liên quan đến quý được chọn
                return true;
            }
        });
        popupMenu.show();
    }
    private void showYearPopupMenu(View view) {
        PopupMenu popupMenu = new PopupMenu(this, view);
        popupMenu.getMenuInflater().inflate(R.menu.year_menu, popupMenu.getMenu());

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                // Xử lý khi một năm được chọn
                int selectedMonth = item.getItemId(); // Lấy ID của năm được chọn
                // Thực hiện các xử lý liên quan đến năm được chọn
                return true;
            }
        });
        popupMenu.show();
    }
}
