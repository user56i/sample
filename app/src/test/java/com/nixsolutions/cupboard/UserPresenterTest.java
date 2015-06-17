package com.nixsolutions.cupboard;

import android.content.Context;

import com.nixsolutions.cupboard.entities.User;
import com.nixsolutions.cupboard.ui.models.IUsersModel;
import com.nixsolutions.cupboard.ui.models.SaveCallback;
import com.nixsolutions.cupboard.ui.models.UsersModel;
import com.nixsolutions.cupboard.ui.presenters.UsersPresenter;
import com.nixsolutions.cupboard.ui.views.IUsersView;

import junit.framework.TestCase;

import java.util.List;
import java.util.concurrent.Semaphore;

public class UserPresenterTest extends TestCase {

    Semaphore locker = new Semaphore(0);

    public void testAddUser() throws InterruptedException {
        final String name = "name";
        final String sername = "sername";

        IUsersViewStub stub = new IUsersViewStub();
        IUsersModel mockedUserModel = new UsersModel(null) {
            @Override
            public void addUser(User user, SaveCallback callback) {

                locker.release();

                assertEquals(user.getName(), name);
                assertEquals(user.getName(), sername);
            }
        };

        UsersPresenter presenter = new UsersPresenter(stub, mockedUserModel);
        presenter.addUser("", "pass");
        assertTrue(stub.errorShowed);

        stub.reset();

        presenter.addUser(name, sername);

        assertFalse(stub.errorShowed);

        locker.acquire();
    }

    public static class IUsersViewStub implements IUsersView {

        public boolean progressHiden;
        public boolean errorShowed;

        @Override
        public void showList(List<User> users) {

        }

        @Override
        public void updateList() {

        }

        @Override
        public void showProgress() {
            progressHiden = false;
        }

        @Override
        public void hideProgress() {
            progressHiden = true;

        }

        @Override
        public void showError(String empty) {
            errorShowed = true;
        }

        @Override
        public Context getContext() {
            return null;
        }

        public void reset() {
            errorShowed = false;
            progressHiden = true;
        }
    }
}
