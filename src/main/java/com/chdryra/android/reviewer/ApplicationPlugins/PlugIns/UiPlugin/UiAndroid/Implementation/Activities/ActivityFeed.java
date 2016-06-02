/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.Activities;



import android.os.Bundle;
import android.support.annotation.Nullable;

import com.chdryra.android.mygenerallibrary.OtherUtils.TagKeyGenerator;
import com.chdryra.android.reviewer.Application.ApplicationInstance;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
import com.chdryra.android.reviewer.Presenter.Interfaces.View.ReviewView;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Implementation.ParcelablePacker;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.NewReviewListener;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvAuthor;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.View.PresenterFeed;
import com.chdryra.android.reviewer.View.LauncherModel.Interfaces.LauncherUi;

/**
 * UI Activity holding published reviews feed.
 */
public class ActivityFeed extends ActivityReviewView implements NewReviewListener{
    private static final String TAG = TagKeyGenerator.getTag(ActivityFeed.class);
    private static final String AUTHOR
            = TagKeyGenerator.getKey(ActivityFeed.class, "Author");

    private ApplicationInstance mApp;
    private PresenterFeed mPresenter;

    @Override
    protected ReviewView createReviewView() {
        mApp = ApplicationInstance.getInstance(this);
        GvAuthor bundledAuthor = getBundledAuthor();
        if(bundledAuthor == null) throw new IllegalArgumentException("No author!");
        mPresenter = new PresenterFeed.Builder(mApp).build(bundledAuthor);

        return mPresenter.getView();
    }


    @Override
    public String getLaunchTag() {
        return TAG;
    }

    @Override
    public void launch(LauncherUi launcher) {
        launcher.launch(getClass(), AUTHOR);
    }

    @Nullable
    private GvAuthor getBundledAuthor() {
        ParcelablePacker<GvAuthor> packer = new ParcelablePacker<>();
        Bundle args = getIntent().getBundleExtra(AUTHOR);
        return args != null ?
                packer.unpack(ParcelablePacker.CurrentNewDatum.CURRENT, args) : new GvAuthor();
    }

    @Override
    protected void onResume() {
        mApp.discardReviewBuilderAdapter();
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        mPresenter.detach();
        super.onDestroy();
    }

    @Override
    public void onNewReviewUsingTemplate(ReviewId template) {
        mPresenter.onNewReviewUsingTemplate(template);
    }
}
