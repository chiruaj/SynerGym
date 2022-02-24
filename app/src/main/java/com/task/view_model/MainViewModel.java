package com.task.view_model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.task.model.StoriesModel;
import com.task.utils.StoriesRepository;

import java.util.List;

public class MainViewModel extends AndroidViewModel {


    private StoriesRepository storiesRepository;


    public MainViewModel(@NonNull Application application) {
        super(application);
        storiesRepository = new StoriesRepository();
    }

    public LiveData<List<StoriesModel.Articles>> getArticles() {
        return storiesRepository.getMutableLiveData();
    }
}
