package com.example.appsale.Activity.User;

import static com.basgeekball.awesomevalidation.ValidationStyle.BASIC;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;
import com.example.appsale.ObjectClass.Server;
import com.example.appsale.ObjectClass.StaticClass;
import com.example.appsale.R;


import org.json.JSONException;
import org.json.JSONObject;



public class SignUp extends AppCompatActivity {
    private EditText txtHo, txtTen, txtExmail, txtPhone, txtUsername, txtPassword,edtId;
    private TextView btnThemUser;
    private ImageView backBtn;
    AwesomeValidation awesomeValidation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        anhXa();
        regex();
        onClick();
        backBtn.setOnClickListener(v -> finish());

    }
    private void regex(){
        awesomeValidation = new AwesomeValidation(BASIC);
        awesomeValidation.addValidation(SignUp.this, R.id.txtUsername,"[a-zA-Z\\s]+", R.string.err_name);
        awesomeValidation.addValidation(SignUp.this, R.id.txtPhone, RegexTemplate.TELEPHONE, R.string.err_phone);
        awesomeValidation.addValidation(SignUp.this, R.id.txtPhone, RegexTemplate.NOT_EMPTY, R.string.err_phone);
        awesomeValidation.addValidation(SignUp.this, R.id.txtExmail, Patterns.EMAIL_ADDRESS, R.string.err_email);
        awesomeValidation.addValidation(SignUp.this, R.id.txtPassword, StaticClass.regexPassword, R.string.err_password);
    }
    private void onClick() {
        btnThemUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (awesomeValidation.validate()) {
                    try {
                        ThemMoi();
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                }else {
                    Toast.makeText(getApplicationContext(), "Invalid", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
    private void anhXa() {
        edtId = findViewById(R.id.edtId);
        txtHo = findViewById(R.id.txtHo);
        txtTen = findViewById(R.id.txtTen);
        txtExmail = findViewById(R.id.txtExmail);
        txtPhone = findViewById(R.id.txtPhone);
        txtUsername = findViewById(R.id.txtUsername);
        txtPassword = findViewById(R.id.txtPassword);
        btnThemUser = findViewById(R.id.btnThemUser);
        backBtn = findViewById(R.id.backBtn);
    }
    public void ThemMoi() throws JSONException {
        String txtHo = this.txtHo.getText().toString();
        String txtTen = this.txtTen.getText().toString();
        String txtExmail = this.txtExmail.getText().toString();
        String txtPhone = this.txtPhone.getText().toString();
        String txtUsername = this.txtUsername.getText().toString();
        String txtPassword = this.txtPassword.getText().toString();
        if (txtHo.equals("") || txtTen.equals("") || txtExmail.equals("") || txtPhone.equals("")
                || txtUsername.equals("") || txtPassword.equals("")) {
            Toast.makeText(getApplicationContext(), "Vui lòng điền đủ thông tin", Toast.LENGTH_SHORT).show();
        } else {
            try {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("first_name", txtTen);
                jsonObject.put("last_name", txtHo);
                jsonObject.put("email", txtExmail);
                jsonObject.put("phone", txtPhone);
                jsonObject.put("user_name", txtUsername);
                jsonObject.put("password", StaticClass.MD5(txtPassword));
                Log.d("jsonObject", String.valueOf( jsonObject));
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PUT,
                        Server.register,
                        jsonObject,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                Log.d("Response", response.toString());
                                try {
                                    if (response.getString("result").equals("Account created")) {
                                        Toast.makeText(getApplicationContext(), "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                                        finish();
                                        startActivity(new Intent(SignUp.this, Login.class));
                                    } else {
                                        Log.d("Response", response.toString());
                                        Toast.makeText(getApplicationContext()
                                                , "Đăng ký thất bại\n" + response.getString("result")
                                                , Toast.LENGTH_SHORT).show();
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
    }
}