package com.nixsolutions.cupboard.dependency.modules;

import com.nixsolutions.cupboard.newtwork.apis.PostServerInterface;

import java.util.HashMap;

import dagger.Module;
import dagger.Provides;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;

@Module
public class PostServerModule extends RetrofitModule {
    private String postUrl;
    private final HashMap<String, String> headers;

    public PostServerModule(String baseUrl,String postUrl, HashMap<String, String> headers) {
        super(baseUrl, headers);

        this.postUrl = postUrl;
        this.headers = headers;
    }

    @Provides
    public PostServerInterface providePostServerInterface(RequestInterceptor interceptor) {
        return new RestAdapter.Builder()
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setRequestInterceptor(interceptor)
                .setEndpoint(postUrl)
                .build().create(PostServerInterface.class);
    }
}
