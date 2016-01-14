package com.chdryra.android.reviewer.PlugIns.UiPlugin.UiAndroid.Activities;

import android.content.Intent;

import com.chdryra.android.reviewer.ApplicationSingletons.ApplicationInstance;
import com.chdryra.android.reviewer.Presenter.Interfaces.View.ReviewView;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Factories.FactoryBuildScreen;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Factories.FactoryReviewEditor;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Implementation.BuildScreen;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Interfaces.ReviewBuilderAdapter;

/**
 * Created by: Rizwan Choudrey
 * On: 18/10/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ActivityBuildReview extends ActivityReviewView {
    private static final String TAG = "BuildScreen";
    private BuildScreen mBuildScreen;

    @Override
    protected ReviewView createReviewView() {
        ApplicationInstance app = ApplicationInstance.getInstance(this);
        mBuildScreen = newBuildScreen(app, getAdapter(app));
        return mBuildScreen.getEditor();
    }

    private ReviewBuilderAdapter<?> getAdapter(ApplicationInstance app) {
        ReviewBuilderAdapter<?> adapter = app.getReviewBuilderAdapter();
        if (adapter == null) adapter = app.newReviewBuilderAdapter();
        return adapter;
    }

    private BuildScreen newBuildScreen(ApplicationInstance app, ReviewBuilderAdapter<?> adapter) {
        FactoryReviewEditor editorFactory = new FactoryReviewEditor();
        FactoryBuildScreen builder = new FactoryBuildScreen();
        return builder.newScreen(this, app.getConfigDataUi(), adapter,
                app.getUiLauncher(), editorFactory);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        mBuildScreen.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public String getLaunchTag() {
        return TAG;
    }
}
