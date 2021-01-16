package com.example.tinnews.ui.details;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.tinnews.model.Article;
import com.example.tinnews.repository.NewsRepository;

public class DetailsViewModel extends ViewModel {

    private final NewsRepository repository;

    public DetailsViewModel(NewsRepository repository) {
        this.repository = repository;
    }

    public LiveData<Integer> checkIfFavorite(Article article) {
        return repository.checkIfFavorite(article);
    }
}
