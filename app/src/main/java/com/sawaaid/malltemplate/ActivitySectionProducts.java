package com.sawaaid.malltemplate;

import static com.sawaaid.malltemplate.adapter.AdapterSubSections.viewArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.View;

import com.sawaaid.malltemplate.adapter.AdapterListener;
import com.sawaaid.malltemplate.adapter.AdapterProducts;
import com.sawaaid.malltemplate.adapter.AdapterSubSections;
import com.sawaaid.malltemplate.connection.Request;
import com.sawaaid.malltemplate.connection.RequestListener;
import com.sawaaid.malltemplate.connection.response.RespProduct;
import com.sawaaid.malltemplate.connection.response.RespSubSection;
import com.sawaaid.malltemplate.data.Constant;
import com.sawaaid.malltemplate.data.DataApp;
import com.sawaaid.malltemplate.databinding.ActivitySectionProductsBinding;
import com.sawaaid.malltemplate.model.Product;
import com.sawaaid.malltemplate.model.SubSection;
import com.sawaaid.malltemplate.model.ViewArrayModel;

import java.util.List;

import me.grantland.widget.AutofitTextView;


public class ActivitySectionProducts extends AppCompatActivity {

    AdapterProducts adapterProducts;
    Request request;
    ActivitySectionProductsBinding binding;
    AdapterSubSections adapterSubSections;
    int sectionId, subSectionId;
    private boolean allLoaded = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySectionProductsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        sectionId = getIntent().getIntExtra("SECTION_ID", 0);

        initComponent();

        requestAction(1, 0);
    }

    private void requestAction(int page_no, int sub_section) {
        adapterProducts.setLoadingOrFailed(null);
        requestSectionProducts(sectionId, page_no, sub_section);
    }

    private void initComponent() {
        request = new Request();
        binding.noProductsInSectionTextView.setVisibility(View.INVISIBLE);
        binding.productsRecyclerView.setLayoutManager(new GridLayoutManager(this, 2, LinearLayoutManager.VERTICAL, false));
        binding.productsRecyclerView.setHasFixedSize(true);
        adapterProducts = new AdapterProducts(this, binding.productsRecyclerView, Constant.LISTING_PAGE);
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
                    requestAction(next_page, subSectionId);
                }
            }
        });

        requestSubSections(sectionId);

    }

    private void requestSubSections(int sectionId) {
        request.subSection(String.valueOf(sectionId), new RequestListener<RespSubSection>() {
            @Override
            public void onFinish() {
                super.onFinish();
            }

            @Override
            public void onSuccess(RespSubSection resp) {
                super.onSuccess(resp);
                displaySubSections(resp.data);
                DataApp.dao().deleteSubSection(String.valueOf(sectionId));
                DataApp.dao().insertSubSections(resp.data);
            }

            @Override
            public void onFailed(String messages) {
                super.onFailed(messages);
                List<SubSection> subSections = DataApp.dao().getSubSections(String.valueOf(sectionId));
                if (subSections.size() > 0) {
                    displaySubSections(subSections);
                }
            }
        });
    }

    private void displaySubSections(List<SubSection> data) {
        adapterSubSections = new AdapterSubSections(data, this);
        binding.secondarySectionsRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        binding.secondarySectionsRecyclerView.setAdapter(adapterSubSections);
        binding.secondarySectionsRecyclerView.setOnFlingListener(null);
        binding.secondarySectionsRecyclerView.setItemViewCacheSize(50);

        adapterSubSections.setOnItemClickListener((view, obj, position) -> {
            adapterProducts.resetListData();
            requestAction(1, obj.id);

            for (ViewArrayModel v : viewArrayList) {
                v.getLytParent().setBackgroundResource(R.drawable.btn_unpressed);
                v.getSubSectionName().setTextColor(getResources().getColor(R.color.textColor));
            }

            view.setBackgroundResource(R.drawable.button_pressed);
            AutofitTextView textView = view.findViewById(R.id.nameTextView);
            textView.setTextColor(getResources().getColor(R.color.colorBackground));
        });
    }

    private void requestSectionProducts(int sectionId, int page_no, int subSectionId) {
        request.sectionProducts(String.valueOf(page_no), String.valueOf(sectionId), String.valueOf(subSectionId), new RequestListener<RespProduct>() {
            @Override
            public void onFinish() {
                super.onFinish();
            }

            @Override
            public void onSuccess(RespProduct resp) {
                super.onSuccess(resp);
                allLoaded = resp.data.size() < Constant.LISTING_PAGE;
                displayApiResult(resp.data);
                DataApp.dao().deleteSectionProducts(String.valueOf(sectionId), String.valueOf(subSectionId));
                DataApp.dao().insertProduct(resp.data);
            }

            @Override
            public void onFailed(String messages) {
                super.onFailed(messages);
                List<Product> productList = DataApp.dao().getSectionProducts(String.valueOf(sectionId), String.valueOf(subSectionId));
                if (productList.size() > 0) {
                    displayApiResult(productList);
                }
            }
        });
    }

    private void displayApiResult(List<Product> data) {
        adapterProducts.insertData(data);

    }
}