/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.ReviewLauncher;

import android.os.Bundle;

import com.chdryra.android.mygenerallibrary.OtherUtils.RequestCodeGenerator;
import com.chdryra.android.reviewer.DataDefinitions.Data.Implementation.AuthorIdParcelable;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.AuthorId;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.ReviewId;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewNode;
import com.chdryra.android.reviewer.Persistence.Implementation.RepositoryResult;
import com.chdryra.android.reviewer.Persistence.Interfaces.ReviewsSource;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Implementation.ParcelablePacker;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Factories.FactoryReviewView;
import com.chdryra.android.reviewer.View.LauncherModel.Factories.UiLauncher;
import com.chdryra.android.reviewer.View.LauncherModel.Interfaces.LaunchableConfig;

/**
 * Created by: Rizwan Choudrey
 * On: 12/09/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class ReviewLauncherImpl implements ReviewLauncher {
    private ReviewsSource mReviewsSource;
    private FactoryReviewView mFactoryReviewView;
    private UiLauncher mLauncher;
    private LaunchableConfig mAuthorsConfig;

    public ReviewLauncherImpl(ReviewsSource reviewsSource,
                              UiLauncher launcher, FactoryReviewView factoryReviewView,
                              LaunchableConfig authorsConfig) {
        mReviewsSource = reviewsSource;
        mFactoryReviewView = factoryReviewView;
        mLauncher = launcher;
        mAuthorsConfig = authorsConfig;
    }

    @Override
    public void launchReview(ReviewId reviewId) {
        mReviewsSource.asMetaReview(reviewId, new ReviewsSource.ReviewsSourceCallback() {
            @Override
            public void onMetaReviewCallback(RepositoryResult result) {
                ReviewNode node = result.getReviewNode();
                if (!result.isError() && node != null) launchReview(node);
            }
        });
    }

    @Override
    public void launchReviews(AuthorId authorId) {
        AuthorIdParcelable id = new AuthorIdParcelable(authorId.toString());
        ParcelablePacker<AuthorIdParcelable> packer = new ParcelablePacker<>();
        Bundle args = new Bundle();
        packer.packItem(ParcelablePacker.CurrentNewDatum.CURRENT, id, args);
        mLauncher.launch(mAuthorsConfig, mAuthorsConfig.getRequestCode(), args);
    }

    private void launchReview(ReviewNode reviewNode) {
        int requestCode = RequestCodeGenerator.getCode(reviewNode.getSubject().getSubject());
        mLauncher.launch(mFactoryReviewView.newReviewsListView(reviewNode), requestCode);
    }
}
