/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.Activities;



import android.os.Bundle;

import com.chdryra.android.mygenerallibrary.OtherUtils.TagKeyGenerator;
import com.chdryra.android.reviewer.Application.AndroidApp.AndroidAppInstance;
import com.chdryra.android.reviewer.DataDefinitions.Data.Implementation.AuthorIdParcelable;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Implementation.ParcelablePacker;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.View.PresenterAuthorsReviews;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.View.PresenterReviewsList;

/**
 * UI Activity holding published reviews feed.
 */
public class ActivityAuthorsReviews extends ActivityReviewsList {
    private static final String TAG = TagKeyGenerator.getTag(ActivityAuthorsReviews.class);

    @Override
    protected PresenterReviewsList newPresenter() {
        ParcelablePacker<AuthorIdParcelable> packer = new ParcelablePacker<>();
        Bundle args = getIntent().getBundleExtra(getLaunchTag());
        AuthorIdParcelable bundledAuthor = args != null ?
                packer.unpack(ParcelablePacker.CurrentNewDatum.CURRENT, args) : null;
        if(bundledAuthor == null) throw new IllegalArgumentException("No author!");

        return new PresenterAuthorsReviews.Builder(AndroidAppInstance.getInstance(this)).build(bundledAuthor);
    }

    @Override
    public String getLaunchTag() {
        return TAG;
    }
}
