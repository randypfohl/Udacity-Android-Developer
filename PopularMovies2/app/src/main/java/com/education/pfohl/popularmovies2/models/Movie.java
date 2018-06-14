package com.education.pfohl.popularmovies2.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Movie implements Parcelable{
    int vote_count;
    int id;
    boolean video;
    double vote_average;
    String title;
    double popularity;
    String poster_path;
    String original_language;
    String original_title;
    int[] genre_ids;
    String backdrop_path;
    boolean adult;
    String overview;
    String release_date;
    boolean favorite;


    public Movie (Parcel in){
        this.vote_count = in.readInt();
        this.id = in.readInt();
        this.video = Boolean.valueOf(in.readString());
        this.vote_average = in.readDouble();
        this.title = in.readString();
        this.popularity = in.readDouble();
        this.poster_path = in.readString();
        this.original_language = in.readString();
        this.original_title = in.readString();
        in.readIntArray(this.genre_ids);
        this.backdrop_path = in.readString();
        this.adult = Boolean.valueOf(in.readString());
        this.overview = in.readString();
        this.release_date = in.readString();
        this.favorite = Boolean.valueOf(in.readString());
    }

    public Movie (){

    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Movie createFromParcel(Parcel in) {

            return new Movie(in);
        }

        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.vote_count);
        dest.writeInt(this.id);
        dest.writeString(String.valueOf(video));
        dest.writeDouble(vote_average);
        dest.writeString(title);
        dest.writeDouble(popularity);
        dest.writeString(poster_path);
        dest.writeString(original_language);
        dest.writeString(original_title);
        dest.writeIntArray(genre_ids);
        dest.writeString(backdrop_path);
        dest.writeString(String.valueOf(adult));
        dest.writeString(overview);
        dest.writeString(release_date);
        dest.writeString(String.valueOf(favorite));
    }



    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    public int getVote_count() {
        return vote_count;
    }

    public void setVote_count(int vote_count) {
        this.vote_count = vote_count;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isVideo() {
        return video;
    }

    public void setVideo(boolean video) {
        this.video = video;
    }

    public double getVote_average() {
        return vote_average;
    }

    public void setVote_average(double vote_average) {
        this.vote_average = vote_average;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getPopularity() {
        return popularity;
    }

    public void setPopularity(double popularity) {
        this.popularity = popularity;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public String getOriginal_language() {
        return original_language;
    }

    public void setOriginal_language(String original_language) {
        this.original_language = original_language;
    }

    public String getOriginal_title() {
        return original_title;
    }

    public void setOriginal_title(String original_title) {
        this.original_title = original_title;
    }

    public int[] getGenre_ids() {
        return genre_ids;
    }

    public void setGenre_ids(int[] genre_ids) {
        this.genre_ids = genre_ids;
    }

    public String getBackdrop_path() {
        return backdrop_path;
    }

    public void setBackdrop_path(String backdrop_path) {
        this.backdrop_path = backdrop_path;
    }

    public boolean isAdult() {
        return adult;
    }

    public void setAdult(boolean adult) {
        this.adult = adult;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }


}
