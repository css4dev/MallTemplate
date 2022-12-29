package com.sawaaid.malltemplate;

import static com.sawaaid.malltemplate.adapter.AdapterAddresses.viewArrayListAddress;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.sawaaid.malltemplate.adapter.AdapterAddresses;
import com.sawaaid.malltemplate.connection.Request;
import com.sawaaid.malltemplate.connection.RequestListener;
import com.sawaaid.malltemplate.connection.response.Resp;
import com.sawaaid.malltemplate.connection.response.RespAddress;
import com.sawaaid.malltemplate.data.DataApp;
import com.sawaaid.malltemplate.databinding.ActivityChooseAddressBinding;
import com.sawaaid.malltemplate.model.Address;
import com.sawaaid.malltemplate.model.ViewAddressModel;

import java.util.List;

import me.grantland.widget.AutofitTextView;


public class ActivityChooseAddress extends AppCompatActivity {

    ActivityChooseAddressBinding binding;
    Request request;
    AdapterAddresses adapterAddresses;
    String locationId = "", totalPrice = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChooseAddressBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        totalPrice = getIntent().getStringExtra("TOTAL_PRICE");

        initComponent();
    }

    private void initComponent() {
        request = new Request();
        binding.completePurchaseButton.setOnClickListener(view -> {
            Intent intent = new Intent(ActivityChooseAddress.this, ActivityCompletePurchase.class);
            intent.putExtra("LOCATION_ID", locationId);
            intent.putExtra("TOTAL_PRICE", totalPrice);
            startActivity(intent);
        });
        binding.addNewAddressButton.setOnClickListener(view ->
                startActivity(new Intent(ActivityChooseAddress.this, ActivityAddAddress.class)));

    }

    private void requestAddresses() {
        binding.progressBar.setVisibility(View.VISIBLE);
        request.addresses(DataApp.global().getUser().id, new RequestListener<RespAddress>() {
            @Override
            public void onFinish() {
                super.onFinish();
                binding.progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onSuccess(RespAddress resp) {
                super.onSuccess(resp);
                displayData(resp.data);
                if (resp.data.size() == 0)
                    binding.text.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFailed(String messages) {
                super.onFailed(messages);
            }
        });
    }

    private void displayData(List<Address> data) {
        adapterAddresses = new AdapterAddresses(data, this);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        binding.recyclerView.setAdapter(adapterAddresses);
        binding.recyclerView.setOnFlingListener(null);
        binding.recyclerView.setItemViewCacheSize(50);

        adapterAddresses.setOnDeleteClickListener((view, obj, position) -> requestDeleteAddress(obj.id));

        adapterAddresses.setOnItemClickListener((view, obj, position) -> {
            locationId = String.valueOf(obj.id);

            for (ViewAddressModel v : viewArrayListAddress) {
                v.getCardView().setBackgroundResource(R.drawable.btn_unpressed);
                v.getLocationName().setTextColor(getResources().getColor(R.color.textColor));
                v.getLocationString().setTextColor(getResources().getColor(R.color.textColor));
                v.getDeleteButton().setBackgroundResource(R.drawable.btn_unpressed);
            }

            view.setBackgroundResource(R.drawable.button_pressed);
            AutofitTextView textView = view.findViewById(R.id.locationName);
            AutofitTextView textView1 = view.findViewById(R.id.locationDetails);
            textView.setTextColor(getResources().getColor(R.color.colorBackground));
            textView1.setTextColor(getResources().getColor(R.color.colorBackground));
        });
    }

    private void requestDeleteAddress(int id) {
        binding.progressBar.setVisibility(View.VISIBLE);
        request.deleteAddress(id, new RequestListener<Resp>() {
            @Override
            public void onFinish() {
                super.onFinish();
                binding.progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onSuccess(Resp resp) {
                super.onSuccess(resp);
                if (resp.status) {
                    Toast.makeText(ActivityChooseAddress.this, "تم حذف العنوان بنجاح", Toast.LENGTH_SHORT).show();
                    requestAddresses();
                }
            }

            @Override
            public void onFailed(String messages) {
                super.onFailed(messages);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        requestAddresses();
    }
}