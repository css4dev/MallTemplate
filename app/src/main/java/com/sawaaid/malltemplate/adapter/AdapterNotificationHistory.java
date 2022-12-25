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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sawaaid.malltemplate.R;
import com.sawaaid.malltemplate.model.NotificationHistory;
import com.sawaaid.malltemplate.model.Product;
import com.sawaaid.malltemplate.utils.Tools;

import java.util.ArrayList;
import java.util.List;

import me.grantland.widget.AutofitTextView;

public class AdapterNotificationHistory extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<NotificationHistory> items = new ArrayList<>();
    Context context;
    private final RecyclerView recyclerView;
    private AdapterListener<NotificationHistory> listener;
    private final int VIEW_ITEM = 1;
    private final int VIEW_PROG = 0;
    private boolean loading;
    private String status;
    private final int page;

    public void setListener(AdapterListener<NotificationHistory> listener) {
        this.listener = listener;
    }


    public static class OriginalViewHolder extends RecyclerView.ViewHolder {
        AutofitTextView title;
        AutofitTextView body;
        AutofitTextView date;
        RelativeLayout cardRelative;
        ImageView imgViewNotificationH;

        public OriginalViewHolder(View v) {
            super(v);
            title = v.findViewById(R.id.title);
            cardRelative = v.findViewById(R.id.cardRelative);
            date = v.findViewById(R.id.date);
            body = v.findViewById(R.id.body);
            imgViewNotificationH = v.findViewById(R.id.imgViewNotificationH);

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

    public AdapterNotificationHistory(Context context, RecyclerView view, int page) {
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
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_notification_history, parent, false);
            vh = new AdapterNotificationHistory.OriginalViewHolder(v);
        } else {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_loading, parent, false);
            vh = new AdapterNotificationHistory.ProgressViewHolder(v);
        }
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, @SuppressLint("RecyclerView") int pos) {
        int position = holder.getAdapterPosition();
        if (holder instanceof AdapterNotificationHistory.OriginalViewHolder) {
            AdapterNotificationHistory.OriginalViewHolder vItem = (AdapterNotificationHistory.OriginalViewHolder) holder;
            NotificationHistory p = items.get(position);

            vItem.title.setText(p.title);
            vItem.body.setText(p.message);
            vItem.date.setText(p.notificationDate);

        } else {
            final AdapterNotificationHistory.ProgressViewHolder v = (AdapterNotificationHistory.ProgressViewHolder) holder;
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

    public void insertData(List<NotificationHistory> items) {
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
        if (recyclerView.getLayoutManager() instanceof LinearLayoutManager) {
            final LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
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
