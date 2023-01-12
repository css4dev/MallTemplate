package com.sawaaid.malltemplate;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.messaging.FirebaseMessaging;
import com.sawaaid.malltemplate.connection.Request;
import com.sawaaid.malltemplate.connection.RequestListener;
import com.sawaaid.malltemplate.connection.response.RespProduct;
import com.sawaaid.malltemplate.data.DataApp;
import com.sawaaid.malltemplate.fragment.FragmentCart;
import com.sawaaid.malltemplate.fragment.FragmentFavorite;
import com.sawaaid.malltemplate.fragment.FragmentHome;
import com.sawaaid.malltemplate.fragment.FragmentSections;
import com.sawaaid.malltemplate.fragment.FragmentUser;

public class ActivityMain extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    FragmentHome fragmentHome;
    SearchView searchEdit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseMessaging.getInstance().subscribeToTopic("NEWS");

        if (DataApp.pref().isFirstLaunch()) {
            ActivityOnBoarding.navigate(this);
            DataApp.pref().setFirstLaunch(false);
        }

        initComponents();

    }

    private void initComponents() {
        replaceFragment(new FragmentHome(), "HOME");
        fragmentHome = new FragmentHome();
        bottomNavigationView = findViewById(R.id.bottomNavigationMain);
        searchEdit = findViewById(R.id.searchEdit);

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

        searchEdit.setIconifiedByDefault(false);
        searchEdit.setQuery("", false);
        searchEdit.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Intent intent = new Intent(getApplicationContext(), ActivitySearch.class);
                Bundle bundle = new Bundle();
                bundle.putString("WORD", query);
                bundle.putString("SECTION_ID", "-1");
                intent.putExtras(bundle);
                startActivity(intent);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                return true;
            }
        });

    }

    private void replaceFragment(Fragment fragment, String tag) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container, fragment, tag);
        fragmentTransaction.commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
        searchEdit.setQuery("", false);
        searchEdit.clearFocus();
    }
}