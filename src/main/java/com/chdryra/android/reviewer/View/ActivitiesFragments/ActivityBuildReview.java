package com.chdryra.android.reviewer.View.ActivitiesFragments;

import android.content.Intent;

import com.chdryra.android.reviewer.Adapter.DataAdapterModel.DataValidator;
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewBuilder;
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewBuilderAdapter;
import com.chdryra.android.reviewer.ApplicationContexts.ApplicationContext;
import com.chdryra.android.reviewer.ApplicationSingletons.Administrator;
import com.chdryra.android.reviewer.View.Launcher.LaunchableUi;
import com.chdryra.android.reviewer.View.Launcher.LauncherUi;
import com.chdryra.android.reviewer.View.Screens.BuildScreen;
import com.chdryra.android.reviewer.View.Screens.ReviewView;

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
        Administrator admin = Administrator.getInstance(this);
        ApplicationContext appContext = admin.getApplicationContext();
        DataValidator validator = appContext.getDataValidator();
        ReviewBuilder builder = new ReviewBuilder(appContext.getAuthor(),
                appContext.getMdGvConverter(), appContext.getTagsManager(),
                appContext.getReviewFactory(), validator);
        ReviewBuilderAdapter adapter = new ReviewBuilderAdapter(this, builder, validator);
        admin.setReviewBuilderAdapter(adapter);
        mBuildScreen = new BuildScreen(this, adapter);
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
