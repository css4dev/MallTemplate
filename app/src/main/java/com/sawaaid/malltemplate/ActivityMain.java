package com.sawaaid.malltemplate;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.sawaaid.malltemplate.fragment.FragmentCart;
import com.sawaaid.malltemplate.fragment.FragmentFavorite;
import com.sawaaid.malltemplate.fragment.FragmentHome;
import com.sawaaid.malltemplate.fragment.FragmentSections;
import com.sawaaid.malltemplate.fragment.FragmentUser;

public class ActivityMain extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    FragmentHome fragmentHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initComponents();
    }

    private void initComponents() {
        replaceFragment(new FragmentHome(), "HOME");
        fragmentHome = new FragmentHome();
        bottomNavigationView = findViewById(R.id.bottomNavigationMain);
        bottomNavigationView.setOnItemSelectedListener(item -> {

            if (item.getItemId() == R.id.home_menu) {
                replaceFragment(new FragmentHome(), "HOME");
            } else if (item.getItemId() == R.id.cart_menu) {
                replaceFragment(new FragmentCart(), "CART");
            } else if (item.getItemId() == R.id.sections_menu) {
                replaceFragment(new FragmentSections(), "SECTIONS");
            } else if (item.getItemId() == R.id.favorite_menu) {
                replaceFragment(new FragmentFavorite(), "FAVORITE");
            } else if (item.getItemId() == R.id.profile_menu) {
                replaceFragment(new FragmentUser(), "USER");
            }


            return true;
        });

    }

    private void replaceFragment(Fragment fragment, String tag) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container, fragment, tag);
        fragmentTransaction.commit();
    }

}