package com.example.randypfohl.popularmovies;

/**
 * Created by randypfohl on 9/2/16.
 */
public class Movie {
    private String originalTitle;
    private String imageExt;
    private String synopsis;
    private String rating;
    private String releaseDate;

    public Movie(){
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public String getImageExt() {
        return imageExt;
    }

    public void setImageExt(String imageExt) {
        this.imageExt = imageExt;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }
}
