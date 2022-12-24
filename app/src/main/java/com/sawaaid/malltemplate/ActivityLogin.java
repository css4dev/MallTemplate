package com.sawaaid.malltemplate;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.sawaaid.malltemplate.connection.Request;
import com.sawaaid.malltemplate.connection.RequestListener;
import com.sawaaid.malltemplate.connection.response.RespUser;
import com.sawaaid.malltemplate.data.DataApp;
import com.sawaaid.malltemplate.databinding.ActivityLoginBinding;

public class ActivityLogin extends AppCompatActivity {

    Request request;
    ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initComponent();
    }

    private void initComponent() {
        request = new Request();
        binding.SignUpTextView.setOnClickListener(view -> startActivity(new Intent(ActivityLogin.this, ActivitySignUp.class)));

        binding.btnSubmit.setOnClickListener(v -> {
            if (TextUtils.isEmpty(binding.emailTxt.getText().toString()) || TextUtils.isEmpty(binding.passwordTxt.getText().toString())) {
                Toast.makeText(this, "أحد الحقول فارغة، يرجى تعبئة الحقول", Toast.LENGTH_LONG).show();
            } else {
                delayAndRequest();
            }
        });
    }

    private void delayAndRequest() {
        new Handler(this.getMainLooper()).postDelayed(() -> requestUserLogin(binding.emailTxt.getText().toString(), binding.passwordTxt.getText().toString()), 250);
    }

    private void requestUserLogin(String email, String password) {
        binding.progressBar.setVisibility(View.VISIBLE);
        request.login(email, password, new RequestListener<RespUser>() {
            @Override
            public void onFinish() {
                super.onFinish();
                binding.progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onSuccess(RespUser resp) {
                super.onSuccess(resp);
                Toast.makeText(ActivityLogin.this, "تم تسجيل الدخول بنجاح", Toast.LENGTH_LONG).show();
                DataApp.global().setUser(resp.data);
                Intent intent = new Intent(ActivityLogin.this, ActivityMain.class);
                new Handler(ActivityLogin.this.getMainLooper()).postDelayed(() -> (ActivityLogin.this).startActivity(intent), 1000);

            }

            @Override
            public void onFailed(String messages) {
                super.onFailed(messages);
                if (messages == null) {
                    Toast.makeText(ActivityLogin.this, "أحد المعلومات خاطئة", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(ActivityLogin.this, "" + messages, Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}