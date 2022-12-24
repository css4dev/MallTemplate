package com.sawaaid.malltemplate.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.IdRes;

import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.sawaaid.malltemplate.R;
import com.sawaaid.malltemplate.data.DataApp;

public class BottomMenuHelper {

    public static void showBadge(Context context, BottomNavigationView
            bottomNavigationView, @IdRes int itemId) {
        removeBadge(bottomNavigationView, itemId);
        BottomNavigationItemView itemView = bottomNavigationView.findViewById(itemId);
        View badge = LayoutInflater.from(context).inflate(R.layout.basket_badge, bottomNavigationView, false);
        TextView text = badge.findViewById(R.id.text1);

        itemView.addView(badge);

        int productCount = DataApp.dao().getProductBasketCount();
        if (productCount <= 0) {
            text.setVisibility(View.INVISIBLE);

        } else {
            text.setVisibility(View.VISIBLE);
            text.setText(String.valueOf(productCount));

        }
    }

    public static void removeBadge(BottomNavigationView bottomNavigationView, @IdRes int itemId) {
        BottomNavigationItemView itemView = bottomNavigationView.findViewById(itemId);
        if (itemView.getChildCount() == 3) {
            itemView.removeViewAt(2);
        }
    }
}
