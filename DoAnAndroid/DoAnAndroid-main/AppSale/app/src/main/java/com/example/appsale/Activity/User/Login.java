package com.example.appsale.Activity.User;

import static com.basgeekball.awesomevalidation.ValidationStyle.BASIC;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;
import com.example.appsale.Activity.MainActivity;
import com.example.appsale.ObjectClass.Server;
import com.example.appsale.ObjectClass.StaticClass;
import com.example.appsale.ObjectClass.User;
import com.example.appsale.R;

import org.json.JSONException;
import org.json.JSONObject;

public class Login extends AppCompatActivity {
    EditText txtPassword,txtUsername;
    Button loginButton, btnSingUp;
    ImageView btnBack;
    AwesomeValidation awesomeValidation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_user);
        anhXa();
        regex();
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (awesomeValidation.validate()) {
                login();
                }else {
                    Toast.makeText(getApplicationContext(), "Invalid", Toast.LENGTH_SHORT).show();
                }

            }
        });
        btnBack.setOnClickListener(v -> finish());
        btnSingUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this, SignUp.class));
            }
        });
    }
    private void login(){
        if (txtUsername.getText().toString().equals("") || txtPassword.getText().toString().equals("")
            ||txtUsername.getText().toString().isEmpty() || txtPassword.getText().toString().isEmpty() ) {
            Toast.makeText(getApplicationContext(), "Vui lòng điền đủ thông tin", Toast.LENGTH_SHORT).show();
            return;
        }
        else {
            try {
                JSONObject jsonObject = new JSONObject();

                jsonObject.put("identifier", txtUsername.getText());
                jsonObject.put("password", StaticClass.MD5(txtPassword.getText().toString()));

                Log.d("jsonObject", String.valueOf(jsonObject));

                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PUT,
                        Server.login,
                        jsonObject,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                Log.d("Response", response.toString());
                                try {
                                    if (response.getString("result").equals("Account does not exist")) {
                                        Toast.makeText(getApplicationContext(), "Tài khoản sai hoặc tài khoản đã bị khóa", Toast.LENGTH_SHORT).show();
                                    } else if (response.getString("result").equals("Wrong password")) {
                                        Toast.makeText(getApplicationContext(), "Sai mật khẩu", Toast.LENGTH_SHORT).show();
                                    } else {
                                        roleIdInit(response.getInt("result"));
                                        initUser(response.getInt("result"));
                                        Log.d("loginResponse", response.toString());
                                        startActivity(new Intent(Login.this,MainActivity.class).
                                                setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
                                        Toast.makeText(getApplicationContext(), "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                                    }
                                } catch (JSONException e) {
                                    throw new RuntimeException(e);
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.w("login_onErrorResponse", error.toString());
                            }
                        });
                RequestQueue queue = Volley.newRequestQueue(this);
                queue.add(jsonObjectRequest);
            } catch (JSONException e) {
                Log.w("onErrorResponsed", String.valueOf(e));
                e.printStackTrace();
            }
        }

    }
    private void roleIdInit(int id) {
        StringRequest sReq = new StringRequest(Request.Method.GET,
                Server.getUserRoleByID + id,
                new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    StaticClass.roleId = jsonObject.getInt("roleId");
                } catch (
                        JSONException e) {
                    Log.w("roleIdInit", e.getMessage());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.w("roleIdInit_onErrorResponse", error.getMessage());
            }
        });
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(sReq);
    }
    private void initUser(int id) {
        StringRequest sReq = new StringRequest(Request.Method.GET,
                Server.getUserInfoById + id,
                new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String firstName = jsonObject.getString("first_name");
                    String lastName = jsonObject.getString("last_name");
                    String email = jsonObject.getString("email");
                    String phone = jsonObject.getString("phone");
                    String username = jsonObject.getString("username");
                    User us = new User(id, firstName, lastName, email, phone, username);
                    StaticClass.user = us;
                } catch (
                        JSONException e) {
                    Log.w("Load USER", "Error: " + e.getMessage());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(sReq);
    }
    private void   anhXa(){
        loginButton = findViewById(R.id.loginButton);
        txtUsername = findViewById(R.id.txtUsername);
        txtPassword = findViewById(R.id.txtPassword);
        btnBack = findViewById(R.id.backBtn);
        btnSingUp = findViewById(R.id.btnSingUp);
    }
    private void regex(){
        awesomeValidation = new AwesomeValidation(BASIC);
        awesomeValidation.addValidation(Login.this, R.id.txtUsername,"[a-zA-Z\\s]+", R.string.err_name);
        awesomeValidation.addValidation(Login.this, R.id.txtPassword, StaticClass.regexPassword, R.string.err_password);
    }
}