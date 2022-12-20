package com.sawaaid.malltemplate;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.View;


import com.sawaaid.malltemplate.adapter.AdapterListener;
import com.sawaaid.malltemplate.adapter.AdapterProducts;
import com.sawaaid.malltemplate.connection.Request;
import com.sawaaid.malltemplate.connection.RequestListener;
import com.sawaaid.malltemplate.connection.response.RespProduct;
import com.sawaaid.malltemplate.data.Constant;
import com.sawaaid.malltemplate.databinding.ActivitySearchBinding;
import com.sawaaid.malltemplate.model.Product;

import java.util.List;

public class ActivitySearch extends AppCompatActivity {
    AdapterProducts adapterProducts;
    String value;
    ActivitySearchBinding binding;
    Request request;
    private boolean allLoaded = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySearchBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        value = getIntent().getStringExtra("WORD");

        initComponent();

        requestAction(1, value);
    }


    private void initComponent() {
        request = new Request();
        binding.searchEdit.setIconifiedByDefault(false);
        binding.searchEdit.setQuery(value, false);

        binding.noResultsTextView.setVisibility(View.INVISIBLE);
        binding.progressBar.setVisibility(View.INVISIBLE);


        binding.searchEdit.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                value = query;
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                return true;
            }
        });
        binding.ProductsGrid.setLayoutManager(new GridLayoutManager(this, 2, LinearLayoutManager.VERTICAL, false));
        binding.ProductsGrid.setHasFixedSize(true);
        adapterProducts = new AdapterProducts(this, binding.ProductsGrid, Constant.LISTING_PAGE);
        binding.ProductsGrid.setAdapter(adapterProducts);
        adapterProducts.setListener(new AdapterListener<Product>() {
            @Override
            public void onClick(View view, String type, Product obj, int position) {
                super.onClick(view, type, obj, position);
            }

            @Override
            public void onSavedClick(View view, boolean saved, Product obj, int position) {
                super.onSavedClick(view, saved, obj, position);
            }

            @Override
            public void onLoadMore(int page) {
                super.onLoadMore(page);
                if (allLoaded) {
                    adapterProducts.setLoaded();
                } else {
                    int next_page = page + 1;
                    requestAction(next_page, value);
                }
            }
        });


    }

    private void requestAction(int page_no, String value) {
        adapterProducts.setLoadingOrFailed(null);
        requestSearch(page_no, value);
    }

    private void requestSearch(int page_no, String value) {
        request.search(String.valueOf(page_no), value, new RequestListener<RespProduct>() {
            @Override
            public void onFinish() {
                super.onFinish();
            }

            @Override
            public void onSuccess(RespProduct resp) {
                super.onSuccess(resp);
                allLoaded = resp.data.size() < Constant.LISTING_PAGE;
                displayApiResult(resp.data);
            }

            @Override
            public void onFailed(String messages) {
                super.onFailed(messages);
            }
        });
    }

    private void displayApiResult(List<Product> data) {
        adapterProducts.insertData(data);
    }
}