package com.sawaaid.malltemplate.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sawaaid.malltemplate.R;
import com.sawaaid.malltemplate.model.Address;


import java.util.List;

import me.grantland.widget.AutofitTextView;

public class AdapterAddresses extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final List<Address> items;
    private AdapterAddresses.OnItemClickListener onItemClickListener;
    Context context;


    public interface OnItemClickListener {
        void onItemClick(View view, Address obj, int position);
    }

    public void setOnItemClickListener(AdapterAddresses.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        View mView;
        AutofitTextView locationName;
        AutofitTextView locationDetails;
        Button deleteButtonRelative;
        LinearLayout lytParent;


        public ViewHolder(View v) {
            super(v);

            mView = itemView;
            locationName = mView.findViewById(R.id.locationName);
            locationDetails = mView.findViewById(R.id.locationDetails);
            deleteButtonRelative = mView.findViewById(R.id.buttonDeleteRelative);
            lytParent = mView.findViewById(R.id.lytParent);


        }
    }

    public AdapterAddresses(List<Address> items, Context context) {
        this.items = items;
        this.context = context;

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_section, parent, false);

        vh = new AdapterAddresses.ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof AdapterAddresses.ViewHolder) {
            AdapterAddresses.ViewHolder vItem = (AdapterAddresses.ViewHolder) holder;
            final Address p = items.get(position);

            vItem.locationName.setText(p.locationName);
            vItem.locationDetails.setText(p.locationString);
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
