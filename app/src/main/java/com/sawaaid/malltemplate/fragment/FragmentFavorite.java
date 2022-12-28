package com.sawaaid.malltemplate.fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sawaaid.malltemplate.adapter.AdapterListener;
import com.sawaaid.malltemplate.adapter.AdapterProducts;
import com.sawaaid.malltemplate.connection.Request;
import com.sawaaid.malltemplate.connection.RequestListener;
import com.sawaaid.malltemplate.connection.response.RespProduct;
import com.sawaaid.malltemplate.data.Constant;
import com.sawaaid.malltemplate.data.DataApp;
import com.sawaaid.malltemplate.databinding.FragmentFavoriteBinding;
import com.sawaaid.malltemplate.model.Product;
import com.sawaaid.malltemplate.room.entity.EntityFavorite;

import java.util.ArrayList;
import java.util.List;


public class FragmentFavorite extends Fragment {
    View rootView;
    Context context;
    List<EntityFavorite> entityFavorite = DataApp.dao().getEntityFavorite();
    List<String> productsId = new ArrayList<>();
    Request request;
    FragmentFavoriteBinding binding;
    AdapterProducts adapterProducts;
    private boolean allLoaded = false;

    public FragmentFavorite() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentFavoriteBinding.inflate(getLayoutInflater());
        rootView = binding.getRoot();

        initComponent();

        requestAction(1);
        return rootView;
    }

    private void requestAction(int page) {
        adapterProducts.setLoadingOrFailed(null);
        requestProducts(page);
    }

    private void initComponent() {
        request = new Request();
        binding.noProductsInFavTextView.setVisibility(View.GONE);
        binding.productsRecyclerView.setLayoutManager(new GridLayoutManager(context, 2, LinearLayoutManager.VERTICAL, false));
        binding.productsRecyclerView.setHasFixedSize(true);
        adapterProducts = new AdapterProducts(context, binding.productsRecyclerView, Constant.LISTING_PAGE);
        binding.productsRecyclerView.setAdapter(adapterProducts);
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
                    requestAction(next_page);
                }
            }
        });
    }

    private void requestProducts(int page) {
        if (entityFavorite.size() > 0) {
            for (int i = 0; i < entityFavorite.size(); i++) {
                productsId.add(String.valueOf(entityFavorite.get(i).productId));
            }
            String ids = productsId.toString();
            ids = ids.replace("[", "");
            ids = ids.replace("]", "");

            request.favProducts(ids, String.valueOf(page), new RequestListener<RespProduct>() {
                @Override
                public void onFinish() {
                    super.onFinish();
                    binding.progressBar.setVisibility(View.GONE);
                }

                @Override
                public void onSuccess(RespProduct resp) {
                    super.onSuccess(resp);
                    allLoaded = resp.data.size() < Constant.LISTING_PAGE;
                    displayData(resp.data);
                    if (resp.data.size() > 0) {
                        binding.noProductsInFavTextView.setVisibility(View.GONE);
                    }
                }

                @Override
                public void onFailed(String messages) {
                    super.onFailed(messages);
                }
            });
        } else {
            binding.noProductsInFavTextView.setVisibility(View.VISIBLE);
            binding.progressBar.setVisibility(View.INVISIBLE);
        }
    }

    private void displayData(List<Product> data) {
        adapterProducts.insertData(data);
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