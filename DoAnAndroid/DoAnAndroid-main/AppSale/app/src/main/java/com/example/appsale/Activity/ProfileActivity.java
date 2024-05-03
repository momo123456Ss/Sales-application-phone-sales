package com.example.appsale.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.appsale.Activity.Cart.CartActivity;
import com.example.appsale.Activity.User.ChangePassword;
import com.example.appsale.Activity.User.EditProfile;
import com.example.appsale.Activity.User.LichSu;
import com.example.appsale.Admin.AdminActivity;
import com.example.appsale.ObjectClass.Server;
import com.example.appsale.ObjectClass.StaticClass;
import com.example.appsale.R;

import org.json.JSONException;
import org.json.JSONObject;

public class ProfileActivity extends AppCompatActivity {
    private ImageView avatarImageView;
    LinearLayout linearAdmin;
    private TextView tvUserName, txtPhone, txtChange, txtLichsu, txtPasswordChange, txtChamsoc, txtAdmin;
    private Button btnLogout, btnDelAccount;
    private String savedUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        initView();
        bottom_navigation();
        avatarImageView.setImageResource(R.drawable.ic_avatar);
        txtChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfileActivity.this, EditProfile.class));
            }
        });
        txtLichsu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfileActivity.this, LichSu.class));
            }
        });
        txtAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfileActivity.this, AdminActivity.class));
            }
        });
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StaticClass.user = null;
                StaticClass.roleId = -1;
                startActivity(new Intent(ProfileActivity.this, MainActivity.class));

            }
        });
        btnDelAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringRequest stringRequest = new StringRequest(Request.Method.PUT,
                        Server.userUpdateUserActiveInfoById + StaticClass.user.getId(),
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONObject jsonObject = new JSONObject(response);
                                    if (jsonObject.getString("result").equals("Update successful")){
                                        Toast.makeText(ProfileActivity.this,"Delete successful", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(ProfileActivity.this,MainActivity.class));
                                        StaticClass.user = null;
                                    }else {
                                        Toast.makeText(ProfileActivity.this,"Delete failed", Toast.LENGTH_SHORT).show();
                                    }
                                } catch (JSONException e) {
                                    throw new RuntimeException(e);
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
                RequestQueue queue = Volley.newRequestQueue(ProfileActivity.this);
                queue.add(stringRequest);
            }
        });
        txtPasswordChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfileActivity.this, ChangePassword.class));
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            if (data != null) {
                String newUsername = data.getStringExtra("newUsername");
                updateUserInfo(newUsername);
            }
        }
    }
    private void updateUserInfo(String newUsername) {
        tvUserName.setText(newUsername);
    }

    private void initView() {
        avatarImageView = findViewById(R.id.avatarImageView);
        tvUserName = findViewById(R.id.tvUserName);
        txtPhone = findViewById(R.id.txtPhone);
        btnLogout = findViewById(R.id.btnLogout);
        txtChange = findViewById(R.id.txtInfoChange);
        txtPasswordChange = findViewById(R.id.txtPasswordChange);
        txtLichsu = findViewById(R.id.txtLichsu);
        txtAdmin = findViewById(R.id.txtAdmin);
        btnDelAccount = findViewById(R.id.btnDelAccount);
        linearAdmin = findViewById(R.id.linearAdmin);
        if (StaticClass.user != null) {
            tvUserName.setText(StaticClass.user.getFirstName() + " " + StaticClass.user.getLastName());
            txtPhone.setText(StaticClass.user.getPhone());
            if (StaticClass.roleId != -1) {
                if (StaticClass.roleId == 2) {
                    txtAdmin.setVisibility(View.GONE);
                    linearAdmin.setVisibility(View.GONE);
                } else {
                    linearAdmin.setVisibility(View.VISIBLE);
                    txtAdmin.setVisibility(View.VISIBLE);
                    btnDelAccount.setVisibility(View.GONE);
                }
            }
        }
    }

    private void bottom_navigation() {
        LinearLayout homeBtn = findViewById(R.id.homeBtn);
        LinearLayout cartBtn = findViewById(R.id.cartBtn);
        LinearLayout profileBtn = findViewById(R.id.profileBtn);

        homeBtn.setOnClickListener(v -> startActivity(new Intent(ProfileActivity.this, MainActivity.class)));
        cartBtn.setOnClickListener(v -> startActivity(new Intent(ProfileActivity.this, CartActivity.class)));
        profileBtn.setOnClickListener(v -> startActivity(new Intent(ProfileActivity.this, ProfileActivity.class)));
    }
}