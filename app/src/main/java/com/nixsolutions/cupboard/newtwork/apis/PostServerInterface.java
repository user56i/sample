package com.nixsolutions.cupboard.newtwork.apis;

import com.nixsolutions.cupboard.entities.GitHubUser;

import retrofit.client.Response;
import retrofit.http.Body;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.Headers;
import retrofit.http.Multipart;
import retrofit.http.POST;
import retrofit.http.Part;
import retrofit.mime.TypedFile;
import rx.Observable;

public interface PostServerInterface {


    @POST("/post.php?dir=vasia&dump")
    Observable<Response> request(@Body GitHubUser user);


    @FormUrlEncoded
    @Headers("Dummy header : 1337")
    @POST("/post.php?dir=vasia&dump&status_code=403")
    Observable<Response> request(@Field("login") String user, @Field("pwd") String pwd);

    @Multipart
//    @FormUrlEncoded
    @POST("/post.php?dir=vasia&dump")
    Observable<Response> request(@Part("login") String user, @Part("pwd") String pwd, @Part("photo") TypedFile photo);
}
