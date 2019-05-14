package com.example.backingapp.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Step implements Serializable {
    @SerializedName("id")
    private
    int id;
    @SerializedName("shortDescription")
    private
    String shortDescription;
    @SerializedName("description")
    private
    String description;
    @SerializedName("videoURL")
    private
    String videoURL;

    public Step(int id, String shortDescription, String description, String videoURL) {
        this.id = id;
        this.shortDescription = shortDescription;
        this.description = description;
        this.videoURL = videoURL;
    }

    public int getId() {
        return id;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public String getDescription() {
        return description;
    }

    public String getVideoURL() {
        return videoURL;
    }
}
