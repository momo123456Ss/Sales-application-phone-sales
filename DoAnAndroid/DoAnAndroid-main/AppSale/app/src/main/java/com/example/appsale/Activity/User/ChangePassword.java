package com.example.appsale.Activity.User;

import static com.basgeekball.awesomevalidation.ValidationStyle.BASIC;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import com.android.volley.toolbox.Volley;
import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.example.appsale.Activity.MainActivity;
import com.example.appsale.ObjectClass.Server;
import com.example.appsale.ObjectClass.StaticClass;
import com.example.appsale.R;

import org.json.JSONException;
import org.json.JSONObject;

public class ChangePassword extends AppCompatActivity {
    ImageView btnBack;
    Button btnChangePassword;
    EditText edtNewPassword, edtConfirmPassword;
    AwesomeValidation awesomeValidation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        anhxa();
        regex();
        btnBack.setOnClickListener(v -> finish());
        btnChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (awesomeValidation.validate()) {
                    try {
                        changePassword();
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                }else {
                    Toast.makeText(getApplicationContext(), "Invalid", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void changePassword() throws JSONException {
        String newPass = edtNewPassword.getText().toString();
        String confirmPass = edtConfirmPassword.getText().toString();
        JsonObjectRequest jsonObjectRequest = null;
        if (newPass.equals(confirmPass)) {
            JSONObject object = new JSONObject();
            object.put("enpassword", StaticClass.MD5(confirmPass));
            Log.w("changePassword", newPass + "\n" + confirmPass + "\n" + object);
            jsonObjectRequest = new JsonObjectRequest(Request.Method.PUT,
                    Server.userUpdateUserPasswordById + StaticClass.user.getId(),
                    object, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        if (response.getString("result").equals("Update successful")) {
                            Toast.makeText(ChangePassword.this,
                                    response.getString("result"),
                                    Toast.LENGTH_SHORT).show();
                            StaticClass.user= null;
                            startActivity(new Intent(ChangePassword.this, MainActivity.class)
                                    .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));

                        } else {
                            Toast.makeText(ChangePassword.this,
                                    "Update Failed",
                                    Toast.LENGTH_SHORT).show();
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
        } else {
            Toast.makeText(ChangePassword.this,
                    "Mật khẩu xác nhận không giống",
                    Toast.LENGTH_SHORT).show();
        }
        RequestQueue queue = Volley.newRequestQueue(ChangePassword.this);
        queue.add(jsonObjectRequest);
    }

    private void regex(){
        awesomeValidation = new AwesomeValidation(BASIC);
        awesomeValidation.addValidation(ChangePassword.this, R.id.edtConfirmPassword, StaticClass.regexPassword, R.string.err_password);
        awesomeValidation.addValidation(ChangePassword.this, R.id.edtNewPassword, StaticClass.regexPassword, R.string.err_password);
    }
    private void anhxa() {
        btnBack = findViewById(R.id.backBtn);
        btnChangePassword = findViewById(R.id.btnChangePassword);
        edtConfirmPassword = findViewById(R.id.edtConfirmPassword);
        edtNewPassword = findViewById(R.id.edtNewPassword);
    }
}