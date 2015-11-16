package com.chdryra.android.reviewer.View.ActivitiesFragments;

import android.content.Intent;

import com.chdryra.android.reviewer.ApplicationSingletons.ApplicationInstance;
import com.chdryra.android.reviewer.View.Launcher.LaunchableUi;
import com.chdryra.android.reviewer.View.Launcher.LauncherUi;
import com.chdryra.android.reviewer.View.Screens.BuildScreen;
import com.chdryra.android.reviewer.View.Screens.Interfaces.ReviewView;

/**
 * Created by: Rizwan Choudrey
 * On: 18/10/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ActivityBuildReview extends ActivityReviewView implements LaunchableUi {
    private static final String TAG = "BuildScreen";
    private BuildScreen mBuildScreen;

    @Override
    protected ReviewView createReviewView() {
        ApplicationInstance admin = ApplicationInstance.getInstance(this);
        if(admin.getReviewBuilderAdapter() == null) admin.newReviewBuilderAdapter();
        mBuildScreen = new BuildScreen(this, admin.getReviewBuilderAdapter());

        return mBuildScreen.getEditor();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        mBuildScreen.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public String getLaunchTag() {
        return TAG;
    }

    @Override
    public void launch(LauncherUi launcher) {
        launcher.launch(this);
    }
}
