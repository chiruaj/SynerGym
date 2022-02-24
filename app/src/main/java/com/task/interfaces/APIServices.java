package com.task.interfaces;

import com.task.model.StoriesModel;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;

public interface APIServices {

    String BASE_URL = "https://newsapi.org/v2/";

    @GET("everything?q=tesla&from=2022-01-24&sortBy=publishedAt&apiKey=b365035a109540f08b2e185f6d9f0500")
    Call<StoriesModel> getStories();
}
