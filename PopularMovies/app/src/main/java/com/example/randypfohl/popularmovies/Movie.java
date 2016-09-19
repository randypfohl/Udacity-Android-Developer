package com.example.randypfohl.popularmovies;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by randypfohl on 9/18/16.
 */
public class Movie implements Parcelable {

    private String title;
    private String release;
    private String voteAve;
    private String plot;
    private String posterUrl;


    public Movie(Parcel in) {
        String[] data = new String[5];

        in.readStringArray(data);
        this.title = data[0];
        this.release = data[1];
        this.voteAve = data[2];
        this.plot = data[3];
        this.posterUrl = data[4];
    }

    public Movie(){

    }

    public String getRelease() {
        return release;
    }

    public void setRelease(String release) {
        this.release = release;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPosterUrl() {
        return posterUrl;
    }

    public void setPosterUrl(String posterUrl) {
        this.posterUrl = posterUrl;
    }

    public String getVoteAve() {
        return voteAve;
    }

    public void setVoteAve(String voteAve) {
        this.voteAve = voteAve;
    }

    public String getPlot() {
        return plot;
    }

    public void setPlot(String plot) {
        this.plot = plot;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int i) {
        dest.writeStringArray(new String[]{
                this.title,
                this.release,
                this.voteAve,
                this.plot,
                this.posterUrl
        });

    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };
}
    /**
     *
     *  this.title = data[0];
     this.release = data[1];
     this.voteAve = data[2];
     this.plot = data[3];
     this.posterUrl = data[4];
     *
     *     // Parcelling part
     public Student(Parcel in){
     String[] data = new String[3];

     in.readStringArray(data);
     this.id = data[0];
     this.name = data[1];
     this.grade = data[2];
     }

     @Оverride
     public int describeContents(){
     return 0;
     }

     @Override
     public void writeToParcel(Parcel dest, int flags) {
     dest.writeStringArray(new String[] {this.id,
     this.name,
     this.grade});
     }
     public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
     public Student createFromParcel(Parcel in) {
     return new Student(in);
     }

     public Student[] newArray(int size) {
     return new Student[size];
     }
     };
     }

     */

