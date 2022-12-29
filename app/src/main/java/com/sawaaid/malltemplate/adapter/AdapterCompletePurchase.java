package com.sawaaid.malltemplate.adapter;

import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.sawaaid.malltemplate.R;
import com.sawaaid.malltemplate.data.DataApp;
import com.sawaaid.malltemplate.model.Product;
import com.sawaaid.malltemplate.room.entity.EntityBasket;
import com.sawaaid.malltemplate.utils.Tools;
import com.sawaaid.malltemplate.widget.ElegantNumberButton;

import java.util.List;

import me.grantland.widget.AutofitTextView;

public class AdapterCompletePurchase extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final List<Product> items;
    private AdapterCompletePurchase.OnItemClickListener onItemClickListener;
    Context context;
    private final List<EntityBasket> basketList = DataApp.dao().getEntityBasket();

    public interface OnItemClickListener {
        void onItemClick(View view, Product obj, int position);
    }

    public void setOnItemClickListener(AdapterCompletePurchase.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        AutofitTextView productName, productPrice, productQuantity;
        ImageView productImage;
        Button buttonDelete;
        ElegantNumberButton elegantNumberButton;
        Button changeQuantityButton;


        public ViewHolder(View v) {
            super(v);
            buttonDelete = v.findViewById(R.id.buttonDelete);
            elegantNumberButton = v.findViewById(R.id.numberButton);
            productName = v.findViewById(R.id.productNameTextView);
            productQuantity = v.findViewById(R.id.quantityTextView);
            productPrice = v.findViewById(R.id.productPriceTextView);
            productImage = v.findViewById(R.id.productImage);
            changeQuantityButton = v.findViewById(R.id.changeQuantityButton);


        }
    }

    public AdapterCompletePurchase(List<Product> items, Context context) {
        this.items = items;
        this.context = context;

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_complete_purchase, parent, false);

        vh = new AdapterCompletePurchase.ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof AdapterCompletePurchase.ViewHolder) {
            AdapterCompletePurchase.ViewHolder vItem = (AdapterCompletePurchase.ViewHolder) holder;
            final Product p = items.get(position);


            if (p.priceAfterSale != 0) {
                vItem.productPrice.setText(p.priceAfterSale + "");
            } else {
                vItem.productPrice.setText(p.dollarPrice + "");
            }

            vItem.productName.setText(p.name);

            vItem.productQuantity.setText("الكمية:" + basketList.get(position).getQuantity());

            Tools.displayImage(context, vItem.productImage, p.photo);


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
