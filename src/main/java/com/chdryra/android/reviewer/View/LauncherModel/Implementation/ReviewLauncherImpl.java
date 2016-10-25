/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.View.LauncherModel.Implementation;

import com.chdryra.android.mygenerallibrary.OtherUtils.RequestCodeGenerator;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.AuthorId;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.ReviewId;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewNode;
import com.chdryra.android.reviewer.Persistence.Implementation.RepositoryResult;
import com.chdryra.android.reviewer.Persistence.Interfaces.ReviewsSource;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Factories.FactoryReviewView;
import com.chdryra.android.reviewer.View.LauncherModel.Interfaces.ReviewLauncher;
import com.chdryra.android.reviewer.View.LauncherModel.Interfaces.UiLauncher;

/**
 * Created by: Rizwan Choudrey
 * On: 12/09/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class ReviewLauncherImpl implements ReviewLauncher {
    private AuthorId mSessionAuthor;
    private final ReviewsSource mReviewsSource;
    private final FactoryReviewView mFactoryReviewView;
    private final UiLauncher mLauncher;
    private final ReviewUiLauncher mFormattedLauncher;

    public ReviewLauncherImpl(ReviewsSource reviewsSource,
                              UiLauncher launcher,
                              ReviewUiLauncher formattedLauncher,
                              FactoryReviewView factoryReviewView) {
        mReviewsSource = reviewsSource;
        mFactoryReviewView = factoryReviewView;
        mLauncher = launcher;
        mFormattedLauncher = formattedLauncher;
    }

    @Override
    public void setSessionAuthor(AuthorId authorId) {
        mSessionAuthor = authorId;
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
    public void launchReviewFormatted(ReviewId reviewId) {
        mFormattedLauncher.launch(reviewId);
    }

    @Override
    public void launchReviews(AuthorId authorId) {
        launchReview(mReviewsSource.asMetaReview(authorId));
    }

    private void launchReview(ReviewNode reviewNode) {
        boolean menu = !reviewNode.getAuthorId().toString().equals(mSessionAuthor.toString());
        int requestCode = RequestCodeGenerator.getCode(reviewNode.getSubject().getSubject());
        UiLauncherArgs args = new UiLauncherArgs(requestCode);
        mLauncher.launch(mFactoryReviewView.newReviewsListView(reviewNode, menu), args);
    }
}
