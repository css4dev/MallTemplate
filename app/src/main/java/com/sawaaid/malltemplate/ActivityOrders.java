package com.sawaaid.malltemplate;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.sawaaid.malltemplate.adapter.AdapterListener;
import com.sawaaid.malltemplate.adapter.AdapterOrders;
import com.sawaaid.malltemplate.connection.Request;
import com.sawaaid.malltemplate.connection.RequestListener;
import com.sawaaid.malltemplate.connection.response.RespOrder;
import com.sawaaid.malltemplate.data.Constant;
import com.sawaaid.malltemplate.data.DataApp;
import com.sawaaid.malltemplate.databinding.ActivityOrdersBinding;
import com.sawaaid.malltemplate.model.Order;

import java.util.List;

public class ActivityOrders extends AppCompatActivity {

    ActivityOrdersBinding binding;
    AdapterOrders adapterOrders;
    Request request;
    private boolean allLoaded = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOrdersBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initComponent();

        requestAction(1);
    }

    private void requestAction(int page_no) {
        adapterOrders.setLoadingOrFailed(null);
        requestOrders(page_no);
    }

    private void initComponent() {
        request = new Request();
        binding.progressBar.setVisibility(View.VISIBLE);
        binding.noOrdersTextView.setVisibility(View.INVISIBLE);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        binding.recyclerView.setHasFixedSize(true);
        adapterOrders = new AdapterOrders(this, binding.recyclerView, Constant.LISTING_PAGE);
        binding.recyclerView.setAdapter(adapterOrders);
        adapterOrders.setListener(new AdapterListener<Order>() {
            @Override
            public void onClick(View view, String type, Order obj, int position) {
                super.onClick(view, type, obj, position);
                Intent intent = new Intent(ActivityOrders.this, ActivityOrderDetails.class);
                intent.putExtra("ORDER_ID", obj.id);
                startActivity(intent);
            }

            @Override
            public void onSavedClick(View view, boolean saved, Order obj, int position) {
                super.onSavedClick(view, saved, obj, position);
            }

            @Override
            public void onLoadMore(int page) {
                super.onLoadMore(page);
                if (allLoaded) {
                    adapterOrders.setLoaded();
                } else {
                    int next_page = page + 1;
                    requestAction(next_page);
                }
            }
        });
    }

    private void requestOrders(int page) {
        request.orders(String.valueOf(page), String.valueOf(DataApp.global().getUser().id), new RequestListener<RespOrder>() {
            @Override
            public void onFinish() {
                super.onFinish();
                binding.progressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onSuccess(RespOrder resp) {
                super.onSuccess(resp);
                allLoaded = resp.data.size() < Constant.LISTING_PAGE;
                displayApiResult(resp.data);
                binding.totalOrdersNumberTextView.setText(resp.data.size() + "");
                if (resp.data.size() == 0) {
                    binding.noOrdersTextView.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailed(String messages) {
                super.onFailed(messages);
            }
        });
    }

    private void displayApiResult(List<Order> data) {
        adapterOrders.insertData(data);
    }
}