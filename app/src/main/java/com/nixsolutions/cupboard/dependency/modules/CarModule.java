package com.nixsolutions.cupboard.dependency.modules;

import com.nixsolutions.cupboard.entities.VEngine;
import com.nixsolutions.cupboard.entities.Vehicle;

import dagger.Module;
import dagger.Provides;

@Module
public class CarModule {

    @Provides
    public Vehicle provideVehicle(VEngine engine) {
        return new Vehicle(engine);
    }

    @Provides
    public VEngine provideVEngine() {
        return new VEngine();
    }
}
