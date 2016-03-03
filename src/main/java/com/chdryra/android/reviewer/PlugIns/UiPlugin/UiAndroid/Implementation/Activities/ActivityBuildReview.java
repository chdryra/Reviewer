/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.PlugIns.UiPlugin.UiAndroid.Implementation.Activities;

import android.content.Intent;
import android.os.Bundle;

import com.chdryra.android.reviewer.ApplicationSingletons.ApplicationInstance;
import com.chdryra.android.reviewer.Presenter.Interfaces.View.ReviewView;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Factories.FactoryBuildScreen;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Factories.FactoryReviewEditor;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Implementation.BuildScreen;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Interfaces.ReviewBuilderAdapter;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions
        .NewReviewListener;
import com.chdryra.android.reviewer.View.LauncherModel.Interfaces.LauncherUi;

/**
 * Created by: Rizwan Choudrey
 * On: 18/10/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ActivityBuildReview extends ActivityReviewView {
    private static final String TAG = "BuildScreen";
    private static final String TEMPLATE_ID = "TemplateId";
    private BuildScreen mBuildScreen;

    @Override
    protected ReviewView createReviewView() {
        ApplicationInstance app = ApplicationInstance.getInstance(this);
        mBuildScreen = newBuildScreen(app, getAdapter(app));
        return mBuildScreen.getEditor();
    }

    private ReviewBuilderAdapter<?> getAdapter(ApplicationInstance app) {
        ReviewBuilderAdapter<?> adapter = app.getReviewBuilderAdapter();
        if (adapter == null) {
            Bundle args = getIntent().getBundleExtra(TEMPLATE_ID);
            String id = args.getString(NewReviewListener.TEMPLATE_ID);
            adapter = id != null ? app.newReviewBuilderAdapter(id) : app.newReviewBuilderAdapter();
        }

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

    @Override
    public void launch(LauncherUi launcher) {
        launcher.launch(getClass(), TEMPLATE_ID);
    }
}
