package com.sawaaid.malltemplate.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sawaaid.malltemplate.R;
import com.sawaaid.malltemplate.model.Product;
import com.sawaaid.malltemplate.utils.Tools;

import java.util.ArrayList;
import java.util.List;

import me.grantland.widget.AutofitTextView;

public class AdapterProducts extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Product> items = new ArrayList<>();
    Context context;
    private final RecyclerView recyclerView;
    private AdapterListener<Product> listener;
    private final int VIEW_ITEM = 1;
    private final int VIEW_PROG = 0;
    private boolean loading;
    private String status;
    private final int page;

    public void setListener(AdapterListener<Product> listener) {
        this.listener = listener;
    }


    public static class OriginalViewHolder extends RecyclerView.ViewHolder {
        public TextView name_textview;
        public AutofitTextView price, old_price;
        public ImageView image_imageview;
        public RelativeLayout lytParent, old_price_relative;
        public LinearLayout add_to_basket_linear;
        public Button buttonAddToFav;

        public OriginalViewHolder(View v) {
            super(v);
            name_textview = v.findViewById(R.id.name);
            image_imageview = v.findViewById(R.id.image);
            lytParent = v.findViewById(R.id.lytParent);
            price = v.findViewById(R.id.price);
            old_price = v.findViewById(R.id.old_price);
            old_price_relative = v.findViewById(R.id.old_price_relative);
            add_to_basket_linear = v.findViewById(R.id.add_to_basket_linear);
            buttonAddToFav = v.findViewById(R.id.buttonAddToFav);

        }
    }

    public static class ProgressViewHolder extends RecyclerView.ViewHolder {
        public ProgressBar progress_loading;
        public TextView status_loading;

        public ProgressViewHolder(View v) {
            super(v);
            progress_loading = v.findViewById(R.id.progress_loading);
            status_loading = v.findViewById(R.id.status_loading);
        }
    }

    public AdapterProducts(Context context, RecyclerView view, int page) {
        this.context = context;
        this.page = page;
        this.recyclerView = view;
        lastItemViewDetector(view);
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position, @NonNull List<Object> payloads) {
        super.onBindViewHolder(holder, position, payloads);

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        if (viewType == VIEW_ITEM) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product, parent, false);
            vh = new OriginalViewHolder(v);
        } else {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_loading, parent, false);
            vh = new ProgressViewHolder(v);
        }
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, @SuppressLint("RecyclerView") int pos) {
        int position = holder.getAdapterPosition();
        if (holder instanceof OriginalViewHolder) {
            OriginalViewHolder vItem = (OriginalViewHolder) holder;
            Product p = items.get(position);

            Tools.displayImage(context, vItem.image_imageview, p.photo);
            vItem.lytParent.setOnClickListener(view -> listener.onClick(view, null, p, position));

        } else {
            final ProgressViewHolder v = (ProgressViewHolder) holder;
            v.progress_loading.setVisibility(status == null ? View.VISIBLE : View.INVISIBLE);
            v.status_loading.setVisibility(status == null ? View.INVISIBLE : View.VISIBLE);

            if (status == null) return;
            v.status_loading.setText(status);
            v.status_loading.setOnClickListener(view -> {
                setLoaded();
                onLoadMore();
            });
        }


    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public int getItemViewType(int position) {
        return this.items.get(position) != null ? VIEW_ITEM : VIEW_PROG;
    }

    public void insertData(List<Product> items) {
        setLoaded();
        int positionStart = getItemCount();
        int itemCount = items.size();
        this.items.addAll(items);
        notifyItemRangeInserted(positionStart, itemCount);
    }

    public void removeItem(int position) {
        items.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, items.size());
    }

    public void setLoaded() {
        status = null;
        loading = false;
        int last_index = getItemCount() - 1;
        if (last_index > -1 && items.get(last_index) == null) {
            items.remove(last_index);
            notifyItemRemoved(last_index);
        }
    }

    public void setLoadingOrFailed(String status) {
        this.status = status;
        if (getItemCount() == 0 || this.items.get(getItemCount() - 1) != null) {
            this.items.add(null);
        }
        recyclerView.post(() -> notifyItemChanged(getItemCount() - 1));
        loading = true;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void resetListData() {
        this.items = new ArrayList<>();
        notifyDataSetChanged();
    }


    private void lastItemViewDetector(RecyclerView recyclerView) {
        if (recyclerView.getLayoutManager() instanceof GridLayoutManager) {
            final GridLayoutManager layoutManager = (GridLayoutManager) recyclerView.getLayoutManager();
            recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                    super.onScrollStateChanged(recyclerView, newState);
                    int lastPos = layoutManager.findLastVisibleItemPosition();
                    if (newState == RecyclerView.SCROLL_STATE_IDLE) return;
                    boolean bottom = lastPos >= getItemCount() - page;
                    if (!loading && bottom && listener != null) {
                        onLoadMore();
                    }
                }
            });

            layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    int type = getItemViewType(position);
                    int spanCount = layoutManager.getSpanCount();
                    return type == VIEW_PROG ? spanCount : 1;
                }
            });
        }
    }

    private void onLoadMore() {
        int current_page = getItemCount() / page;
        listener.onLoadMore(current_page);
        loading = true;
        status = null;
    }


    @Override
    public long getItemId(int position) {
        return position;
    }

}
