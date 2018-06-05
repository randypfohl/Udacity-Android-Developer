package com.education.pfohl.popularmovies2.models;

import java.util.List;

public class Trailers {
    public String id;
    public List<Video> results;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Video> getResults() {
        return results;
    }

    public void setResults(List<Video> results) {
        this.results = results;
    }
}
