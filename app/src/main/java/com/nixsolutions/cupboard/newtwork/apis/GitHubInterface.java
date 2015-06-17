package com.nixsolutions.cupboard.newtwork.apis;

import com.nixsolutions.cupboard.entities.GitHubOrganization;
import com.nixsolutions.cupboard.entities.GitHubUser;

import java.util.List;

import retrofit.http.GET;
import retrofit.http.Path;
import rx.Observable;

public interface GitHubInterface {

    @GET("/users/{username}")
    GitHubUser getUser(@Path("username") String username);

    @GET("/users")
    List<GitHubUser> getUsers();

    @GET("/users")
    Observable<List<GitHubUser>> getUsersAsync();

    @GET("/users/{username}/orgs")
    List<GitHubOrganization> getUserOrganizations(@Path("username") String username);

}
