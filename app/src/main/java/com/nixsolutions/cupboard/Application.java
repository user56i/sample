package com.nixsolutions.cupboard;


import android.content.Context;

import com.nixsolutions.cupboard.dependency.components.ApplicationComponent;
import com.nixsolutions.cupboard.dependency.components.DaggerApplicationComponent;
import com.nixsolutions.cupboard.dependency.modules.ApplicationModule;
import com.nixsolutions.cupboard.dependency.modules.RetrofitModule;


public class Application extends android.app.Application {

    private static Context staticContext;
    private ApplicationComponent applicationComponent;

    public static Context getStaticContext() {
        return staticContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        staticContext = this.getApplicationContext();
        initApplicationComponent();
    }

    private void initApplicationComponent() {
        applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .retrofitModule(new RetrofitModule(Constants.BASE_URL, Constants.BASE_HEADER))
                .build();
    }

    public ApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }
}
