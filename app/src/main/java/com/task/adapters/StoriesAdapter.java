package com.task.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.task.R;
import com.task.databinding.StoriesItemBindingImpl;
import com.task.model.RealMStoriesModel;
import com.task.model.StoriesModel;

import java.util.ArrayList;
import java.util.List;

public class StoriesAdapter extends RecyclerView.Adapter<StoriesAdapter.StoriesViewHolder> {

    Context ctx;
    ArrayList<StoriesModel.Articles> list;
    OnItemClick onItemClick;

    public interface OnItemClick {
        void onClick(StoriesModel.Articles articles);
    }

    public StoriesAdapter(Context ctx, OnItemClick onItemClick) {
        this.ctx = ctx;
        this.onItemClick = onItemClick;
    }


    @NonNull
    @Override
    public StoriesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        StoriesItemBindingImpl storiesItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.stories_item, parent, false);

        return new StoriesViewHolder(storiesItemBinding);
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setEmployeeList(ArrayList<StoriesModel.Articles> employees) {
        this.list = employees;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull StoriesViewHolder holder, int position) {
        StoriesModel.Articles articles = list.get(position);
        holder.bind(onItemClick,articles);

        holder.storiesItemBinding.setArticles(articles);
    }

    @Override
    public int getItemCount() {
        if (list != null) {
            return list.size();
        } else {
            return 0;
        }
    }


    public static class StoriesViewHolder extends RecyclerView.ViewHolder {

        StoriesItemBindingImpl storiesItemBinding;

        public StoriesViewHolder(@NonNull StoriesItemBindingImpl itemView) {
            super(itemView.getRoot());
            this.storiesItemBinding = itemView;
        }

        void bind(OnItemClick onItemClick, StoriesModel.Articles articles) {
            itemView.setOnClickListener(v -> {
                onItemClick.onClick(articles);
            });
        }
    }
}
