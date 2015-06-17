package com.nixsolutions.cupboard.ui.presenters;

import com.nixsolutions.cupboard.entities.User;

import java.util.List;

public interface IUsersPresenter {

    void deleteUser(int position);

    void addUser(String name, String sername);

    List<User> getUsers();
}
