package com.example.tinnews.ui.details;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.tinnews.R;
import com.example.tinnews.repository.NewsRepository;
import com.example.tinnews.repository.NewsViewModelFactory;
import com.example.tinnews.ui.details.DetailsFragmentArgs;
import com.example.tinnews.databinding.FragmentDetailsBinding;
import com.example.tinnews.model.Article;
import com.squareup.picasso.Picasso;

public class DetailsFragment extends Fragment {

    private FragmentDetailsBinding binding;

    public DetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentDetailsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Article article = DetailsFragmentArgs.fromBundle(getArguments()).getArticle();
        NewsRepository repository = new NewsRepository(getContext());
        DetailsViewModel viewModel = new ViewModelProvider(this, new NewsViewModelFactory(repository)).get(DetailsViewModel.class);
        viewModel.checkIfFavorite(article).observe(
                getViewLifecycleOwner(),
                exists -> {
                    if (exists == 0) {
                        binding.detailsFavoriteImageView.setImageResource(R.drawable.ic_favorite_border_24dp);
                        binding.detailsFavoriteImageView.setOnClickListener(v -> viewModel.setFavoriteArticleInput(article));
                    } else {
                        binding.detailsFavoriteImageView.setImageResource(R.drawable.ic_favorite_24dp);
                        binding.detailsFavoriteImageView.setOnClickListener(v -> viewModel.deleteSavedArticle(article));
                    }
                });
        binding.detailsTitleTextView.setText(article.title);
        binding.detailsAuthorTextView.setText(article.author);
        binding.detailsDateTextView.setText(article.publishedAt);
        binding.detailsDescriptionTextView.setText(article.description);
        binding.detailsContentTextView.setText(article.content);
        Picasso.get().load(article.urlToImage).into(binding.detailsImageView);
    }
}