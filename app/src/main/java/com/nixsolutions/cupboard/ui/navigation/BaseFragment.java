package com.nixsolutions.cupboard.ui.navigation;

import android.support.v4.app.Fragment;

import com.nixsolutions.cupboard.newtwork.apis.GitHubInterface;

import javax.inject.Inject;

public class BaseFragment extends Fragment {

    @Inject
    public GitHubInterface apiInterface;
}
