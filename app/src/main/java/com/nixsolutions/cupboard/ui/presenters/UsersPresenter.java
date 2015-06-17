package com.nixsolutions.cupboard.ui.presenters;

import android.text.TextUtils;

import com.nixsolutions.cupboard.entities.User;
import com.nixsolutions.cupboard.ui.models.IUsersModel;
import com.nixsolutions.cupboard.ui.models.SaveCallback;
import com.nixsolutions.cupboard.ui.views.IUsersView;

import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class UsersPresenter implements IUsersPresenter, SaveCallback {

    private IUsersView view;

//    @Inject
    IUsersModel model;

    public UsersPresenter(IUsersView view, IUsersModel model) {
        this.view = view;
        this.model = model;
    }

    @Override
    public void deleteUser(int position) {

        model.deleteUser(model.getUsers().get(position), new SaveCallback() {
            @Override
            public void onSavedItem() {
                view.updateList();
            }
        });
    }

    @Override
    public void addUser(String name, String sername) {

        if (name.trim().isEmpty() || sername.trim().isEmpty()) {
            view.showError("empty");
        } else {
            view.showProgress();
            model.addUser(new User(name, sername), this);
        }
    }

    @Override
    public List<User> getUsers() {
        return model.getUsers();
    }


    @Override
    public void onSavedItem() {

        model.getUsersAsync()
                .subscribeOn(Schedulers.computation())
                .delay(1, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<User>>() {
                    @Override
                    public void call(List<User> users) {
                        view.hideProgress();
                        view.showList(users);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        view.showError(throwable.getMessage());
                    }
                });
    }
}
