package com.example.appsale.Activity.User;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import com.example.appsale.Activity.ProfileActivity;
import com.example.appsale.R;

public class Care extends AppCompatActivity {

    private ImageView backBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_care);
        initView();
        backBtn.setOnClickListener(v -> startActivity(new Intent(Care.this, ProfileActivity.class)));
    }

    private void initView() {
        backBtn = findViewById(R.id.backBtn);
    }
}