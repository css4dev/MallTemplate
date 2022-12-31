package com.sawaaid.malltemplate;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.sawaaid.malltemplate.adapter.AdapterCompletePurchase;
import com.sawaaid.malltemplate.connection.Request;
import com.sawaaid.malltemplate.connection.RequestListener;
import com.sawaaid.malltemplate.connection.response.Resp;
import com.sawaaid.malltemplate.connection.response.RespProduct;
import com.sawaaid.malltemplate.data.DataApp;
import com.sawaaid.malltemplate.databinding.ActivityCompletePurchaseBinding;
import com.sawaaid.malltemplate.model.Product;
import com.sawaaid.malltemplate.room.entity.EntityBasket;

import java.util.ArrayList;
import java.util.List;

public class ActivityCompletePurchase extends AppCompatActivity {

    ActivityCompletePurchaseBinding binding;
    Request request;
    String locationId, userNotes = "", totalPrice;
    List<EntityBasket> entityBasketList = DataApp.dao().getEntityBasket();
    List<String> productsId = new ArrayList<>();
    AdapterCompletePurchase adapterCompletePurchase;

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

        requestProducts();

        binding.buttonBuy.setOnClickListener(view -> insertOrder());

    }

    private void requestProducts() {
        if (entityBasketList.size() > 0) {
            for (int i = 0; i < entityBasketList.size(); i++) {
                productsId.add(String.valueOf(entityBasketList.get(i).productId));
            }
            String ids = productsId.toString();
            ids = ids.replace("[", "");
            ids = ids.replace("]", "");

            request.cartProducts(ids, new RequestListener<RespProduct>() {
                @Override
                public void onFinish() {
                    super.onFinish();
                    binding.progressBar.setVisibility(View.GONE);
                }

                @Override
                public void onSuccess(RespProduct resp) {
                    super.onSuccess(resp);
                    displayData(resp.data);
                }

                @Override
                public void onFailed(String messages) {
                    super.onFailed(messages);
                }
            });
        }
    }

    private void displayData(List<Product> data) {
        adapterCompletePurchase = new AdapterCompletePurchase(data, this);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        binding.recyclerView.setAdapter(adapterCompletePurchase);
        binding.recyclerView.setOnFlingListener(null);
        binding.recyclerView.setItemViewCacheSize(50);

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