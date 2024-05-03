package com.example.appsale.Activity.User;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import com.example.appsale.Activity.ProfileActivity;
import com.example.appsale.R;

public class FeedBack extends AppCompatActivity {
    private ImageView backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_back);

        initView();
        backBtn.setOnClickListener(v -> startActivity(new Intent(FeedBack.this, ProfileActivity.class)));
    }

    private void initView() {
        backBtn = findViewById(R.id.backBtn);
    }
}