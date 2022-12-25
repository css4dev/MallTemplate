package com.sawaaid.malltemplate;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.View;

import com.sawaaid.malltemplate.adapter.AdapterListener;
import com.sawaaid.malltemplate.adapter.AdapterNotificationHistory;
import com.sawaaid.malltemplate.connection.Request;
import com.sawaaid.malltemplate.connection.RequestListener;
import com.sawaaid.malltemplate.connection.response.RespNotificationHistory;
import com.sawaaid.malltemplate.data.Constant;
import com.sawaaid.malltemplate.databinding.ActivityNotificationHistoryBinding;
import com.sawaaid.malltemplate.model.NotificationHistory;

import java.util.List;

public class ActivityNotificationHistory extends AppCompatActivity {
    ActivityNotificationHistoryBinding binding;
    Request request;
    AdapterNotificationHistory adapterNotificationHistory;
    private boolean allLoaded = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNotificationHistoryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initComponent();

        requestAction(1);

    }

    private void requestAction(int page) {
        adapterNotificationHistory.setLoadingOrFailed(null);
        requestNotificationHistory(page);
    }

    private void initComponent() {
        request = new Request();
        binding.backButton.setOnClickListener(view -> onBackPressed());
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        binding.recyclerView.setHasFixedSize(true);
        adapterNotificationHistory = new AdapterNotificationHistory(this, binding.recyclerView, Constant.LISTING_PAGE);
        binding.recyclerView.setAdapter(adapterNotificationHistory);
        adapterNotificationHistory.setListener(new AdapterListener<NotificationHistory>() {
            @Override
            public void onClick(View view, String type, NotificationHistory obj, int position) {
                super.onClick(view, type, obj, position);
            }

            @Override
            public void onSavedClick(View view, boolean saved, NotificationHistory obj, int position) {
                super.onSavedClick(view, saved, obj, position);
            }

            @Override
            public void onLoadMore(int page) {
                super.onLoadMore(page);
                if (allLoaded) {
                    adapterNotificationHistory.setLoaded();
                } else {
                    int next_page = page + 1;
                    requestAction(next_page);
                }
            }
        });
    }

    private void requestNotificationHistory(int page_no) {
        request.notificationHistory(String.valueOf(page_no), new RequestListener<RespNotificationHistory>() {
            @Override
            public void onFinish() {
                super.onFinish();
            }

            @Override
            public void onSuccess(RespNotificationHistory resp) {
                super.onSuccess(resp);
                allLoaded = resp.data.size() < Constant.LISTING_PAGE;
                displayApiResult(resp.data);
            }

            @Override
            public void onFailed(String messages) {
                super.onFailed(messages);
            }
        });
    }

    private void displayApiResult(List<NotificationHistory> data) {
        adapterNotificationHistory.insertData(data);
    }
}