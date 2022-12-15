package com.sawaaid.malltemplate.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.sawaaid.malltemplate.R;
import com.sawaaid.malltemplate.model.Ads;
import com.sawaaid.malltemplate.utils.Tools;

import java.util.List;

public class ViewPager2Adapter extends RecyclerView.Adapter<ViewPager2Adapter.viewHolder> {

    Context context;
    private final List<Ads> adsList;


    public ViewPager2Adapter(Context context, List<Ads> adsList) {
        this.context = context;
        this.adsList = adsList;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new viewHolder(
                LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.view_pager_item,
                                parent, false));


    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        holder.setImage(adsList.get(position));
    }



    @Override
    public int getItemCount() {
        return adsList.size();
    }




    class viewHolder extends RecyclerView.ViewHolder {

        private final ImageView imageView;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image);

        }

        void setImage(Ads ads) {
            Tools.displayImage(context, imageView, ads.photo);
        }
    }


}
