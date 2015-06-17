package com.nixsolutions.cupboard.entities;

import javax.inject.Inject;

public class Vehicle {

    private VEngine engine;

    @Inject
    public Vehicle(VEngine engine) {
        this.engine = engine;
    }

    public void gas() {
        engine.setRpm(engine.getRpm() + 10);

    }

    public void stop() {
        engine.setRpm(0);
    }

    @Override
    public String toString() {
        return String.valueOf("RPM" + engine.getRpm());
    }
}
