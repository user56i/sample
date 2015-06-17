package com.nixsolutions.cupboard.entities;

public class TestBean {

    public Long _id;

    public State state;

    public Long get_id() {
        return _id;
    }

    public void set_id(Long _id) {
        this._id = _id;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public static class State {

        public int pos;
        public int pos1;
        public int pos2;
    }
}
