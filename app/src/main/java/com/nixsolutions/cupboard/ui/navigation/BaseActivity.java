package com.nixsolutions.cupboard.ui.navigation;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.nixsolutions.cupboard.Application;
import com.nixsolutions.cupboard.dependency.components.ApplicationComponent;

public class BaseActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        buildActivityComponent(getApplicationComponent());
    }

    protected void buildActivityComponent(ApplicationComponent applicationComponent) {

    }


    protected ApplicationComponent getApplicationComponent() {
        return ((Application) getApplicationContext()).getApplicationComponent();
    }
}
