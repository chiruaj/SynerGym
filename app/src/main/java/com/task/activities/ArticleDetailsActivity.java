package com.task.activities;

import android.content.Context;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.task.R;
import com.task.model.StoriesModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ArticleDetailsActivity extends AppCompatActivity {

    Context ctx;
    StoriesModel.Articles articles;

    ImageView img_story;
    TextView tv_title;
    TextView tv_date;
    TextView tv_desc;
    FloatingActionButton backArrow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_details);
        ctx = this;
        articles = (StoriesModel.Articles) getIntent().getParcelableExtra("articles");
        intializeUi();
        intializeValues();

    }

    private void intializeValues() {
        Glide.with(this).load(articles.getUrlToImage()).into(img_story);
        tv_title.setText(articles.getTitle());
        tv_date.setText(changeDateFormat(articles.getPublishedAt()));
        tv_desc.setText(articles.getDescription());
        backArrow.setOnClickListener(view -> finish());

    }

    private String changeDateFormat(String date) {
        SimpleDateFormat input = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        SimpleDateFormat output = new SimpleDateFormat("yyyy-MM-dd");
        Date d = null;
        try {
            d = input.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return output.format(d);
    }


    private void intializeUi() {
        img_story = findViewById(R.id.img_story);
        tv_title = findViewById(R.id.tv_title);
        tv_date = findViewById(R.id.tv_date);
        tv_desc = findViewById(R.id.tv_desc);
        backArrow = findViewById(R.id.backArrow);
    }
}