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
