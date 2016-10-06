/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.View.LauncherModel.Implementation;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Intent;
import android.support.annotation.Nullable;

import com.chdryra.android.reviewer.Application.Implementation.ReviewViewPacker;
import com.chdryra.android.reviewer.ApplicationContexts.Interfaces.UserSession;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.Activities.ActivityEditData;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.Dialogs.Implementation.DialogShower;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.ReviewId;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvDataParcelable;
import com.chdryra.android.reviewer.Presenter.Interfaces.View.ReviewView;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Interfaces.BuildScreenLauncher;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Interfaces.ImageChooser;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Factories.FactoryReviewLauncher;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvDataType;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.ReviewLauncher.ReviewLauncher;
import com.chdryra.android.reviewer.View.LauncherModel.Interfaces.LaunchableUi;
import com.chdryra.android.reviewer.View.LauncherModel.Interfaces.UiLauncher;
import com.chdryra.android.reviewer.View.LauncherModel.Interfaces.UiTypeLauncher;

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
    private UserSession mSession;

    private final BuildScreenLauncher mBuildUiLauncher;
    private final Class<? extends Activity> mDefaultActivity;
    private final FactoryReviewLauncher mReviewLauncherFactory;

    public UiLauncherAndroid(BuildScreenLauncher buildUiLauncher,
                             FactoryReviewLauncher reviewLauncherFactory,
                             Class<? extends Activity> defaultActivity) {
        mBuildUiLauncher = buildUiLauncher;
        mReviewLauncherFactory = reviewLauncherFactory;
        mDefaultActivity = defaultActivity;
    }

    public void setActivity(Activity activity) {
        mCommissioner = activity;
    }

    public void setSession(UserSession session) {
        mSession = session;
    }

    @Override
    public void launch(LaunchableUi ui, UiLauncherArgs args) {
        ui.launch(new AndroidTypeLauncher(mCommissioner, ui.getLaunchTag(), args));
    }

    @Override
    public void launchBuildUi(@Nullable ReviewId template) {
        mBuildUiLauncher.launch(template);
    }

    @Override
    public void launchEditDataUi(GvDataType<? extends GvDataParcelable> dataType) {
        ActivityEditData.start(mCommissioner, dataType);
    }

    @Override
    public void launchImageChooser(ImageChooser chooser, int requestCode) {
        mCommissioner.startActivityForResult(chooser.getChooserIntents(), requestCode);
    }

    @Override
    public ReviewLauncher newReviewLauncher() {
        return mReviewLauncherFactory.newReviewLauncher(this, mSession.getAuthorId());
    }

    private class AndroidTypeLauncher implements UiTypeLauncher {
        private final Activity mCommissioner;
        private final String mTag;
        private final UiLauncherArgs mArgs;

        public AndroidTypeLauncher(Activity commissioner, String tag, UiLauncherArgs args) {
            mCommissioner = commissioner;
            mTag = tag;
            mArgs = args;
        }

        @Override
        public void launch(DialogFragment launchableUI) {
            DialogShower.show(launchableUI, mCommissioner, mArgs.getRequestCode(), mTag, mArgs.getBundle());
        }

        @Override
        public void launch(Class<? extends Activity> activityClass, String argsKey) {
            Intent i = new Intent(mCommissioner, activityClass);
            i.putExtra(argsKey, mArgs.getBundle());
            if(mArgs.isClearBackStack()) i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            mCommissioner.startActivityForResult(i, mArgs.getRequestCode());
        }

        @Override
        public void launch(ReviewView<?> view) {
            launchReviewView(view, mDefaultActivity);
        }


        private void launchReviewView(ReviewView<?> view, Class<? extends Activity> activity) {
            Intent i = new Intent(mCommissioner, activity);
            ReviewViewPacker.packView(view, i);
            mCommissioner.startActivityForResult(i, mArgs.getRequestCode());
        }
    }
}
