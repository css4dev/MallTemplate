package com.sawaaid.malltemplate;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.app.Activity;
import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sawaaid.malltemplate.databinding.ActivityOnBoardingBinding;

public class ActivityOnBoarding extends AppCompatActivity {

    ActivityOnBoardingBinding binding;
    private String aboutTitleArray[];
    private String aboutDescriptionArray[];
    private TypedArray aboutImagesArray;

    public static void navigate(Activity activity) {
        Intent i = new Intent(activity, ActivityOnBoarding.class);
        activity.startActivity(i);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOnBoardingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        aboutTitleArray = getResources().getStringArray(R.array.about_title_array);
        aboutDescriptionArray = getResources().getStringArray(R.array.about_description_array);
        aboutImagesArray = getResources().obtainTypedArray(R.array.about_images_array);

    }
}