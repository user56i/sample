package com.nixsolutions.cupboard.entities;

public class GitHubUserWithOrganizationUnion {

    private Long _id;
    private String login;
    private String avatarUrl;
    private String organizationAvatarUrl;
    private String description;

    public Long getId() {
        return _id;
    }

    public void setId(Long _id) {
        this._id = _id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getOrganizationAvatarUrl() {
        return organizationAvatarUrl;
    }

    public void setOrganizationAvatarUrl(String organizationAvatarUrl) {
        this.organizationAvatarUrl = organizationAvatarUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
