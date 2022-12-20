package com.sawaaid.malltemplate.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.SearchView;

import com.sawaaid.malltemplate.ActivitySectionProducts;
import com.sawaaid.malltemplate.R;
import com.sawaaid.malltemplate.adapter.AdapterNewProducts;
import com.sawaaid.malltemplate.adapter.AdapterSections;
import com.sawaaid.malltemplate.adapter.AdapterSpecialProducts;
import com.sawaaid.malltemplate.adapter.ViewPager2Adapter;
import com.sawaaid.malltemplate.connection.Request;
import com.sawaaid.malltemplate.connection.RequestListener;
import com.sawaaid.malltemplate.connection.response.RespAds;
import com.sawaaid.malltemplate.connection.response.RespProduct;
import com.sawaaid.malltemplate.connection.response.RespSections;
import com.sawaaid.malltemplate.data.DataApp;
import com.sawaaid.malltemplate.model.Ads;
import com.sawaaid.malltemplate.model.Product;
import com.sawaaid.malltemplate.model.Section;

import java.util.List;


public class FragmentHome extends Fragment {

    View rootView;
    Context context;
    RecyclerView sections_recyclerview, newProductsRecyclerView, offerProductsRecyclerView;
    AdapterSections adapterSections;
    AdapterNewProducts adapterNewProducts;
    Request request;
    ProgressBar progressBar;
    ViewPager2Adapter viewPager2Adapter;
    ViewPager2 viewPager2;
    LinearLayout sliderDots;
    Runnable sliderRunnable;
    AdapterSpecialProducts adapterSpecialProducts;
    private final Handler sliderHandler = new Handler();


