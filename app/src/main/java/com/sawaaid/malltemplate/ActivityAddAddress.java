package com.sawaaid.malltemplate;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.sawaaid.malltemplate.connection.Request;
import com.sawaaid.malltemplate.connection.RequestListener;
import com.sawaaid.malltemplate.connection.response.Resp;
import com.sawaaid.malltemplate.data.DataApp;
import com.sawaaid.malltemplate.databinding.ActivityAddAddressBinding;


public class ActivityAddAddress extends AppCompatActivity {

    ActivityAddAddressBinding binding;
    Request request;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddAddressBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initComponent();
    }

    private void initComponent() {
        request = new Request();

        binding.backButton.setOnClickListener(view -> onBackPressed());
        binding.saveLocationFinishButton.setOnClickListener(view -> {
            if (TextUtils.isEmpty(binding.addressDetailsEditText.getText().toString()) ||
                    TextUtils.isEmpty(binding.addressTypeEditText.getText().toString())
            ) {
                Toast.makeText(this, "أحد الحقول فارغ", Toast.LENGTH_LONG).show();
            } else {
                requestAddAddress(binding.addressTypeEditText.getText().toString(), binding.addressDetailsEditText.getText().toString());
            }
        });
    }

    private void requestAddAddress(String locationName, String locationString) {
        binding.progressBar.setVisibility(View.VISIBLE);
        request.insertAddress(locationString, locationName, String.valueOf(DataApp.global().getUser().id), new RequestListener<Resp>() {
            @Override
            public void onFinish() {
                super.onFinish();
                binding.progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onSuccess(Resp resp) {
                super.onSuccess(resp);
                if (resp.status) {
                    Toast.makeText(ActivityAddAddress.this, "تم إضافة عنوان بنجاح", Toast.LENGTH_LONG).show();
                    finish();
                }
            }

            @Override
            public void onFailed(String messages) {
                super.onFailed(messages);
            }
        });
    }
}