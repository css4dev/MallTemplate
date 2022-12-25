package com.sawaaid.malltemplate;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.sawaaid.malltemplate.connection.Request;
import com.sawaaid.malltemplate.connection.RequestListener;
import com.sawaaid.malltemplate.connection.response.RespAddress;
import com.sawaaid.malltemplate.data.DataApp;
import com.sawaaid.malltemplate.databinding.ActivityChooseAddressBinding;
import com.sawaaid.malltemplate.model.Address;

import java.util.List;


public class ActivityChooseAddress extends AppCompatActivity {

    ActivityChooseAddressBinding binding;
    Request request;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChooseAddressBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initComponent();
    }

    private void initComponent() {
        request = new Request();

        requestAddresses();
    }

    private void requestAddresses() {
        request.addresses(DataApp.global().getUser().id, new RequestListener<RespAddress>(){
            @Override
            public void onFinish() {
                super.onFinish();
            }

            @Override
            public void onSuccess(RespAddress resp) {
                super.onSuccess(resp);
                displayData(resp.data);
            }

            @Override
            public void onFailed(String messages) {
                super.onFailed(messages);
            }
        });
    }

    private void displayData(List<Address> data) {

    }
}