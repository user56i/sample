package com.nixsolutions.cupboard.dependency.components;

import com.nixsolutions.cupboard.dependency.modules.ActivityModule;
import com.nixsolutions.cupboard.dependency.modules.UsersProviderModule;
import com.nixsolutions.cupboard.ui.views.UsersActivity;

import dagger.Component;

@Component(dependencies = {ApplicationComponent.class, ActivityModule.class},
        modules = UsersProviderModule.class)
public interface UsersComponent {

    void inject(UsersActivity view);
}
