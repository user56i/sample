package com.nixsolutions.cupboard.entities;

public class Human {
    public Long _id = null;
    public String name;
    public String secondName;
    public String address;

    @Override
    public String toString() {
        return String.format("%s %s : %s", name, secondName, address);
    }
}
