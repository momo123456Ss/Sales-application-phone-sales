package com.example.appsale.Admin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import com.example.appsale.Activity.ProfileActivity;
import com.example.appsale.Admin.Manufacturer.AdminActivity_DSLoaiSP;
import com.example.appsale.Admin.Phone.AdminActivity_DSDienthoai;
import com.example.appsale.Admin.Statistics.AdminActivity_ThongKe;
import com.example.appsale.Admin.Statistics.AdminActivity_ThongKe_DoanhThu;
import com.example.appsale.Admin.User.ListUsers;
import com.example.appsale.R;

public class AdminActivity extends AppCompatActivity {
    ImageView imageView;
    TextView txtSP,txtLoai,txtUser,txtThongKe;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        AnhXa();

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminActivity.this, ProfileActivity.class));
            }
        });
        txtSP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminActivity.this, AdminActivity_DSDienthoai.class));
            }
        });
       txtUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminActivity.this, ListUsers.class));
            }
        });
        txtLoai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminActivity.this, AdminActivity_DSLoaiSP.class));
            }
        });
        txtThongKe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminActivity.this, AdminActivity_ThongKe_DoanhThu.class));
            }
        });

    }

    public void AnhXa()
    {
        txtSP=findViewById(R.id.txtDienThoai);
        txtLoai=findViewById(R.id.txtLoai);
        txtUser=findViewById(R.id.txtUser);
        txtThongKe=findViewById(R.id.txtThongKeDoanhThu);
        imageView=findViewById(R.id.backBtn);
    }
    @Override
    public void onBackPressed() {
    }

}
