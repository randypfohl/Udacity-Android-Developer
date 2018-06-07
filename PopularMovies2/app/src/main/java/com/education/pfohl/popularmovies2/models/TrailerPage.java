package com.education.pfohl.popularmovies2.models;

import java.util.List;

public class TrailerPage {
    public String id;
    public List<Trailer> results;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Trailer> getResults() {
        return results;
    }

    public void setResults(List<Trailer> results) {
        this.results = results;
    }
}
