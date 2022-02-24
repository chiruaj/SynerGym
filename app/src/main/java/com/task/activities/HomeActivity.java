package com.task.activities;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.task.R;
import com.task.adapters.StoriesAdapter;
import com.task.model.RealMStoriesModel;
import com.task.model.StoriesModel;
import com.task.view_model.MainViewModel;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;

public class HomeActivity extends AppCompatActivity {

    Context ctx;
    RecyclerView rec_stories;
    ProgressBar progressBar;
    StoriesAdapter storiesAdapter;
    List<RealMStoriesModel> realMStoriesModel;
    private Realm realm;
    private MainViewModel mainViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DataBindingUtil.setContentView(this, R.layout.activity_home);

        ctx = this;

        realm = Realm.getDefaultInstance();

        rec_stories = findViewById(R.id.rec_stories);
        progressBar = findViewById(R.id.progressBar);

        rec_stories.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        storiesAdapter = new StoriesAdapter(ctx, articles -> {
            Intent intent = new Intent(this, ArticleDetailsActivity.class);
            intent.putExtra("articles", (Parcelable) articles);
            startActivity(intent);
        });
        rec_stories.setAdapter(storiesAdapter);


        if (isNetworkAvailable()) {
            callStories();
        } else {
            realMStoriesModel = realm.where(RealMStoriesModel.class).findAll();

            if (realMStoriesModel.size() != 0) {
                List<StoriesModel.Articles> list = new ArrayList<>();
                for (int i = 0; i < realMStoriesModel.size(); i++) {

                    StoriesModel.Articles articles = new StoriesModel.Articles();
                    articles.setAuthor(realMStoriesModel.get(i).getAuthor());
                    articles.setContent(realMStoriesModel.get(i).getContent());
                    articles.setDescription(realMStoriesModel.get(i).getDescription());
                    articles.setTitle(realMStoriesModel.get(i).getTitle());
                    articles.setUrl(realMStoriesModel.get(i).getUrl());
                    articles.setUrlToImage(realMStoriesModel.get(i).getUrlToImage());

                    list.add(articles);
                }
                storiesAdapter.setEmployeeList((ArrayList<StoriesModel.Articles>) list);
            }
        }

    }

    private void callStories() {
        mainViewModel.getArticles().observe(this, articles -> {
            storiesAdapter.setEmployeeList((ArrayList<StoriesModel.Articles>) articles);
            updateData(articles);
        });
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    private void updateData(List<StoriesModel.Articles> response) {

        realm.executeTransaction(realm -> realm.delete(RealMStoriesModel.class));
        if (response != null) {
            realm.beginTransaction();
            for (int i = 0; i < response.size(); i++) {
                StoriesModel.Articles articles = response.get(i);
                RealMStoriesModel realmModel = new RealMStoriesModel();
                realmModel.setId(i);
                realmModel.setName(articles.getAuthor());
                realmModel.setAuthor(articles.getAuthor());
                realmModel.setContent(articles.getContent());
                realmModel.setDescription(articles.getDescription());
                realmModel.setTitle(articles.getTitle());
                realmModel.setUrl(articles.getUrl());
                realmModel.setUrlToImage(articles.getUrlToImage());
                realm.executeTransactionAsync(realm -> realm.insertOrUpdate(realmModel));
            }
            realm.commitTransaction();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (realm != null) { // don't trust old devices
            realm.close();
        }
    }
}