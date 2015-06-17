package com.nixsolutions.cupboard.dependency.modules;

import android.content.Context;

import com.nixsolutions.cupboard.ui.navigation.BaseActivity;
import com.nixsolutions.cupboard.ui.navigation.UiMediator;

import dagger.Module;
import dagger.Provides;
import nl.qbusict.cupboard.Cupboard;
import nl.qbusict.cupboard.CupboardFactory;
import nl.qbusict.cupboard.ProviderCompartment;

@Module
public class ActivityModule {
    private BaseActivity activity;

    public ActivityModule(BaseActivity activity) {
        this.activity = activity;
    }

    @Provides
    public Cupboard provideCupboard() {
        return CupboardFactory.cupboard();
    }

    @Provides
    public ProviderCompartment provideCompartment(Cupboard cupboard, Context context) {
        return cupboard.withContext(context);
    }

    @Provides
    public UiMediator provideUiMediator() {
        return new UiMediator(activity);
    }


}
