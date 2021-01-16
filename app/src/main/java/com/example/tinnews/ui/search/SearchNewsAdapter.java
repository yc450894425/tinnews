package com.example.tinnews.ui.search;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tinnews.R;
import com.example.tinnews.databinding.SearchNewsItemBinding;
import com.example.tinnews.model.Article;
import com.google.gson.internal.bind.ReflectiveTypeAdapterFactory;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class SearchNewsAdapter extends RecyclerView.Adapter<SearchNewsAdapter.SearchNewsViewHolder> {

    interface ItemCallback {
        void onOpenDetails(Article article);
        void onRemoveFavorite(Article article);
    }

    // 1. Supporting data:
    private List<Article> articles = new ArrayList<>();
    private ItemCallback itemCallback;

    public void setItemCallback(ItemCallback itemCallback) {
        this.itemCallback = itemCallback;
    }

    public void setArticles(List<Article> newsList) {
        articles.clear();
        articles.addAll(newsList);
        notifyDataSetChanged();
    }

    // 2. Adapter overrides:
    @NonNull
    @Override
    public SearchNewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_news_item, parent, false);
        return new SearchNewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchNewsViewHolder holder, int position) {
        Article article = articles.get(position);
        holder.itemTitleTextView.setText(article.title);
        holder.itemAuthorTextView.setText(article.author);
        holder.itemTimeTextView.setText(article.publishedAt);
        Picasso.get().load(article.urlToImage).into(holder.itemImageView);
        holder.itemView.setOnClickListener(v -> itemCallback.onOpenDetails(article));
    }

    @Override
    public int getItemCount() {
        return articles.size();
    }

    // 3. SearchNewsViewHolder:
    public static class SearchNewsViewHolder extends RecyclerView.ViewHolder {

        ImageView itemImageView;
        ImageView favoriteImageView;
        TextView itemTitleTextView;
        TextView itemTimeTextView;
        TextView itemAuthorTextView;

        public SearchNewsViewHolder(@NonNull View itemView) {
            super(itemView);

            SearchNewsItemBinding binding = SearchNewsItemBinding.bind(itemView);
            itemImageView = binding.searchItemImage;
            itemTitleTextView = binding.searchItemTitle;
            itemTimeTextView = binding.searchItemTime;
            itemAuthorTextView = binding.searchItemAuthor;
        }
    }
}
