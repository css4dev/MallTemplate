package com.sawaaid.malltemplate;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.sawaaid.malltemplate.connection.Request;
import com.sawaaid.malltemplate.connection.RequestListener;
import com.sawaaid.malltemplate.connection.response.RespUser;
import com.sawaaid.malltemplate.data.DataApp;
import com.sawaaid.malltemplate.databinding.ActivityProfileBinding;

public class ActivityProfile extends AppCompatActivity {

    ActivityProfileBinding binding;
    Request request;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initComponent();
    }

    private void initComponent() {
        binding.progressBar.setVisibility(View.INVISIBLE);
        request = new Request();
        binding.NameEditText.setText(DataApp.global().getUser().name);
        binding.EmailEditText.setText(DataApp.global().getUser().email);
        binding.phoneNumberEditText.setText(DataApp.global().getUser().phoneNumber);

        binding.saveButton.setOnClickListener(view -> {
            if (TextUtils.isEmpty(binding.NameEditText.getText().toString()))
                Toast.makeText(this, "حقل الاسم مطلوب، لا يمكن أن يكون فارغاً", Toast.LENGTH_SHORT).show();
            else
                updateInfo();
        });
    }

    private void updateInfo() {
        String password;
        if (TextUtils.isEmpty(binding.PasswordEditText.getText().toString())) {
            password = "";
        } else {
            password = binding.PasswordEditText.getText().toString();
        }
        binding.progressBar.setVisibility(View.VISIBLE);
        request.editProfile(DataApp.global().getUser().id,
                binding.EmailEditText.getText().toString(),
                binding.NameEditText.getText().toString(),
                password,
                binding.phoneNumberEditText.getText().toString(),
                DataApp.pref().getFcmRegId(), new RequestListener<RespUser>() {
                    @Override
                    public void onFinish() {
                        super.onFinish();
                        binding.progressBar.setVisibility(View.INVISIBLE);
                    }

                    @Override
                    public void onSuccess(RespUser resp) {
                        super.onSuccess(resp);
                        Toast.makeText(ActivityProfile.this, "تم التعديل بنجاح", Toast.LENGTH_LONG).show();
                        DataApp.global().setUser(resp.data);

                    }

                    @Override
                    public void onFailed(String messages) {
                        super.onFailed(messages);
                    }
                });
    }
}