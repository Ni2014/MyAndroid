package com.example.administrator.ex02_gson;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ShowUser {

    @Expose
    private Integer id;
    @SerializedName("screen_name")
    @Expose
    private String screenName;
    @Expose
    private String name;
    @Expose
    private String province;
    @Expose
    private String city;
    @Expose
    private String location;
    @Expose
    private String description;
    @Expose
    private String url;
    @SerializedName("profile_image_url")
    @Expose
    private String profileImageUrl;
    @Expose
    private Status status;
    @SerializedName("allow_all_comment")
    @Expose
    private Boolean allowAllComment;
    @SerializedName("avatar_large")
    @Expose
    private String avatarLarge;
    @SerializedName("follow_me")
    @Expose
    private Boolean followMe;
    @SerializedName("online_status")
    @Expose
    private Integer onlineStatus;

    /**
     * 
     * @return
     *     The id
     */
    public Integer getId() {
        return id;
    }

    /**
     * 
     * @param id
     *     The id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 
     * @return
     *     The screenName
     */
    public String getScreenName() {
        return screenName;
    }

    /**
     * 
     * @param screenName
     *     The screen_name
     */
    public void setScreenName(String screenName) {
        this.screenName = screenName;
    }

    /**
     * 
     * @return
     *     The name
     */
    public String getName() {
        return name;
    }

    /**
     * 
     * @param name
     *     The name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 
     * @return
     *     The province
     */
    public String getProvince() {
        return province;
    }

    /**
     * 
     * @param province
     *     The province
     */
    public void setProvince(String province) {
        this.province = province;
    }

    /**
     * 
     * @return
     *     The city
     */
    public String getCity() {
        return city;
    }

    /**
     * 
     * @param city
     *     The city
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * 
     * @return
     *     The location
     */
    public String getLocation() {
        return location;
    }

    /**
     * 
     * @param location
     *     The location
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * 
     * @return
     *     The description
     */
    public String getDescription() {
        return description;
    }

    /**
     * 
     * @param description
     *     The description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * 
     * @return
     *     The url
     */
    public String getUrl() {
        return url;
    }

    /**
     * 
     * @param url
     *     The url
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * 
     * @return
     *     The profileImageUrl
     */
    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    /**
     * 
     * @param profileImageUrl
     *     The profile_image_url
     */
    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

    /**
     * 
     * @return
     *     The status
     */
    public Status getStatus() {
        return status;
    }

    /**
     * 
     * @param status
     *     The status
     */
    public void setStatus(Status status) {
        this.status = status;
    }

    /**
     * 
     * @return
     *     The allowAllComment
     */
    public Boolean getAllowAllComment() {
        return allowAllComment;
    }

    /**
     * 
     * @param allowAllComment
     *     The allow_all_comment
     */
    public void setAllowAllComment(Boolean allowAllComment) {
        this.allowAllComment = allowAllComment;
    }

    /**
     * 
     * @return
     *     The avatarLarge
     */
    public String getAvatarLarge() {
        return avatarLarge;
    }

    /**
     * 
     * @param avatarLarge
     *     The avatar_large
     */
    public void setAvatarLarge(String avatarLarge) {
        this.avatarLarge = avatarLarge;
    }

    /**
     * 
     * @return
     *     The followMe
     */
    public Boolean getFollowMe() {
        return followMe;
    }

    /**
     * 
     * @param followMe
     *     The follow_me
     */
    public void setFollowMe(Boolean followMe) {
        this.followMe = followMe;
    }

    /**
     * 
     * @return
     *     The onlineStatus
     */
    public Integer getOnlineStatus() {
        return onlineStatus;
    }

    /**
     * 
     * @param onlineStatus
     *     The online_status
     */
    public void setOnlineStatus(Integer onlineStatus) {
        this.onlineStatus = onlineStatus;
    }

}
