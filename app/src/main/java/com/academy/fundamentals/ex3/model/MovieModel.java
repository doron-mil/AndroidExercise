package com.academy.fundamentals.ex3.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.DrawableRes;

public class MovieModel implements Parcelable {

    private String name;
    @DrawableRes
    private int imageResourceId;
    @DrawableRes
    private int backImageResourceId;
    private String overview;
    private String trailerUrl;
    private String releaseDate;

    private int movieId;
    private String imageResourceUri;
    private String backImageResourceUri;

    public MovieModel(int movieId,String name, String overview,
                      String releaseDate,
                      String imageResourceUri, String backImageResourceUri, String trailerUrl) {
        this.name = name;
        this.overview = overview;
        this.trailerUrl = trailerUrl;
        this.releaseDate = releaseDate;
        this.movieId = movieId;
        this.imageResourceUri = imageResourceUri;
        this.backImageResourceUri = backImageResourceUri;
    }

    protected MovieModel(Parcel in) {
        name = in.readString();
        imageResourceId = in.readInt();
        backImageResourceId = in.readInt();
        overview = in.readString();
        trailerUrl = in.readString();
        releaseDate = in.readString();
    }

    public static final Creator<MovieModel> CREATOR = new Creator<MovieModel>() {
        @Override
        public MovieModel createFromParcel(Parcel in) {
            return new MovieModel(in);
        }

        @Override
        public MovieModel[] newArray(int size) {
            return new MovieModel[size];
        }
    };

    public MovieModel() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @DrawableRes
    public int getImageResourceId() {
        return imageResourceId;
    }

    public void setImageResourceId(@DrawableRes int imageResourceId) {
        this.imageResourceId = imageResourceId;
    }

    @DrawableRes
    public int getBackImageResourceId() {
        return backImageResourceId;
    }

    public void setBackImageResourceId(@DrawableRes int backImageResourceId) {
        this.backImageResourceId = backImageResourceId;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getTrailerUrl() {
        return trailerUrl;
    }

    public void setTrailerUrl(String trailerUrl) {
        this.trailerUrl = trailerUrl;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getImageResourceUri() {
        return imageResourceUri;
    }

    public void setImageResourceUri(String imageResourceUri) {
        this.imageResourceUri = imageResourceUri;
    }

    public String getBackImageResourceUri() {
        return backImageResourceUri;
    }

    public void setBackImageResourceUri(String backImageResourceUri) {
        this.backImageResourceUri = backImageResourceUri;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeString(this.name);
        parcel.writeInt(this.imageResourceId);
        parcel.writeInt(this.backImageResourceId);
        parcel.writeString(this.overview);
        parcel.writeString(this.releaseDate);
        parcel.writeString(this.trailerUrl);
    }
}
