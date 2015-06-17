package com.nixsolutions.cupboard.ui.activity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.nixsolutions.cupboard.R;
import com.nixsolutions.cupboard.database.ContentDescriptor;
import com.nixsolutions.cupboard.entities.GitHubOrganization;
import com.nixsolutions.cupboard.entities.GitHubUser;
import com.nixsolutions.cupboard.entities.GitHubUserWithOrganizationUnion;
import com.nixsolutions.cupboard.ui.navigation.BaseActivity;
import com.nixsolutions.cupboard.newtwork.apis.GitHubInterface;

import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import nl.qbusict.cupboard.CupboardFactory;
import nl.qbusict.cupboard.ProviderCompartment;


public class UserActivity extends BaseActivity {

    public static final String ID = "id";
    private GitHubUser user;
    private ProviderCompartment compartment;


    @Inject
    GitHubInterface gitHubInterface;
    @InjectView(R.id.recycle)
    RecyclerView recyclerView;
    private Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        ButterKnife.inject(this);
        compartment = CupboardFactory.getInstance().withContext(this);

        Bundle extras = getIntent().getExtras();

        initView();

        init(extras);
    }

    private void initView() {
        adapter = new Adapter(getApplicationContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getBaseContext()));
    }

    private void init(Bundle extras) {
        if (extras != null) {
            long userId = extras.getLong(ID);


            user = compartment.query(ContentDescriptor.getUri(GitHubUser.class), GitHubUser.class)
                    .withSelection("_id=?", String.valueOf(userId)).get();

            new Task().execute();
        } else {
            goQwery();
        }
    }

    private void goQwery() {
        List<GitHubUserWithOrganizationUnion> list;

        if (user != null) {
            list = compartment.query(
                    ContentDescriptor.getUri(GitHubUserWithOrganizationUnion.class),
                    GitHubUserWithOrganizationUnion.class)
                    .withSelection(" login =? OR login is null", user.getLogin())
                    .list();
        } else {
            list = compartment.query(
                    ContentDescriptor.getUri(GitHubUserWithOrganizationUnion.class),
                    GitHubUserWithOrganizationUnion.class)
                    .withSelection(" login is null", null)
                    .list();
        }

        adapter.setList(list);
    }

    public class Task extends AsyncTask<Void, Void, List<GitHubOrganization>> {

        @Override
        protected List<GitHubOrganization> doInBackground(Void... params) {


            List<GitHubOrganization> users = null;
            try {
                users = gitHubInterface.getUserOrganizations(user.getLogin());
            } catch (Exception e) {
                Log.e("qq", null, e);
            }

            return users;
        }

        @Override
        protected void onPostExecute(List<GitHubOrganization> organizations) {
            super.onPostExecute(organizations);

            UserActivity activity = UserActivity.this;

            CupboardFactory.getInstance().withContext(activity).delete(
                    ContentDescriptor.getUri(GitHubOrganization.class),
                    null, null);

            CupboardFactory.getInstance().withContext(activity).put(
                    ContentDescriptor.getUri(GitHubOrganization.class),
                    GitHubOrganization.class,
                    organizations);

            goQwery();
        }
    }

}
