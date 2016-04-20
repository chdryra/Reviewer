/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.Fragments;


import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.Activities.ActivityUsersFeed;
import com.chdryra.android.reviewer.R;

/**
 * Created by: Rizwan Choudrey
 * On: 23/02/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class FragmentLogin extends Fragment {
    private static final int LAYOUT = R.layout.fragment_login;
    private static final int LOGIN = R.id.login_email_button;

    private Button mLoginButton;

    public static FragmentLogin newInstance() {
        return new FragmentLogin();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedState) {
        View view = inflater.inflate(LAYOUT, container, false);

        mLoginButton = (Button) view.findViewById(LOGIN);

        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchFeedScreen();
            }
        });

        return view;
    }

    private void launchFeedScreen() {
        Intent intent = new Intent(getActivity(), ActivityUsersFeed.class);
        startActivity(intent);
        getActivity().finish();
    }
}
