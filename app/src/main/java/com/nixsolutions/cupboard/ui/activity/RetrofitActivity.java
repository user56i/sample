package com.nixsolutions.cupboard.ui.activity;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.nixsolutions.cupboard.R;
import com.nixsolutions.cupboard.database.ContentDescriptor;
import com.nixsolutions.cupboard.entities.GitHubUser;
import com.nixsolutions.cupboard.Application;
import com.nixsolutions.cupboard.ui.navigation.BaseActivity;
import com.nixsolutions.cupboard.newtwork.apis.GitHubInterface;

import java.util.LinkedList;
import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import nl.qbusict.cupboard.CupboardFactory;
import nl.qbusict.cupboard.ProviderCompartment;
import rx.Observable;
import rx.functions.Action1;
import rx.functions.Action2;
import rx.functions.Func0;
import rx.functions.Func1;
import rx.schedulers.Schedulers;


public class RetrofitActivity extends BaseActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    @InjectView(R.id.listView)
    ListView listView;

    @InjectView(R.id.editText)
    EditText editText;

    @Inject
    GitHubInterface gitHubInterface;

    private ProviderCompartment compartment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fetrofit);
        ButterKnife.inject(this);

        compartment = CupboardFactory.getInstance().withContext(Application.getStaticContext());

        getSupportLoaderManager().initLoader(0, null, this);

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                getSupportLoaderManager().restartLoader(0, null, RetrofitActivity.this);
            }
        });
    }

    @OnClick(R.id.rxTest)
    public void onTestClick() {
        gitHubInterface.getUsersAsync()
                .subscribeOn(Schedulers.newThread())
                .subscribe(getCachingAction(), getErrorHandler());
    }

    private Action1<Throwable> getErrorHandler() {
        return new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                Toast.makeText(Application.getStaticContext(), throwable.getMessage(), Toast.LENGTH_LONG).show();
            }
        };
    }

    private Action1<List<GitHubUser>> getCachingAction() {
        return new Action1<List<GitHubUser>>() {
            @Override
            public void call(List<GitHubUser> gitHubUsers) {
                compartment.put(ContentDescriptor.getUri(GitHubUser.class), GitHubUser.class, gitHubUsers);
            }
        };
    }

    private Action2<List<GitHubUser>, GitHubUser> getItemFactory() {
        return new Action2<List<GitHubUser>, GitHubUser>() {
            @Override
            public void call(List<GitHubUser> gitHubUsers, GitHubUser gitHubUser) {
                gitHubUsers.add(gitHubUser);
            }
        };
    }

    private Func0<List<GitHubUser>> getContainer() {
        return new Func0<List<GitHubUser>>() {
            @Override
            public List<GitHubUser> call() {
                return new LinkedList<>();
            }
        };
    }

    private Func1<GitHubUser, Boolean> getFilterFunc(final String s) {
        return new Func1<GitHubUser, Boolean>() {
            @Override
            public Boolean call(GitHubUser gitHubUser) {
                return gitHubUser.getLogin().startsWith(s)
                        || String.valueOf(gitHubUser.getId()).startsWith(s)
                        || gitHubUser.getReposUrl().startsWith(s);
            }
        };
    }

    private Func1<List<GitHubUser>, Observable<GitHubUser>> getMapFunc() {
        return new Func1<List<GitHubUser>, Observable<GitHubUser>>() {
            @Override
            public Observable<GitHubUser> call(List<GitHubUser> gitHubUsers) {
                return Observable.from(gitHubUsers);
            }
        };
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(this, ContentDescriptor.getUri(GitHubUser.class), null, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

        List<GitHubUser> list = CupboardFactory.getInstance().withCursor(data).list(GitHubUser.class);

        if (TextUtils.isEmpty(editText.getText().toString().trim())) {
            setToAdapter(list);
        } else {
            Observable.from(list)
                    .filter(getFilterFunc(editText.getText().toString()))
                    .collect(getContainer(), getItemFactory())
                    .subscribe(createSettingListAction());
        }
    }

    private Action1<List<GitHubUser>> createSettingListAction() {
        return new Action1<List<GitHubUser>>() {
            @Override
            public void call(List<GitHubUser> gitHubUsers) {
                setToAdapter(gitHubUsers);
            }
        };
    }

    private void setToAdapter(List<GitHubUser> gitHubUsers) {
        ArrayAdapter<GitHubUser> adapter = new ArrayAdapter<>(Application.getStaticContext(), android.R.layout.simple_list_item_1, gitHubUsers);
        listView.setAdapter(adapter);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
