package com.chdryra.android.reviewer.View.ActivitiesFragments;

import android.content.Intent;

import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewBuilding.Interfaces
        .ReviewBuilderAdapter;
import com.chdryra.android.reviewer.ApplicationSingletons.ApplicationInstance;
import com.chdryra.android.reviewer.View.Launcher.LaunchableUi;
import com.chdryra.android.reviewer.View.Launcher.LauncherUi;
import com.chdryra.android.reviewer.View.ReviewViewModel.ReviewBuilding.Implementation.BuildScreen;
import com.chdryra.android.reviewer.View.ReviewViewModel.ReviewBuilding.Factories.FactoryReviewEditor;
import com.chdryra.android.reviewer.View.ReviewViewModel.Interfaces.ReviewView;

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
        ApplicationInstance app = ApplicationInstance.getInstance(this);
        ReviewBuilderAdapter<?> adapter = app.getReviewBuilderAdapter();
        if(adapter == null) adapter = app.newReviewBuilderAdapter();
        FactoryReviewEditor editorFactory = new FactoryReviewEditor();
        mBuildScreen = new BuildScreen(this, adapter, editorFactory,
                app.getConfigDataUi(), app.getLaunchableFactory());
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
