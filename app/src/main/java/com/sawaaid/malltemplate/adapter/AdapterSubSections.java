package com.sawaaid.malltemplate.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.sawaaid.malltemplate.R;
import com.sawaaid.malltemplate.model.SubSection;
import java.util.List;

import me.grantland.widget.AutofitTextView;

public class AdapterSubSections extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final List<SubSection> items;
    private AdapterSubSections.OnItemClickListener onItemClickListener;
    Context context;


    public interface OnItemClickListener {
        void onItemClick(View view, SubSection obj, int position);
    }

    public void setOnItemClickListener(AdapterSubSections.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public AutofitTextView section_title;
        public RelativeLayout lytParent;


        public ViewHolder(View v) {
            super(v);
            section_title = v.findViewById(R.id.nameTextView);
            lytParent = v.findViewById(R.id.lytParent);


        }
    }

    public AdapterSubSections(List<SubSection> items, Context context) {
        this.items = items;
        this.context = context;

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sub_section, parent, false);

        vh = new AdapterSubSections.ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof AdapterSubSections.ViewHolder) {
            AdapterSubSections.ViewHolder vItem = (AdapterSubSections.ViewHolder) holder;
            final SubSection p = items.get(position);

            vItem.section_title.setText(p.name);
            vItem.lytParent.setOnClickListener(view -> onItemClickListener.onItemClick(view, p, position));


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
