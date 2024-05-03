package com.example.appsale.Activity.User;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.appsale.Activity.ProfileActivity;
import com.example.appsale.ObjectClass.Server;
import com.example.appsale.ObjectClass.StaticClass;
import com.example.appsale.R;

import org.json.JSONException;
import org.json.JSONObject;

public class EditProfile extends AppCompatActivity {
    private EditText edtFirstName, edtLastName, edtEmail, edtPhone,edtUserName;
    private Button saveButton;
    private ImageView backBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        initView();
        getInfo();
        backBtn.setOnClickListener(v -> startActivity(new Intent(EditProfile.this, ProfileActivity.class)));

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateInfo();
            }
        });
    }

    private void updateInfo() {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("first_name", edtFirstName.getText().toString());
            jsonObject.put("last_name", edtLastName.getText().toString());
            jsonObject.put("email", edtEmail.getText().toString());
            jsonObject.put("phone", edtPhone.getText().toString());
            jsonObject.put("username", edtUserName.getText().toString());
            Log.d("jsonObject", String.valueOf(jsonObject));
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PUT,
                    Server.updateUserInfoById + StaticClass.user.getId(),
                    jsonObject,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Log.d("Response", response.toString());
                            try {
                                if (response.getString("result").equals("Update successful")) {
                                    Toast.makeText(getApplicationContext(), "Lưu thành công", Toast.LENGTH_SHORT).show();
                                    finish();
                                    startActivity(new Intent(EditProfile.this, ProfileActivity.class));
                                } else {
                                    Log.d("Response", response.toString());
                                    Toast.makeText(getApplicationContext(), "Lưu thất bại", Toast.LENGTH_SHORT).show();
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
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    protected void getInfo(){
        edtFirstName.setText(StaticClass.user.getFirstName());
        edtLastName.setText(StaticClass.user.getLastName());
        edtEmail.setText(StaticClass.user.getEmail());
        edtPhone.setText(StaticClass.user.getPhone());
        edtUserName.setText(StaticClass.user.getUsername());
    }
    private void initView() {
        edtFirstName = findViewById(R.id.edtFirstName);
        edtLastName = findViewById(R.id.edtLastName);
        edtEmail = findViewById(R.id.edtEmail);
        backBtn = findViewById(R.id.backBtn);
        saveButton = findViewById(R.id.saveButton);
        edtPhone = findViewById(R.id.edtPhone);
        edtUserName = findViewById(R.id.edtUserName);
    }
}
