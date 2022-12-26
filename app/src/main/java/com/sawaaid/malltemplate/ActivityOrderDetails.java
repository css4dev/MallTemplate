package com.sawaaid.malltemplate;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.util.Log;

import com.sawaaid.malltemplate.adapter.AdapterOrderDetails;
import com.sawaaid.malltemplate.connection.Request;
import com.sawaaid.malltemplate.connection.RequestListener;
import com.sawaaid.malltemplate.connection.response.Resp;
import com.sawaaid.malltemplate.connection.response.RespOrderDetails;
import com.sawaaid.malltemplate.databinding.ActivityOrderDetailsBinding;
import com.sawaaid.malltemplate.model.OrderDetails;

import java.util.List;

public class ActivityOrderDetails extends AppCompatActivity {

    ActivityOrderDetailsBinding binding;
    Request request;
    AdapterOrderDetails adapterOrderDetails;
    int orderId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOrderDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        orderId = getIntent().getIntExtra("ORDER_ID", 0);

        initComponent();
    }

    private void initComponent() {
        request = new Request();
        binding.backButton.setOnClickListener(view -> onBackPressed());
        binding.orderId.setText(orderId + "");
        requestOrderDetails(orderId);
    }

    private void requestOrderDetails(int orderId) {
        request.orderDetails(orderId, new RequestListener<RespOrderDetails>() {
            @Override
            public void onFinish() {
                super.onFinish();
            }

            @Override
            public void onSuccess(RespOrderDetails resp) {
                super.onSuccess(resp);
                displayData(resp.data);
            }

            @Override
            public void onFailed(String messages) {
                super.onFailed(messages);
            }
        });
    }

    private void displayData(List<OrderDetails> data) {
        adapterOrderDetails = new AdapterOrderDetails(data, this);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        binding.recyclerView.setAdapter(adapterOrderDetails);
        binding.recyclerView.setOnFlingListener(null);
        binding.recyclerView.setItemViewCacheSize(50);


    }
}