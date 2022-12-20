package com.sawaaid.malltemplate.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sawaaid.malltemplate.R;
import com.sawaaid.malltemplate.model.Product;
import com.sawaaid.malltemplate.utils.Tools;

import java.util.List;

import me.grantland.widget.AutofitTextView;

public class AdapterSpecialProducts extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final List<Product> items;
    private AdapterSpecialProducts.OnItemClickListener onItemClickListener;
    Context context;


    public interface OnItemClickListener {
        void onItemClick(View view, Product obj, int position);
    }

    public void setOnItemClickListener(AdapterSpecialProducts.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView name_textview;
        public AutofitTextView price, old_price;
        public ImageView image_imageview;
        public RelativeLayout lytParent, old_price_relative;
        public LinearLayout add_to_basket_linear;


        public ViewHolder(View v) {
            super(v);
            name_textview = v.findViewById(R.id.name);
            image_imageview = v.findViewById(R.id.image);
            lytParent = v.findViewById(R.id.lytParent);
            price = v.findViewById(R.id.price);
            old_price = v.findViewById(R.id.old_price);
            old_price_relative = v.findViewById(R.id.old_price_relative);
            add_to_basket_linear = v.findViewById(R.id.add_to_basket_linear);


        }
    }

    public AdapterSpecialProducts(List<Product> items, Context context) {
        this.items = items;
        this.context = context;

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product_home, parent, false);

        vh = new AdapterSpecialProducts.ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof AdapterSpecialProducts.ViewHolder) {
            AdapterSpecialProducts.ViewHolder vItem = (AdapterSpecialProducts.ViewHolder) holder;
            final Product p = items.get(position);

            vItem.name_textview.setText(p.name);
            Tools.displayImage(context, vItem.image_imageview, p.photo);
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
