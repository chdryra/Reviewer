/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.PlugIns.UiPlugin.UiAndroid.Implementation.Activities;

import android.app.Fragment;

import com.chdryra.android.mygenerallibrary.ActivitySingleFragment;
import com.chdryra.android.reviewer.PlugIns.UiPlugin.UiAndroid.Implementation.Fragments.FragmentFacebookLogin;
import com.chdryra.android.reviewer.View.LauncherModel.Interfaces.LaunchableUi;
import com.chdryra.android.reviewer.View.LauncherModel.Interfaces.LauncherUi;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;

/**
 * Created by: Rizwan Choudrey
 * On: 23/02/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class ActivitySocialLogin extends ActivitySingleFragment
        implements FragmentFacebookLogin.FacebookLoginListener, LaunchableUi {
    private static final String TAG = "ActivitySocialLogin";
    private static final String KEY = "ActivitySocialLogin.platform";

    @Override
    protected Fragment createFragment() {
        return new FragmentFacebookLogin();
    }

    @Override
    public void onSuccess(LoginResult loginResult) {
        setResult(RESULT_OK);
        finish();
    }

    @Override
    public void onError(FacebookException error) {
        setResult(RESULT_FIRST_USER);
        finish();
    }

    @Override
    public String getLaunchTag() {
        return TAG;
    }

    @Override
    public void launch(LauncherUi launcher) {
        launcher.launch(getClass(), KEY);
    }
}
