package com.sawaaid.malltemplate.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sawaaid.malltemplate.ActivitySectionProducts;
import com.sawaaid.malltemplate.R;
import com.sawaaid.malltemplate.adapter.AdapterSections;
import com.sawaaid.malltemplate.adapter.AdapterSectionsFragment;
import com.sawaaid.malltemplate.connection.Request;
import com.sawaaid.malltemplate.connection.RequestListener;
import com.sawaaid.malltemplate.connection.response.RespSections;
import com.sawaaid.malltemplate.data.DataApp;
import com.sawaaid.malltemplate.databinding.FragmentSectionsBinding;
import com.sawaaid.malltemplate.model.Section;

import java.util.List;

public class FragmentSections extends Fragment {

    FragmentSectionsBinding binding;
    AdapterSectionsFragment adapterSectionsFragment;
    Request request;
    Context context;

    public FragmentSections() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentSectionsBinding.inflate(getLayoutInflater());
        initComponent();

        return binding.getRoot();
    }

    private void initComponent() {
        request = new Request();
        requestSections();
    }

    private void requestSections() {
        binding.progressBar.setVisibility(View.VISIBLE);
        request.sections(new RequestListener<RespSections>() {
            @Override
            public void onFinish() {
                super.onFinish();
                binding.progressBar.setVisibility(View.INVISIBLE);
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
        addRecyclerWithAdapter(binding.categoriesRecyclerView, data);
    }

    private void addRecyclerWithAdapter(RecyclerView recyclerView, List<Section> data) {
        adapterSectionsFragment = new AdapterSectionsFragment(data, context);
        recyclerView.setLayoutManager(new GridLayoutManager(context, 3));
        recyclerView.setAdapter(adapterSectionsFragment);
        recyclerView.setOnFlingListener(null);
        recyclerView.setItemViewCacheSize(50);

        adapterSectionsFragment.setOnItemClickListener((view, obj, position) -> {
            Intent intent = new Intent(context, ActivitySectionProducts.class);
            intent.putExtra("SECTION_ID", obj.id);
            startActivity(intent);
        });
    }

    @Override
    public void onDetach() {
        super.onDetach();
        this.context = null;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }
}