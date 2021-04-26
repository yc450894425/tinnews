package com.example.tinnews.ui.home;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.tinnews.TinNewsApplication;
import com.example.tinnews.databinding.FragmentHomeBinding;
import com.example.tinnews.model.Article;
import com.example.tinnews.model.NewsResponse;
import com.example.tinnews.repository.NewsRepository;
import com.example.tinnews.repository.NewsViewModelFactory;
import com.yuyakaido.android.cardstackview.CardStackLayoutManager;
import com.yuyakaido.android.cardstackview.CardStackListener;
import com.yuyakaido.android.cardstackview.Direction;
import com.yuyakaido.android.cardstackview.Duration;
import com.yuyakaido.android.cardstackview.RewindAnimationSetting;
import com.yuyakaido.android.cardstackview.StackFrom;
import com.yuyakaido.android.cardstackview.SwipeAnimationSetting;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class HomeFragment extends Fragment implements CardStackListener {

    private HomeViewModel viewModel;
    private FragmentHomeBinding binding;
    private CardStackLayoutManager layoutManager;
    private CardSwipeAdapter swipeAdapter;
    private HomeInput input;
    private List<Article> articles;

    public HomeFragment() {
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
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // setup CardStackView
        swipeAdapter = new CardSwipeAdapter();
        layoutManager = new CardStackLayoutManager(requireContext(), this);
        layoutManager.setStackFrom(StackFrom.Top);
        binding.homeCardStackView.setLayoutManager(layoutManager);
        binding.homeCardStackView.setAdapter(swipeAdapter);

        // handle buttons
        binding.homeLikeButton.setOnClickListener(v -> swipeCard(Direction.Right));
        binding.homeUnlikeButton.setOnClickListener(v -> swipeCard(Direction.Left));
        binding.homeRewindButton.setOnClickListener(v -> rewindCard());
        binding.homeRefreshButton.setOnClickListener(v -> loadMore());

        NewsRepository repository = new NewsRepository(getContext());
        // create a new/get an old viewModel via factory pattern
        viewModel = new ViewModelProvider(this, new NewsViewModelFactory(repository))
                .get(HomeViewModel.class);

        input = new HomeInput("us", 1, 5);
        viewModel.setHomeInput(input);
        viewModel.getTopHeadlines()
                .observe(
                        getViewLifecycleOwner(),
                        new Observer<NewsResponse>() {
                            @Override
                            public void onChanged(NewsResponse newsResponse) {
                                if (newsResponse != null) {
                                    articles = newsResponse.articles;
                                    swipeAdapter.setArticles(articles);
                                }
                            }
                        });
    }

    private void swipeCard(Direction direction) {
        SwipeAnimationSetting setting = new SwipeAnimationSetting
                .Builder()
                .setDirection(direction)
                .setDuration(Duration.Normal.duration)
                .build();
        layoutManager.setSwipeAnimationSetting(setting);
        binding.homeCardStackView.swipe();
    }

    private void rewindCard() {
        RewindAnimationSetting setting = new RewindAnimationSetting
                .Builder()
                .setDirection(Direction.Bottom)
                .setDuration(Duration.Fast.duration)
                .build();
        layoutManager.setRewindAnimationSetting(setting);
        binding.homeCardStackView.rewind();
    }

    private void loadMore() {
        input = new HomeInput(input.country, input.page + 1, input.pageSize);
        viewModel.setHomeInput(input);
    }

    private void showToast(Boolean success) {
        String text = success ? "Saved successfully!" : "Saved failed";
        Toast.makeText(this.requireContext(), text, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCardDragging(Direction direction, float ratio) {
    }

    @Override
    public void onCardSwiped(Direction direction) {
        int currPosition = layoutManager.getTopPosition();
        System.out.println(currPosition);
        if (direction == Direction.Left) {
            Log.d("CardStackView", "Unliked " + currPosition);
        } else if (direction == Direction.Right) {
            Log.d("CardStackView", "Liked "  + currPosition);
            Article article = articles.get(currPosition - 1);
            viewModel.setFavoriteArticle(article)
                    .observe(
                            getViewLifecycleOwner(),
                            this::showToast
                            );
        }
        if (currPosition == swipeAdapter.getItemCount()) {
            loadMore();
        }
    }

    @Override
    public void onCardRewound() {
        viewModel.deleteFavoriteArticle(articles.get(layoutManager.getTopPosition()));
    }

    @Override
    public void onCardCanceled() {

    }

    @Override
    public void onCardAppeared(View view, int position) {
    }

    @Override
    public void onCardDisappeared(View view, int position) {
    }
}