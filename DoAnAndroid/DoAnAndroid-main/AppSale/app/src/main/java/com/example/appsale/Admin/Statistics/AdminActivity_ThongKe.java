package com.example.appsale.Admin.Statistics;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appsale.Admin.AdminActivity;
import com.example.appsale.R;

public class AdminActivity_ThongKe extends AppCompatActivity {
ImageView btnBack;
TextView doanhThu,soLuongSanPhamDaBan;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_thongke);
        anhXa();
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminActivity_ThongKe.this, AdminActivity.class));
            }
        });
        doanhThu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminActivity_ThongKe.this, AdminActivity_ThongKe_DoanhThu.class));
            }
        });
        soLuongSanPhamDaBan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminActivity_ThongKe.this, AdminActivity_ThongKe_SLSP_DaBan.class));
            }
        });
    }
    public void anhXa(){
        btnBack=findViewById(R.id.backBtn);
        doanhThu=findViewById(R.id.txtDoanhThu);
//        soLuongSanPhamDaBan=findViewById(R.id.txtSoLuongDaBan);
    }
}

