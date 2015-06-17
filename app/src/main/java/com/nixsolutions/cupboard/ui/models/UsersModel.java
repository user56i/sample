package com.nixsolutions.cupboard.ui.models;

import com.nixsolutions.cupboard.database.ContentDescriptor;
import com.nixsolutions.cupboard.entities.User;

import java.util.List;

import javax.inject.Inject;

import nl.qbusict.cupboard.ProviderCompartment;
import rx.Observable;
import rx.Subscriber;

public class UsersModel implements IUsersModel {

    @Inject
    ProviderCompartment compartment;

    public UsersModel(ProviderCompartment compartment) {
        this.compartment = compartment;
    }

    @Override
    public List<User> getUsers() {
        return getUsersList();
    }

    @Override
    public Observable<List<User>> getUsersAsync() {
        return Observable.create(new Observable.OnSubscribe<List<User>>() {
            @Override
            public void call(Subscriber<? super List<User>> subscriber) {
                try {
                    List<User> list = getUsersList();

                    if (!subscriber.isUnsubscribed()) {
                        subscriber.onNext(list);
                    }
                } catch (Exception e) {
                    subscriber.onError(e);
                }
            }
        });
    }

    private List<User> getUsersList() {
        return compartment.query(
                ContentDescriptor.getUri(User.class),
                User.class).list();
    }

    @Override
    public void addUser(User user, SaveCallback callback) {
        compartment.put(ContentDescriptor.getUri(User.class), User.class, user);

        callback.onSavedItem();
    }

    @Override
    public void deleteUser(final User user, SaveCallback callback) {
        compartment.delete(
                ContentDescriptor.getUri(User.class),
                " name =? ", user.getName());

        callback.onSavedItem();
    }
}
