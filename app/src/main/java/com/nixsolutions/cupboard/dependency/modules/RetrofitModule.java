package com.nixsolutions.cupboard.dependency.modules;

import com.nixsolutions.cupboard.newtwork.apis.GitHubInterface;

import java.util.HashMap;

import dagger.Module;
import dagger.Provides;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;

@Module
public class RetrofitModule {
    private final String baseUrl;
    private final HashMap<String, String> headers;

    public RetrofitModule(String baseUrl, HashMap<String, String> headers) {
        this.baseUrl = baseUrl;
        this.headers = headers;
    }

    @Provides
    public GitHubInterface provideGitHubInterface(RestAdapter adapter) {
        return adapter.create(GitHubInterface.class);
    }

    @Provides
    public RestAdapter provideRestAdapter(RequestInterceptor interceptor) {
        return new RestAdapter.Builder()
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setRequestInterceptor(interceptor)
                .setEndpoint(baseUrl)
                .build();
    }

    @Provides
    public RequestInterceptor provideRequestInterceptor() {

        return new RequestInterceptor() {
            @Override
            public void intercept(RequestFacade request) {

                if (headers != null) {
                    for (String key : headers.keySet()) {
                        request.addHeader(key, headers.get(key));
                    }
                }
            }
        };
    }


}
