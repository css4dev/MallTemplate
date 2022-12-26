package com.sawaaid.malltemplate;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.sawaaid.malltemplate.connection.Request;
import com.sawaaid.malltemplate.connection.RequestListener;
import com.sawaaid.malltemplate.connection.response.Resp;
import com.sawaaid.malltemplate.data.DataApp;
import com.sawaaid.malltemplate.databinding.ActivityCompletePurchaseBinding;

public class ActivityCompletePurchase extends AppCompatActivity {

    ActivityCompletePurchaseBinding binding;
    Request request;
    String locationId, userNotes = "", totalPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCompletePurchaseBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        locationId = getIntent().getStringExtra("LOCATION_ID");
        totalPrice = getIntent().getStringExtra("TOTAL_PRICE");

        initComponent();
    }

    private void initComponent() {
        request = new Request();
        binding.completePurchaseButton.setOnClickListener(view -> insertOrder());
    }

    private void insertOrder() {
        request.insertOrder(DataApp.dao().getEntityBasket(), String.valueOf(DataApp.global().getUser().id), locationId, userNotes, totalPrice, new RequestListener<Resp>() {
            @Override
            public void onFinish() {
                super.onFinish();
            }

            @Override
            public void onSuccess(Resp resp) {
                super.onSuccess(resp);
                if (resp.status)
                    Toast.makeText(ActivityCompletePurchase.this, "تم الإضافة بنجاح", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(ActivityCompletePurchase.this, "لم يتم", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailed(String messages) {
                super.onFailed(messages);
                Log.d("sadq", "onFailed: " + messages);
            }
        });
    }
}