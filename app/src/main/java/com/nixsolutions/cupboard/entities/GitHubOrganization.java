package com.nixsolutions.cupboard.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GitHubOrganization {

    @Expose
    private String login;

    @SerializedName("id")
    @Expose
    private Long _id;
    @Expose
    private String url;
    @SerializedName("repos_url")
    @Expose
    private String reposUrl;
    @SerializedName("events_url")
    @Expose
    private String eventsUrl;
    @SerializedName("members_url")
    @Expose
    private String membersUrl;
    @SerializedName("public_members_url")
    @Expose
    private String publicMembersUrl;
    @SerializedName("avatar_url")
    @Expose
    private String avatarUrl;
    @Expose
    private String description;

    /**
     * @return The login
     */
    public String getLogin() {
        return login;
    }

    /**
     * @param login The login
     */
    public void setLogin(String login) {
        this.login = login;
    }

    /**
     * @return The _id
     */
    public Long getId() {
        return _id;
    }

    /**
     * @param id The _id
     */
    public void setId(Long id) {
        this._id = id;
    }

    /**
     * @return The url
     */
    public String getUrl() {
        return url;
    }

    /**
     * @param url The url
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * @return The reposUrl
     */
    public String getReposUrl() {
        return reposUrl;
    }

    /**
     * @param reposUrl The repos_url
     */
    public void setReposUrl(String reposUrl) {
        this.reposUrl = reposUrl;
    }

    /**
     * @return The eventsUrl
     */
    public String getEventsUrl() {
        return eventsUrl;
    }

    /**
     * @param eventsUrl The events_url
     */
    public void setEventsUrl(String eventsUrl) {
        this.eventsUrl = eventsUrl;
    }

    /**
     * @return The membersUrl
     */
    public String getMembersUrl() {
        return membersUrl;
    }

    /**
     * @param membersUrl The members_url
     */
    public void setMembersUrl(String membersUrl) {
        this.membersUrl = membersUrl;
    }

    /**
     * @return The publicMembersUrl
     */
    public String getPublicMembersUrl() {
        return publicMembersUrl;
    }

    /**
     * @param publicMembersUrl The public_members_url
     */
    public void setPublicMembersUrl(String publicMembersUrl) {
        this.publicMembersUrl = publicMembersUrl;
    }

    /**
     * @return The avatarUrl
     */
    public String getAvatarUrl() {
        return avatarUrl;
    }

    /**
     * @param avatarUrl The avatar_url
     */
    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    /**
     * @return The description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description The description
     */
    public void setDescription(String description) {
        this.description = description;
    }

}