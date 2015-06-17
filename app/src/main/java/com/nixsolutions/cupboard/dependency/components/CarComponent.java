package com.nixsolutions.cupboard.dependency.components;

import com.nixsolutions.cupboard.dependency.modules.CarModule;
import com.nixsolutions.cupboard.entities.Vehicle;

import dagger.Component;

@Component(modules = CarModule.class)
public interface CarComponent {

    Vehicle createVehicle();
}
