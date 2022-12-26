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
import com.sawaaid.malltemplate.databinding.ActivityActitvitySignUpBinding;


public class ActivitySignUp extends AppCompatActivity {

    ActivityActitvitySignUpBinding binding;
    Request request;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actitvity_sign_up);

        initComponent();
    }

    private void initComponent() {
        request = new Request();
        binding.backButton.setOnClickListener(view -> onBackPressed());
        binding.progressBar.setVisibility(View.INVISIBLE);
        binding.signUpButton.setOnClickListener(view -> {
            if (TextUtils.isEmpty(binding.NameEditText.getText().toString()) ||
                    TextUtils.isEmpty(binding.PasswordEditText.getText().toString()) ||
                    TextUtils.isEmpty(binding.ConfirmPasswordEditText.getText().toString())) {
                Toast.makeText(this, "أحد الحقول فارغة، يرجى تعبئة الحقول", Toast.LENGTH_LONG).show();
            } else {
                delayAndRequest();
            }
        });
    }

    private void delayAndRequest() {
        String email, phoneNumber, token;
        if (TextUtils.isEmpty(binding.EmailEditText.getText().toString())) {
            email = "0";
        } else {
            email = binding.EmailEditText.getText().toString();
        }

        if (TextUtils.isEmpty(binding.phoneNumberEditText.getText().toString())) {
            phoneNumber = "0";
        } else {
            phoneNumber = binding.phoneNumberEditText.getText().toString();
        }

        token = DataApp.pref().getFcmRegId();

        request.signUp(email, binding.NameEditText.getText().toString(),
                binding.PasswordEditText.getText().toString(), phoneNumber, token, new RequestListener<RespUser>() {
                    @Override
                    public void onFinish() {
                        super.onFinish();
                    }

                    @Override
                    public void onSuccess(RespUser resp) {
                        super.onSuccess(resp);
                        Toast.makeText(ActivitySignUp.this, "تم تسجيل الدخول بنجاح", Toast.LENGTH_LONG).show();
                        DataApp.global().setUser(resp.data);
                        Intent intent = new Intent(ActivitySignUp.this, ActivityMain.class);
                        new Handler(ActivitySignUp.this.getMainLooper()).postDelayed(() -> (ActivitySignUp.this).startActivity(intent), 1000);
                    }

                    @Override
                    public void onFailed(String messages) {
                        super.onFailed(messages);
                        Log.d("TAGbv", "onFailed: " + messages);
                    }
                });
    }
}