    public FragmentHome() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_home, container, false);

        initComponents();

        return rootView;
    }

    private void initComponents() {

        request = new Request();
        progressBar = rootView.findViewById(R.id.progress_bar);
        sections_recyclerview = rootView.findViewById(R.id.sections_recyclerview);
        viewPager2 = rootView.findViewById(R.id.viewPager);
        sliderDots = rootView.findViewById(R.id.sliderDots);
        newProductsRecyclerView = rootView.findViewById(R.id.newProductsRecyclerView);
        offerProductsRecyclerView = rootView.findViewById(R.id.offerProductsRecyclerView);

        initViewPager();
        requestSections();
        requestAds();
        requestNewProducts();
        requestSpecialProducts();

    }

    private void requestSpecialProducts() {
        request.specialProducts(new RequestListener<RespProduct>() {
            @Override
            public void onFinish() {
                super.onFinish();
            }

            @Override
            public void onSuccess(RespProduct resp) {
                super.onSuccess(resp);
                displayDataSpecialProducts(resp.data);
            }

            @Override
            public void onFailed(String messages) {
                super.onFailed(messages);
            }
        });
    }

    private void displayDataSpecialProducts(List<Product> data) {
        addRecyclerWithAdapterSpecialProducts(data, offerProductsRecyclerView);
    }

    private void addRecyclerWithAdapterSpecialProducts(List<Product> data, RecyclerView recyclerView) {
        adapterSpecialProducts = new AdapterSpecialProducts(data, context);
        recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setAdapter(adapterSpecialProducts);
        recyclerView.setOnFlingListener(null);
        recyclerView.setItemViewCacheSize(50);

        adapterSpecialProducts.setOnItemClickListener((view, obj, position) -> {

        });
    }

    private void requestNewProducts() {
        request.newProducts(new RequestListener<RespProduct>() {
            @Override
            public void onFinish() {
                super.onFinish();
            }

            @Override
            public void onSuccess(RespProduct resp) {
                super.onSuccess(resp);
                displayDataNewProducts(resp.data);
            }

            @Override
            public void onFailed(String messages) {
                super.onFailed(messages);
            }
        });
    }

    private void displayDataNewProducts(List<Product> data) {
        addRecyclerWithAdapterNewProducts(newProductsRecyclerView, data);
    }

    private void addRecyclerWithAdapterNewProducts(RecyclerView recyclerView, List<Product> data) {
        adapterNewProducts = new AdapterNewProducts(data, context);
        recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setAdapter(adapterNewProducts);
        recyclerView.setOnFlingListener(null);
        recyclerView.setItemViewCacheSize(50);

        adapterNewProducts.setOnItemClickListener((view, obj, position) -> {

        });
    }


    private void initViewPager() {
        viewPager2.setClipToPadding(false);
        viewPager2.setClipChildren(false);
        viewPager2.setOffscreenPageLimit(3);
        viewPager2.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);
        CompositePageTransformer compositePageTransformer = new CompositePageTransformer();
        compositePageTransformer.addTransformer(new MarginPageTransformer(40));
        compositePageTransformer.addTransformer((page, position) -> {
            float r = 1 - Math.abs(position);
            page.setScaleY(0.85f + r * 0.15f);
        });
        viewPager2.setPageTransformer(compositePageTransformer);
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                sliderHandler.removeCallbacks(sliderRunnable);
                sliderHandler.postDelayed(sliderRunnable, 5000);
                setDots(viewPager2Adapter.getItemCount(), position);
            }
        });
    }

    private void setDots(int dotsCount, int position) {
        sliderDots.removeAllViewsInLayout();

        if (dotsCount != 0) {
            ImageView[] dots = new ImageView[dotsCount];

            for (int i = 0; i < dotsCount; i++) {
                try {
                    dots[i] = new ImageView(getContext());
                    dots[i].setImageDrawable(ContextCompat.getDrawable(context, R.drawable.active_dot));
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    params.setMargins(8, 0, 8, 0);
                    sliderDots.addView(dots[i], params);
                } catch (Throwable ignored) {
                }
            }

            try {
                dots[0].setImageDrawable(ContextCompat.getDrawable(context, R.drawable.nonactive_dot));

            } catch (Throwable ignored) {
            }

            for (int i = 0; i < dotsCount; i++) {
                try {
                    dots[i].setImageDrawable(ContextCompat.getDrawable(context, R.drawable.active_dot));
                } catch (Throwable ignored) {

                }
            }
            try {
                dots[position].setImageDrawable(ContextCompat.getDrawable(context, R.drawable.nonactive_dot));
            } catch (Throwable ignored) {

            }


        }
    }


    private void requestSections() {
        progressBar.setVisibility(View.VISIBLE);
        request.sections(new RequestListener<RespSections>() {
            @Override
            public void onFinish() {
                super.onFinish();
                progressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onSuccess(RespSections resp) {
                super.onSuccess(resp);
                displayData(resp.data);
                DataApp.dao().deleteSections();
                DataApp.dao().insertSections(resp.data);
            }

            @Override
            public void onFailed(String messages) {
                super.onFailed(messages);
                List<Section> sections = DataApp.dao().getSections();
                if (sections.size() != 0)
                    displayData(sections);
            }
        });
    }

    private void displayData(List<Section> data) {
        addRecyclerWithAdapter(sections_recyclerview, data);
    }

    private void addRecyclerWithAdapter(RecyclerView recyclerView, List<Section> data) {
        adapterSections = new AdapterSections(data, context);
        recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setAdapter(adapterSections);
        recyclerView.setOnFlingListener(null);
        recyclerView.setItemViewCacheSize(50);

        adapterSections.setOnItemClickListener((view, obj, position) -> {
            Intent intent = new Intent(context, ActivitySectionProducts.class);
            intent.putExtra("SECTION_ID", obj.id);
            startActivity(intent);
        });
    }

    private void requestAds() {
        request.ads(new RequestListener<RespAds>() {
            @Override
            public void onFinish() {
                super.onFinish();
            }

            @Override
            public void onSuccess(RespAds resp) {
                super.onSuccess(resp);
                setTimerRunnable(resp.data);
                viewPager2Adapter = new ViewPager2Adapter(context, resp.data);
                viewPager2.setAdapter(viewPager2Adapter);
                DataApp.dao().deleteAds();
                DataApp.dao().insertAds(resp.data);
            }

            @Override
            public void onFailed(String messages) {
                super.onFailed(messages);

                List<Ads> entityAdsList = DataApp.dao().getAds();
                if (entityAdsList.size() != 0) {
                    setTimerRunnable(entityAdsList);
                    viewPager2Adapter = new ViewPager2Adapter(context, entityAdsList);
                    viewPager2.setAdapter(viewPager2Adapter);
                }
            }
        });
    }

    private void setTimerRunnable(List<Ads> data) {
        sliderRunnable = () -> {
            if (viewPager2.getCurrentItem() == data.size() - 1) {
                viewPager2.setCurrentItem(0);

            } else {

                viewPager2.setCurrentItem(viewPager2.getCurrentItem() + 1);
            }
        };
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        this.context = null;
    }
}