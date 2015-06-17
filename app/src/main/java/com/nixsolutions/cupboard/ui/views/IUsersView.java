package com.nixsolutions.cupboard.ui.views;


import com.nixsolutions.cupboard.entities.User;

import java.util.List;

public interface IUsersView extends IBaseView {

    void showList(List<User> users);

    void updateList();

    void showProgress();

    void hideProgress();

    void showError(String empty);
}
