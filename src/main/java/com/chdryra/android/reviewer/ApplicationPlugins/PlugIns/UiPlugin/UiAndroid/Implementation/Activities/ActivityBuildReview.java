/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation
        .Activities;

import android.content.Intent;
import android.os.Bundle;

import com.chdryra.android.mygenerallibrary.OtherUtils.TagKeyGenerator;
import com.chdryra.android.reviewer.Application.Implementation.AppInstanceAndroid;
import com.chdryra.android.reviewer.Application.Interfaces.ApplicationInstance;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.Review;
import com.chdryra.android.reviewer.Presenter.Interfaces.View.ReviewView;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Interfaces.BuildScreenLauncher;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Implementation.PresenterReviewBuild;
import com.chdryra.android.reviewer.View.LauncherModel.Interfaces.LauncherUi;

/**
 * Created by: Rizwan Choudrey
 * On: 18/10/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ActivityBuildReview extends ActivityReviewView {
    private static final String TAG = TagKeyGenerator.getTag(ActivityBuildReview.class);
    public static final String TEMPLATE_ID = BuildScreenLauncher.TEMPLATE_ID;

    private PresenterReviewBuild mPresenter;

    @Override
    protected ReviewView createReviewView() {
        ApplicationInstance app = AppInstanceAndroid.getInstance(this);

        PresenterReviewBuild.Builder builder = new PresenterReviewBuild.Builder();

        setTemplateReviewIfAvailable(app, builder);

        mPresenter = builder.build(app);

        return mPresenter.getEditor();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        mPresenter.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public String getLaunchTag() {
        return TAG;
    }

    @Override
    public void launch(LauncherUi launcher) {
        launcher.launch(getClass(), TEMPLATE_ID);
    }

    private void setTemplateReviewIfAvailable(ApplicationInstance app,
                                              PresenterReviewBuild.Builder builder) {
        Bundle args = getIntent().getBundleExtra(TEMPLATE_ID);
        String id = args != null ? args.getString(TEMPLATE_ID) : null;
        if (id != null) {
            Review template = app.getReviewBuilder().unpackTemplate(args);
            if (template != null) builder.setTemplateReview(template);
        }
    }
}
