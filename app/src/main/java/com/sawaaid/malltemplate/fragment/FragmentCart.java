package com.sawaaid.malltemplate.fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.sawaaid.malltemplate.ActivityMain;
import com.sawaaid.malltemplate.R;
import com.sawaaid.malltemplate.adapter.AdapterCart;
import com.sawaaid.malltemplate.connection.Request;
import com.sawaaid.malltemplate.connection.RequestListener;
import com.sawaaid.malltemplate.connection.response.RespProduct;
import com.sawaaid.malltemplate.data.DataApp;
import com.sawaaid.malltemplate.databinding.FragmentCartBinding;
import com.sawaaid.malltemplate.model.Product;
import com.sawaaid.malltemplate.room.entity.EntityBasket;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class FragmentCart extends Fragment {

    View rootView;
    Context context;
    List<EntityBasket> entityBasketList = DataApp.dao().getEntityBasket();
    List<String> productsId = new ArrayList<>();
    Request request;
    FragmentCartBinding binding;
    public static double BillPrice;
    AdapterCart adapterCart;
    BottomNavigationView bottomNavigationView;
    String fare = "10";
    String minimumOrderPrice = "50";
    ActivityMain mainActivity;
    LinearLayout parent;

    public FragmentCart() {
    }

    public FragmentCart(BottomNavigationView bottomNavigationView) {
        this.bottomNavigationView = bottomNavigationView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        mainActivity = (ActivityMain) getActivity();
        super.onCreate(savedInstanceState);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentCartBinding.inflate(getLayoutInflater());
        rootView = binding.getRoot();

        parent = rootView.findViewById(R.id.parent);
        initComponent();


        return rootView;
    }

    private void initComponent() {
        request = new Request();
        binding.noProductsInBasketTextView.setVisibility(View.GONE);
        requestProducts();
    }

    private void requestProducts() {
        if (entityBasketList.size() > 0) {
            for (int i = 0; i < entityBasketList.size(); i++) {
                productsId.add(String.valueOf(entityBasketList.get(i).productId));
            }
            String ids = productsId.toString();
            ids = ids.replace("[", "");
            ids = ids.replace("]", "");

            request.cartProducts(ids, new RequestListener<RespProduct>() {
                @Override
                public void onFinish() {
                    super.onFinish();
                    binding.progressBar.setVisibility(View.GONE);
                }

                @Override
                public void onSuccess(RespProduct resp) {
                    super.onSuccess(resp);
                    displayData(resp.data);
                    if (resp.data.size() > 0) {
                        binding.noProductsInBasketTextView.setVisibility(View.GONE);
                        calculateBillPrice(fare, minimumOrderPrice);
                    }
                }

                @Override
                public void onFailed(String messages) {
                    super.onFailed(messages);
                }
            });
        } else {
            binding.noProductsInBasketTextView.setVisibility(View.VISIBLE);
            binding.progressBar.setVisibility(View.INVISIBLE);
        }

    }

    private void displayData(List<Product> data) {
        adapterCart = new AdapterCart(data, binding.totalPriceTextView, binding.recyclerView, bottomNavigationView, binding.noProductsInBasketTextView, fare, minimumOrderPrice, parent, binding.fareTextView, mainActivity);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        binding.recyclerView.setAdapter(adapterCart);
        binding.recyclerView.setOnFlingListener(null);
        binding.recyclerView.setItemViewCacheSize(50);
    }

    public void calculateBillPrice(String fare, String minimumOrderPrice) {
        List<EntityBasket> productsEntity = DataApp.dao().getEntityBasket();
        BillPrice = 0;
        for (int i = 0; i < productsEntity.size(); i++) {
            double a = productsEntity.get(i).getQuantity();
            double b = productsEntity.get(i).getPrice();
            BillPrice += a * b;
        }
        Locale locale = new Locale("TR", "TR");
        if (productsEntity.size() > 0) {
            binding.totalPriceTextView.setText(("₺ " + String.format(locale, "%.2f", (BillPrice))));
        } else {
            binding.totalPriceTextView.setText(("₺ " + "0"));

        }
        if (BillPrice > 0)
            checkDeliveryFare(fare, minimumOrderPrice);
    }

    private void checkDeliveryFare(String fare, String minimumOrderPrice) {
        if (BillPrice < Double.parseDouble(minimumOrderPrice)) {
            BillPrice = BillPrice + Double.parseDouble(fare);
            Locale locale = new Locale("TR", "TR");

            binding.totalPriceTextView.setText(("₺ " + String.format(locale, "%.2f", (BillPrice))));

            binding.fareTextView.setText("تم إضافة " + fare + " ليرة تركية على الطلب الأقل من " + minimumOrderPrice);
            binding.fareTextView.setVisibility(View.VISIBLE);
        }
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