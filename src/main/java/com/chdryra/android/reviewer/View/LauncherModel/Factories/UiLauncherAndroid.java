/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.View.LauncherModel.Factories;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;

import com.chdryra.android.mygenerallibrary.Dialogs.DialogShower;
import com.chdryra.android.reviewer.Application.AndroidApp.ReviewViewPacker;
import com.chdryra.android.reviewer.Presenter.Interfaces.View.ReviewView;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.View.ReviewsListView;
import com.chdryra.android.reviewer.View.LauncherModel.Interfaces.LaunchableConfig;
import com.chdryra.android.reviewer.View.LauncherModel.Interfaces.LaunchableUi;
import com.chdryra.android.reviewer.View.LauncherModel.Interfaces.LauncherUi;

/**
 * Created by: Rizwan Choudrey
 * On: 14/05/2015
 * Email: rizwan.choudrey@gmail.com
 * <p/>
 * Knows how to launch a {@link LaunchableUi} depending on whether
 * it is a Dialog or Activity underneath.
 */

public class UiLauncherAndroid implements UiLauncher {
    private Activity mCommissioner;
    private Class<? extends Activity> mDefaultActivity;
    private Class<? extends Activity> mReviewsListActivity;

    public UiLauncherAndroid(Activity commissioner,
                             Class<? extends Activity> defaultActivity,
                             Class<? extends Activity> reviewsListActivity) {
        mCommissioner = commissioner;
        mDefaultActivity = defaultActivity;
        mReviewsListActivity = reviewsListActivity;
    }

    @Override
    public void launch(LaunchableUi ui, int requestCode, Bundle args) {
        ui.launch(new AndroidLauncher(mCommissioner, requestCode, ui.getLaunchTag(), args));
    }

    @Override
    public void launch(LaunchableUi ui, int requestCode) {
        launch(ui, requestCode, new Bundle());
    }

    @Override
    public void launch(LaunchableConfig config, int requestCode, Bundle args) {
        launch(config.getLaunchable(), requestCode, args);
    }

    @Override
    public void launch(LaunchableConfig config, int requestCode) {
        launch(config.getLaunchable(), requestCode, new Bundle());
    }

    private class AndroidLauncher implements LauncherUi {
        private final Activity mCommissioner;
        private final int mRequestCode;
        private final String mTag;
        private final Bundle mArgs;

        public AndroidLauncher(Activity commissioner, int requestCode, String tag, Bundle args) {
            mCommissioner = commissioner;
            mRequestCode = requestCode;
            mTag = tag;
            mArgs = args;
        }

        @Override
        public Activity getCommissioner() {
            return mCommissioner;
        }

        @Override
        public void launch(DialogFragment launchableUI) {
            DialogShower.show(launchableUI, mCommissioner, mRequestCode, mTag, mArgs);
        }

        @Override
        public void launch(Class<? extends Activity> activityClass, String argsKey) {
            launch(new Intent(mCommissioner, activityClass), argsKey);
        }

        @Override
        public void launch(ReviewView<?> view) {
            launchReviewView(view, mDefaultActivity);
        }

        @Override
        public void launch(ReviewsListView view) {
            launchReviewView(view, mReviewsListActivity);
        }

        @Override
        public void launch(Intent i, String argsKey) {
            i.putExtra(argsKey, mArgs);
            mCommissioner.startActivityForResult(i, mRequestCode);
        }

        private void launchReviewView(ReviewView<?> view, Class<? extends Activity> activity) {
            Intent i = new Intent(mCommissioner, activity);
            ReviewViewPacker.packView(mCommissioner, view, i);
            mCommissioner.startActivityForResult(i, mRequestCode);
        }
    }
}
