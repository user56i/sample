package com.nixsolutions.cupboard.ui.models;

import com.nixsolutions.cupboard.entities.User;

import java.util.List;

import rx.Observable;

public interface IUsersModel {

    List<User> getUsers();

    Observable<List<User>> getUsersAsync();

    void addUser(User user, SaveCallback callback);

    void deleteUser(User user, SaveCallback callback);

}
