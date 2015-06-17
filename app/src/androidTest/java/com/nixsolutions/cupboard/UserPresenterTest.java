package com.nixsolutions.cupboard;

import android.content.ContentProvider;
import android.test.ProviderTestCase2;

import com.nixsolutions.cupboard.database.ContentDescriptor;
import com.nixsolutions.cupboard.database.CupboardContentProvider;
import com.nixsolutions.cupboard.entities.User;
import com.nixsolutions.cupboard.ui.models.UsersModel;
import com.nixsolutions.cupboard.ui.presenters.UsersPresenter;
import com.nixsolutions.cupboard.ui.views.IUsersView;

import org.mockito.Mockito;

import nl.qbusict.cupboard.CupboardFactory;
import nl.qbusict.cupboard.ProviderCompartment;
import rx.Observable;

public class UserPresenterTest extends ProviderTestCase2<CupboardContentProvider> {


    public UserPresenterTest() {
        super(CupboardContentProvider.class, ContentDescriptor.AUTHORITY);
    }

    public void testAddUser() {

        ProviderCompartment compartment = CupboardFactory.cupboard().withContext(getMockContext());

        IUsersView view = Mockito.mock(IUsersView.class);
        UsersPresenter presenter = new UsersPresenter(view, new UsersModel(compartment));

        String u1 = "u1";
        presenter.addUser(u1, "p1");
        presenter.addUser("u2", "p2");
        presenter.addUser("u3", "p3");

        assertEquals(3, presenter.getUsers().size());

        presenter.deleteUser(0);
        assertEquals(2, presenter.getUsers().size());

        for(User u : presenter.getUsers()){
            assertFalse(u.getName().equals(u1));
        }

    }
}
