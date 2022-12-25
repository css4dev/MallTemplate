package com.sawaaid.malltemplate.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.sawaaid.malltemplate.R;
import com.sawaaid.malltemplate.model.Address;
import com.sawaaid.malltemplate.model.ViewAddressModel;
import com.sawaaid.malltemplate.model.ViewArrayModel;


import java.util.ArrayList;
import java.util.List;

import me.grantland.widget.AutofitTextView;

public class AdapterAddresses extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final List<Address> items;
    private AdapterAddresses.OnItemClickListener onItemClickListener;
    private AdapterAddresses.OnDeleteItemClickListener onDeleteClickListener;
    Context context;
    public static ArrayList<ViewAddressModel> viewArrayListAddress = new ArrayList<>();

    public interface OnItemClickListener {
        void onItemClick(View view, Address obj, int position);
    }

    public void setOnItemClickListener(AdapterAddresses.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnDeleteItemClickListener {
        void onItemClick(View view, Address obj, int position);
    }

    public void setOnDeleteClickListener(AdapterAddresses.OnDeleteItemClickListener onDeleteClickListener) {
        this.onDeleteClickListener = onDeleteClickListener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        View mView;
        AutofitTextView locationName;
        AutofitTextView locationDetails;
        Button deleteButtonRelative;
        LinearLayout lytParent;
        CardView cardView;


        public ViewHolder(View v) {
            super(v);

            mView = itemView;
            locationName = mView.findViewById(R.id.locationName);
            locationDetails = mView.findViewById(R.id.locationDetails);
            deleteButtonRelative = mView.findViewById(R.id.buttonDeleteRelative);
            lytParent = mView.findViewById(R.id.lytParent);
            cardView = mView.findViewById(R.id.card_view);

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
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_address, parent, false);

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
            vItem.deleteButtonRelative.setOnClickListener(view -> onDeleteClickListener.onItemClick(view, p, position));

            if (viewArrayListAddress.size() < items.size()) {
                ViewAddressModel viewArrayModel = new ViewAddressModel();
                viewArrayModel.setCardView(vItem.cardView);
                viewArrayModel.setDeleteButton(vItem.deleteButtonRelative);
                viewArrayModel.setLocationString(vItem.locationDetails);
                viewArrayModel.setLocationName(vItem.locationName);
                viewArrayListAddress.add(viewArrayModel);
            }

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
