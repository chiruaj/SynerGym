package com.task.utils;

import static com.task.interfaces.APIServices.BASE_URL;

import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.task.interfaces.APIServices;
import com.task.model.StoriesModel;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class StoriesRepository {

    private static final String TAG = "EmployeeRepository";
    private ArrayList<StoriesModel.Articles> employees = new ArrayList<>();
    private MutableLiveData<List<StoriesModel.Articles>> mutableLiveData = new MutableLiveData<>();
    Retrofit retrofit;

    public StoriesRepository() {


    }
    public MutableLiveData<List<StoriesModel.Articles>> getMutableLiveData() {


        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        APIServices apiServices = retrofit.create(APIServices.class);
        Call<StoriesModel> call = apiServices.getStories();
        call.enqueue(new Callback<StoriesModel>() {
            @Override
            public void onResponse(Call<StoriesModel> call,
                                   Response<StoriesModel> response) {
                StoriesModel employeeDBResponse = response.body();
                if (employeeDBResponse != null && employeeDBResponse.getArticles() != null) {
                    employees = (ArrayList<StoriesModel.Articles>) employeeDBResponse.getArticles();
                    mutableLiveData.setValue(employees);
                }
            }
            @Override
            public void onFailure(Call<StoriesModel> call, Throwable t) {
            }
        });
        return mutableLiveData;
    }
}
