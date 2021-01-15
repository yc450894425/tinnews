package com.example.tinnews.ui.home;

import android.util.Pair;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.example.tinnews.model.Article;
import com.example.tinnews.model.NewsResponse;
import com.example.tinnews.repository.NewsRepository;

import java.util.Objects;

public class HomeViewModel extends ViewModel {

    private final NewsRepository repository;
    private final MutableLiveData<HomeInput> homeInputLiveData = new MutableLiveData<>();


    public HomeViewModel(NewsRepository newsRepository) {
        this.repository = newsRepository;
    }

    public void setHomeInput(HomeInput update) {
        if (update.equals(homeInputLiveData.getValue())) {
            return;
        }
        homeInputLiveData.setValue(update);
    }

    public LiveData<NewsResponse> getTopHeadlines() {
        return Transformations.switchMap(homeInputLiveData, repository::getTopHeadlines);
    }

    public LiveData<Boolean> setFavoriteArticle(Article article) {
        return repository.favoriteArticle(article);
    }

    public void deleteFavoriteArticle(Article article) {
        repository.deleteSavedArticle(article);
    }
}
