package com.example.appsale.Admin.Phone;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.appsale.Activity.Cart.CartActivity;
import com.example.appsale.Activity.DetailActivity;
import com.example.appsale.Activity.MainActivity;
import com.example.appsale.Adapter.ManufacturerMainAdapter;
import com.example.appsale.ObjectClass.Manufacturer;
import com.example.appsale.ObjectClass.Product;
import com.example.appsale.ObjectClass.Server;
import com.example.appsale.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Admin_Activity_DSDienThoai_Edit extends AppCompatActivity {
    ImageView btnTroVe;
    Button saveButton;
    ArrayList<Manufacturer> manufacturerArrayList;
    EditText id,name,price,num,size,weight,chipset,ram,storage,battery,charging,hangsx,pic,vid;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_ds_dienthoai_edit);
        anhXa();
        manufacturerArrayList = new ArrayList<>();
        getManufacturer();
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    addProduct();
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        btnTroVe.setOnClickListener(v->finish());
    }
    private void addProduct() throws JSONException {
        JSONObject product = new JSONObject();
        product.put("name", name.getText().toString());
        product.put("price", price.getText().toString());
        product.put("image", pic.getText().toString());
        product.put("manufacturerId", getManufacturerId(manufacturerArrayList,hangsx.getText().toString()));
        product.put("num", num.getText().toString());
        product.put("size", size.getText().toString());
        product.put("weight", weight.getText().toString());
        product.put("chipset", chipset.getText().toString());
        product.put("ram", ram.getText().toString());
        product.put("storage", storage.getText().toString());
        product.put("battery", battery.getText().toString());
        product.put("charging", charging.getText().toString());
        product.put("video", vid.getText().toString());
        Log.w("product", String.valueOf(product));
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PUT,
                Server.addProduct,
                product,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if (response.getString("result").equals("Add successful")){
                                Toast.makeText(Admin_Activity_DSDienThoai_Edit.this, "Thêm thành công", Toast.LENGTH_SHORT).show();
                                finish();
                                startActivity(new Intent(getApplicationContext(), AdminActivity_DSDienthoai.class));
                            }else {
                                Toast.makeText(Admin_Activity_DSDienThoai_Edit.this, "Thêm thất bại", Toast.LENGTH_SHORT).show();
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
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(jsonObjectRequest);
    }
    private int getManufacturerId(ArrayList<Manufacturer> manufacturerArrayList, String name){
        int id;
        for( int i = 0; i < manufacturerArrayList.toArray().length; i++){
            Manufacturer manufacturer = manufacturerArrayList.get(i);
            Log.w("name",manufacturer.getName().toString());
            if (manufacturer.getName().equalsIgnoreCase(name)){
                id = manufacturer.getId();
                return id;
            }
        }
        return 0;
    }
    private void getManufacturer(){
        StringRequest sReq = new StringRequest(Request.Method.GET,
                Server.getAllManufacturer,
                new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray array = new JSONArray(response);
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject manufacturerObject = array.getJSONObject(i);
                        Manufacturer manufacturer = new Manufacturer(
                                manufacturerObject.getInt("id"),
                                manufacturerObject.getString("name"),
                                manufacturerObject.getString("image")
                        );
                        manufacturerArrayList.add(manufacturer);
                    }
                    Log.w("Load manufacturer", String.valueOf(manufacturerArrayList));
                } catch (
                        JSONException e) {
                    Log.w("Load manufacturer", "Error: " + e.getMessage());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.w("initRecyclerviewListPhone_onErrorResponse", error.getMessage());
            }
        });
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(sReq);
    }
    public void anhXa(){
        btnTroVe=findViewById(R.id.backBtn);

        id=findViewById(R.id.edtId);
        name=findViewById(R.id.edtName);
        hangsx=findViewById(R.id.edtHangSX);
        num=findViewById(R.id.edtNum);
        price=findViewById(R.id.edtPrice);
        size=findViewById(R.id.edtSize);
        weight=findViewById(R.id.edtWeight);
        chipset=findViewById(R.id.edtChipset);
        ram=findViewById(R.id.edtRam);
        storage=findViewById(R.id.edtStorage);
        battery=findViewById(R.id.edtPin);
        charging=findViewById(R.id.edtcharging);
        pic=findViewById(R.id.imgPicture);
        vid=findViewById(R.id.edtVideo);
        saveButton = findViewById(R.id.saveButton);
    }
}
