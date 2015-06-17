package com.nixsolutions.cupboard.dependency.components;

import com.nixsolutions.cupboard.dependency.modules.PostServerModule;
import com.nixsolutions.cupboard.newtwork.apis.PostServerInterface;
import com.nixsolutions.cupboard.ui.activity.PostActivity;

import dagger.Component;

@Component(modules = PostServerModule.class)
public interface PostServerComponent {

    void inject(PostActivity activity);

    PostServerInterface get();
}
