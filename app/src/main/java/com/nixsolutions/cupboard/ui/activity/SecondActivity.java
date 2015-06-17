package com.nixsolutions.cupboard.ui.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.nixsolutions.cupboard.R;
import com.nixsolutions.cupboard.database.ContentDescriptor;
import com.nixsolutions.cupboard.entities.GitHubUser;
import com.nixsolutions.cupboard.ui.navigation.BaseActivity;
import com.nixsolutions.cupboard.newtwork.apis.GitHubInterface;

import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import nl.qbusict.cupboard.CupboardFactory;
import nl.qbusict.cupboard.ProviderCompartment;


public class SecondActivity extends BaseActivity implements AdapterView.OnItemClickListener {

    @Inject
    GitHubInterface gitHubInterface;

    @InjectView(R.id.list)
    public ListView list;
    private List<GitHubUser> gitHubUsers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second_activity);
        ButterKnife.inject(this);

        list.setOnItemClickListener(this);
    }

    @OnClick(R.id.update)
    public void onUpdate() {
        new Task().execute();
        Toast.makeText(this, "updating", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(this, UserActivity.class);
        intent.putExtra(UserActivity.ID, gitHubUsers.get(position).getId());
        startActivity(intent);
    }

    public class Task extends AsyncTask<Void, Void, List<GitHubUser>> {

        @Override
        protected List<GitHubUser> doInBackground(Void... params) {

            List<GitHubUser> users = null;
            try {
                users = gitHubInterface.getUsers();
            } catch (Exception e) {
                Log.e("qq", null, e);

                Intent intent = new Intent(SecondActivity.this, UserActivity.class);
                startActivity(intent);
            }

            return users;
        }

        @Override
        protected void onPostExecute(List<GitHubUser> gitHubUsers) {
            super.onPostExecute(gitHubUsers);
            SecondActivity activity = SecondActivity.this;
            ProviderCompartment compartment = CupboardFactory.getInstance().withContext(activity);

            if (gitHubUsers != null && !gitHubUsers.isEmpty()) {
                compartment.delete(ContentDescriptor.getUri(GitHubUser.class), null, null);
                compartment.put(ContentDescriptor.getUri(GitHubUser.class), GitHubUser.class, gitHubUsers);
            } else {
                gitHubUsers = compartment.query(ContentDescriptor.getUri(GitHubUser.class), GitHubUser.class).list();
            }

            activity.gitHubUsers = gitHubUsers;

            list.setAdapter(new ArrayAdapter<GitHubUser>(activity, android.R.layout.simple_list_item_1, gitHubUsers));
        }
    }

}
