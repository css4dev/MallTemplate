package com.sawaaid.malltemplate.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sawaaid.malltemplate.R;
import com.sawaaid.malltemplate.model.OrderDetails;

import java.util.List;

import me.grantland.widget.AutofitTextView;

public class AdapterOrderDetails extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final List<OrderDetails> items;
    Context context;


    public static class ViewHolder extends RecyclerView.ViewHolder {
        View mView;
        AutofitTextView productName, productPrice,
                productQuantity;
        ImageView productImage;


        public ViewHolder(View v) {
            super(v);

            mView = itemView;
            productName = mView.findViewById(R.id.productNameTextView);
            productPrice = mView.findViewById(R.id.productPriceTextView);
            productQuantity = mView.findViewById(R.id.quantityTextView);
            productImage = mView.findViewById(R.id.productImage);

        }
    }

    public AdapterOrderDetails(List<OrderDetails> items, Context context) {
        this.items = items;
        this.context = context;

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order_details, parent, false);

        vh = new AdapterOrderDetails.ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof AdapterOrderDetails.ViewHolder) {
            AdapterOrderDetails.ViewHolder vItem = (AdapterOrderDetails.ViewHolder) holder;
            final OrderDetails p = items.get(position);

            vItem.productName.setText(p.name);
            vItem.productPrice.setText(p.onePrice + "");
            vItem.productQuantity.setText(p.quantity + "");

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
