package com.example.appsale.Admin.Manufacturer;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.appsale.R;

public class Admin_Activity_DSLoaiSP_Edit extends AppCompatActivity {
    ImageView pic,btnTroVe;

    EditText id,name;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_ds_loai_dienthoai_edit);
        anhXa();
        onClick();
    }
    public void onClick(){
        btnTroVe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Admin_Activity_DSLoaiSP_Edit.this, AdminActivity_DSLoaiSP.class));
            }
        });
    }
    public void anhXa(){
        btnTroVe=findViewById(R.id.backBtn);

        id=findViewById(R.id.edtId);
        name=findViewById(R.id.edtName);

        pic=findViewById(R.id.imgPicture);
    }
}
