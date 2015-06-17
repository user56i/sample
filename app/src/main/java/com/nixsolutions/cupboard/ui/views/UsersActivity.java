package com.nixsolutions.cupboard.ui.views;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.nixsolutions.cupboard.R;
import com.nixsolutions.cupboard.dependency.components.ApplicationComponent;
import com.nixsolutions.cupboard.dependency.components.DaggerUsersComponent;
import com.nixsolutions.cupboard.dependency.modules.ActivityModule;
import com.nixsolutions.cupboard.dependency.modules.UsersProviderModule;
import com.nixsolutions.cupboard.entities.User;
import com.nixsolutions.cupboard.newtwork.apis.GitHubInterface;
import com.nixsolutions.cupboard.ui.navigation.BaseActivity;
import com.nixsolutions.cupboard.ui.presenters.IUsersPresenter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import butterknife.OnItemClick;

public class UsersActivity extends BaseActivity implements IUsersView {

    private static final String DIALOG = "dialog";
    @InjectView(R.id.name)
    EditText name;

    @InjectView(R.id.sername)
    EditText sername;

    @InjectView(R.id.list)
    ListView listView;

    @Inject
    IUsersPresenter presenter;

    @Inject
    public GitHubInterface apiInterface;

    private ArrayAdapter<User> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_mvp_main);
        ButterKnife.inject(this);

        adapter = new ArrayAdapter<User>(this, android.R.layout.simple_list_item_1, new ArrayList<User>());

        showList(presenter.getUsers());
    }

    @Override
    protected void buildActivityComponent(ApplicationComponent applicationComponent) {
        DaggerUsersComponent.builder()
                .applicationComponent(applicationComponent)
                .activityModule(new ActivityModule(this))
                .usersProviderModule(new UsersProviderModule(this))
                .build()
                .inject(this);
    }


    @Override
    public void showList(final List<User> users) {
        adapter.clear();
        adapter.addAll(users);
        listView.setAdapter(adapter);
    }

    @Override
    public void updateList() {
        showList(presenter.getUsers());
        adapter.notifyDataSetChanged();
        listView.invalidate();
    }

    @Override
    public void showProgress() {
        new ProgressFragment().show(getSupportFragmentManager(), DIALOG);
    }

    @Override
    public void hideProgress() {
        Fragment dialog = getSupportFragmentManager().findFragmentByTag(DIALOG);
        if (dialog != null) {
            ((DialogFragment) dialog).dismissAllowingStateLoss();
        }
    }

    @Override
    public void showError(String empty) {
        Toast.makeText(this, empty, Toast.LENGTH_LONG).show();
    }

    @OnClick(R.id.add_button)
    public void onAddButton() {
        presenter.addUser(name.getText().toString(), sername.getText().toString());
    }

    @OnItemClick(R.id.list)
    void onItemClick(int position) {
        presenter.deleteUser(position);
    }

    @Override
    public Context getContext() {
        return getApplicationContext();
    }
}
