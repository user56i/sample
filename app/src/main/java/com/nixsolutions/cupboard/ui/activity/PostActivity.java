package com.nixsolutions.cupboard.ui.activity;

import android.os.Bundle;
import android.os.Environment;

import com.nixsolutions.cupboard.R;
import com.nixsolutions.cupboard.entities.GitHubUser;
import com.nixsolutions.cupboard.ui.navigation.BaseActivity;
import com.nixsolutions.cupboard.newtwork.apis.PostServerInterface;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.inject.Inject;

import rx.android.view.OnClickEvent;
import rx.android.view.ViewObservable;
import rx.functions.Action1;


public class PostActivity extends BaseActivity {

    @Inject
    PostServerInterface postServerInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        ViewObservable.clicks(findViewById(R.id.post)).subscribe(new Action1<OnClickEvent>() {
            @Override
            public void call(OnClickEvent onClickEvent) {
                GitHubUser user = new GitHubUser();
                user.setId(2L);
                user.setUrl("das");
                user.setLogin("log");

                File file = new File(Environment.getExternalStorageDirectory(), "/f.java");

                try {
                    if (!file.exists()) {
                        file.createNewFile();

                        FileWriter write = new FileWriter(file);

                        BufferedWriter bufferedWriter = new BufferedWriter(write);

                        bufferedWriter.write("llllllllllllllllllllllllol");

                        bufferedWriter.close();
                        write.close();
                    }

//                    postServerInterface.request(user).subscribe();
                    postServerInterface.request("bill", "murrey").subscribe();
//                    postServerInterface.request("bill", "murrey", new TypedFile("text.java", file)).subscribe();

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });
    }
}
