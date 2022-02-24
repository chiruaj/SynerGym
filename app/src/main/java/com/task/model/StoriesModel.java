package com.task.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

public class StoriesModel implements Parcelable {


    public static final Creator<StoriesModel> CREATOR = new Creator<StoriesModel>() {
        @Override
        public StoriesModel createFromParcel(Parcel in) {
            return new StoriesModel(in);
        }

        @Override
        public StoriesModel[] newArray(int size) {
            return new StoriesModel[size];
        }
    };
    private List<Articles> articles;
    private int totalResults;
    private String status;

    protected StoriesModel(Parcel in) {
        articles = in.createTypedArrayList(Articles.CREATOR);
        totalResults = in.readInt();
        status = in.readString();
    }

    public List<Articles> getArticles() {
        return articles;
    }

    public void setArticles(List<Articles> articles) {
        this.articles = articles;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeTypedList(articles);
        parcel.writeInt(totalResults);
        parcel.writeString(status);
    }

    public static class Articles implements Parcelable {
        public static final Creator<Articles> CREATOR = new Creator<Articles>() {
            @Override
            public Articles createFromParcel(Parcel in) {
                return new Articles(in);
            }

            @Override
            public Articles[] newArray(int size) {
                return new Articles[size];
            }
        };
        private String content;
        private String publishedAt;
        private String urlToImage;
        private String url;
        private String description;
        private String title;
        private String author;
        private Source source;

        public Articles(Parcel in) {
            content = in.readString();
            publishedAt = in.readString();
            urlToImage = in.readString();
            url = in.readString();
            description = in.readString();
            title = in.readString();
            author = in.readString();
        }

        public Articles() {

        }

        @BindingAdapter({"image_url"})
        public static void loadImage(ImageView imageView, String imageURL) {
            Glide.with(imageView.getContext())
                    .setDefaultRequestOptions(new RequestOptions())
                    .load(imageURL)
                    .into(imageView);
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getPublishedAt() {
            return publishedAt;
        }

        public void setPublishedAt(String publishedAt) {
            this.publishedAt = publishedAt;
        }

        public String getUrlToImage() {
            return urlToImage;
        }

        public void setUrlToImage(String urlToImage) {
            this.urlToImage = urlToImage;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public Source getSource() {
            return source;
        }

        public void setSource(Source source) {
            this.source = source;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeString(content);
            parcel.writeString(publishedAt);
            parcel.writeString(urlToImage);
            parcel.writeString(url);
            parcel.writeString(description);
            parcel.writeString(title);
            parcel.writeString(author);
        }
    }

    public static class Source implements Parcelable {
        public static final Creator<Source> CREATOR = new Creator<Source>() {
            @Override
            public Source createFromParcel(Parcel in) {
                return new Source(in);
            }

            @Override
            public Source[] newArray(int size) {
                return new Source[size];
            }
        };
        private String name;

        protected Source(Parcel in) {
            name = in.readString();
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeString(name);
        }
    }
}
