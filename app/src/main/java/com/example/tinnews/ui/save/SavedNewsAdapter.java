package com.example.tinnews.ui.save;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tinnews.R;
import com.example.tinnews.databinding.SavedNewsItemBinding;
import com.example.tinnews.model.Article;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class SavedNewsAdapter extends RecyclerView.Adapter<SavedNewsAdapter.SavedNewsViewHolder> {

    // 1. Supporting data:
    private List<Article> articles = new ArrayList<>();

    public void setArticles(List<Article> newsList) {
        articles.clear();
        articles.addAll(newsList);
        notifyDataSetChanged();
    }

    // 2. Adapter overrides:
    @NonNull
    @Override
    public SavedNewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.saved_news_item, parent, false);
        return new SavedNewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SavedNewsViewHolder holder, int position) {
        Article article = articles.get(position);
        holder.authorTextView.setText(article.author);
        holder.descriptionTextView.setText(article.description);
        Picasso.get().load(article.urlToImage).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return articles.size();
    }

    // 3. SavedNewsViewHolder:
    public static class SavedNewsViewHolder extends RecyclerView.ViewHolder {

        ImageView favoriteIcon;
        ImageView imageView;
        TextView authorTextView;
        TextView descriptionTextView;

        public SavedNewsViewHolder(@NonNull View itemView) {
            super(itemView);
            SavedNewsItemBinding binding = SavedNewsItemBinding.bind(itemView);
            favoriteIcon = binding.savedItemFavoriteImageView;
            imageView = binding.savedItemImage;
            authorTextView = binding.savedItemAuthorContent;
            descriptionTextView = binding.savedItemDescriptionContent;
        }
    }
}
