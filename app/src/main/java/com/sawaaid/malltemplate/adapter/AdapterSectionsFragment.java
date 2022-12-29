package com.sawaaid.malltemplate.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sawaaid.malltemplate.R;
import com.sawaaid.malltemplate.model.Section;
import com.sawaaid.malltemplate.utils.Tools;

import java.util.List;

import me.grantland.widget.AutofitTextView;

public class AdapterSectionsFragment extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final List<Section> items;
    private AdapterSectionsFragment.OnItemClickListener onItemClickListener;
    Context context;


    public interface OnItemClickListener {
        void onItemClick(View view, Section obj, int position);
    }

    public void setOnItemClickListener(AdapterSectionsFragment.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public AutofitTextView section_title;
        public ImageView section_image;
        public RelativeLayout lytParent;


        public ViewHolder(View v) {
            super(v);
            section_title = v.findViewById(R.id.section_title);
            section_image = v.findViewById(R.id.section_image);
            lytParent = v.findViewById(R.id.lytParent);


        }
    }

    public AdapterSectionsFragment(List<Section> items, Context context) {
        this.items = items;
        this.context = context;

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_section, parent, false);

        vh = new AdapterSectionsFragment.ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof AdapterSectionsFragment.ViewHolder) {
            AdapterSectionsFragment.ViewHolder vItem = (AdapterSectionsFragment.ViewHolder) holder;
            final Section p = items.get(position);

            vItem.section_title.setText(p.name);
            Tools.displayImage(context, vItem.section_image, p.photo);
            vItem.lytParent.setOnClickListener(view -> {
                onItemClickListener.onItemClick(view, p, position);
            });


        }


    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

}
