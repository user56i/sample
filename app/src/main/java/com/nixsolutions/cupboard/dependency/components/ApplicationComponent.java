package com.nixsolutions.cupboard.dependency.components;


import android.content.Context;

import com.nixsolutions.cupboard.dependency.modules.ApplicationModule;
import com.nixsolutions.cupboard.dependency.modules.RetrofitModule;
import com.nixsolutions.cupboard.ui.navigation.BaseActivity;
import com.nixsolutions.cupboard.ui.navigation.BaseFragment;
import com.nixsolutions.cupboard.newtwork.apis.GitHubInterface;

import dagger.Component;

@Component(modules = {
        ApplicationModule.class,
        RetrofitModule.class})
public interface ApplicationComponent {

    void inject(BaseActivity activity);

    void inject(BaseFragment fragment);

    GitHubInterface getAPI();

    Context context();
}
