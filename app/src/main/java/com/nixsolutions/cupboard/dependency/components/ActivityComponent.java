package com.nixsolutions.cupboard.dependency.components;

import com.nixsolutions.cupboard.dependency.modules.ActivityModule;
import com.nixsolutions.cupboard.ui.navigation.BaseActivity;
import com.nixsolutions.cupboard.ui.navigation.UiMediator;

import dagger.Component;
import nl.qbusict.cupboard.Cupboard;
import nl.qbusict.cupboard.ProviderCompartment;


@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {

    void inject(BaseActivity activity);

    Cupboard getCupboard();

    ProviderCompartment getCompartment();

    UiMediator getUiMediator();
}
