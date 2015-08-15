package com.example.administrator.ex02_gson;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Status {

    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @Expose
    private String text;
    @Expose
    private Boolean favorited;

    /**
     * 
     * @return
     *     The createdAt
     */
    public String getCreatedAt() {
        return createdAt;
    }

    /**
     * 
     * @param createdAt
     *     The created_at
     */
    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * 
     * @return
     *     The text
     */
    public String getText() {
        return text;
    }

    /**
     * 
     * @param text
     *     The text
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * 
     * @return
     *     The favorited
     */
    public Boolean getFavorited() {
        return favorited;
    }

    /**
     * 
     * @param favorited
     *     The favorited
     */
    public void setFavorited(Boolean favorited) {
        this.favorited = favorited;
    }

}
