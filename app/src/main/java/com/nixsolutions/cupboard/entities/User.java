package com.nixsolutions.cupboard.entities;

public class User {

    private String name;
    private String sername;

    public User() {
    }

    public User(String name, String sername) {
        this.name = name;
        this.sername = sername;
    }

    public String getName() {
        return name;
    }

    public String getSername() {
        return sername;
    }

    public void setSername(String sername) {
        this.sername = sername;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name + "\n" + sername;
    }
}
