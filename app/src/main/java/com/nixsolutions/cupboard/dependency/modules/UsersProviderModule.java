package com.nixsolutions.cupboard.dependency.modules;

import com.nixsolutions.cupboard.ui.models.IUsersModel;
import com.nixsolutions.cupboard.ui.models.UsersModel;
import com.nixsolutions.cupboard.ui.presenters.IUsersPresenter;
import com.nixsolutions.cupboard.ui.presenters.UsersPresenter;
import com.nixsolutions.cupboard.ui.views.IUsersView;

import dagger.Module;
import dagger.Provides;
import nl.qbusict.cupboard.CupboardFactory;
import nl.qbusict.cupboard.ProviderCompartment;


@Module
public class UsersProviderModule {

    private IUsersView mainView;

    public UsersProviderModule(IUsersView mainView) {
        this.mainView = mainView;
    }

    @Provides
    public IUsersPresenter provideMainPresenter(IUsersModel model) {
        return new UsersPresenter(mainView, model);
    }

    @Provides
    public IUsersModel provideMainModel(ProviderCompartment compartment) {
        return new UsersModel(compartment);
    }

    @Provides
    public ProviderCompartment provideCompartment() {
        return CupboardFactory.cupboard().withContext(mainView.getContext());
    }
}
