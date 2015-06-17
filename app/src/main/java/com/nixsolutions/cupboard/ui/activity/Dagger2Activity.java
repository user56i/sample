package com.nixsolutions.cupboard.ui.activity;

import android.os.Bundle;
import android.widget.Toast;

import com.nixsolutions.cupboard.R;
import com.nixsolutions.cupboard.dependency.components.ApplicationComponent;
import com.nixsolutions.cupboard.dependency.components.DaggerActivityComponent;
import com.nixsolutions.cupboard.dependency.modules.ActivityModule;
import com.nixsolutions.cupboard.entities.Vehicle;
import com.nixsolutions.cupboard.newtwork.apis.GitHubInterface;
import com.nixsolutions.cupboard.ui.navigation.BaseActivity;

import javax.inject.Inject;

import nl.qbusict.cupboard.ProviderCompartment;


public class Dagger2Activity extends BaseActivity {

    @Inject
    ProviderCompartment compartment;
    @Inject
    GitHubInterface hubInterface;
    @Inject
    Vehicle vehicle;

    @Override
    protected void buildActivityComponent(ApplicationComponent applicationComponent) {

        DaggerActivityComponent.builder()
                .applicationComponent(applicationComponent)
                .activityModule(new ActivityModule(this))
                .build()
                .inject(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_dagger2);

        vehicle.gas();
        vehicle.gas();
        vehicle.gas();
        vehicle.gas();

        Toast.makeText(this, vehicle.toString(), Toast.LENGTH_LONG).show();

        vehicle.stop();

        Toast.makeText(this, vehicle.toString(), Toast.LENGTH_LONG).show();
    }

}
