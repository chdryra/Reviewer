/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation
        .Activities;

import android.content.Intent;

import com.chdryra.android.corelibrary.OtherUtils.TagKeyGenerator;
import com.chdryra.android.startouch.Application.Implementation.AppInstanceAndroid;
import com.chdryra.android.startouch.Presenter.Interfaces.View.ReviewView;
import com.chdryra.android.startouch.Presenter.ReviewBuilding.Implementation.PresenterReviewBuild;
import com.chdryra.android.startouch.View.LauncherModel.Implementation.ReviewPack;
import com.chdryra.android.startouch.View.LauncherModel.Interfaces.UiTypeLauncher;

/**
 * Created by: Rizwan Choudrey
 * On: 18/10/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ActivityBuildReview extends ActivityReviewView {
    private static final String TAG = TagKeyGenerator.getTag(ActivityBuildReview.class);

    private PresenterReviewBuild mPresenter;

    @Override
    protected ReviewView<?> createReviewView() {
        AppInstanceAndroid.setActivity(this);
        AppInstanceAndroid app = getApp();
        ReviewPack template = app.unpackReview(getIntent().getBundleExtra(getLaunchTag()));
        mPresenter = new PresenterReviewBuild.Builder().setReview(template).build(app);

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
    public void launch(UiTypeLauncher launcher) {
        launcher.launch(getClass(), getLaunchTag());
    }

    @Override
    public void onBackPressed() {
        mPresenter.onBackPressed();
    }
}
