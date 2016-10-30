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
import com.chdryra.android.reviewer.Presenter.Interfaces.View.ReviewView;
import com.chdryra.android.reviewer.Presenter.Interfaces.View.ReviewViewAdapter;
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
    private final FactoryReviewView mViewFactory;
    private final UiLauncher mLauncher;
    private final ReviewUiLauncher mFormattedLauncher;

    public ReviewLauncherImpl(ReviewsSource reviewsSource,
                              UiLauncher launcher,
                              ReviewUiLauncher formattedLauncher,
                              FactoryReviewView viewFactory) {
        mReviewsSource = reviewsSource;
        mViewFactory = viewFactory;
        mLauncher = launcher;
        mFormattedLauncher = formattedLauncher;
    }

    @Override
    public void setSessionAuthor(AuthorId authorId) {
        mSessionAuthor = authorId;
    }

    @Override
    public void launchAsList(ReviewId reviewId) {
        mReviewsSource.asMetaReview(reviewId, new ReviewsSource.ReviewsSourceCallback() {
            @Override
            public void onMetaReviewCallback(RepositoryResult result) {
                ReviewNode node = result.getReviewNode();
                if (!result.isError() && node != null) {
                    launchView(newListView(node), getRequestCode(node));
                }
            }
        });
    }

    @Override
    public void launchSummary(final ReviewId reviewId) {
        mReviewsSource.asMetaReview(reviewId, new ReviewsSource.ReviewsSourceCallback() {
            @Override
            public void onMetaReviewCallback(RepositoryResult result) {
                ReviewNode node = result.getReviewNode();
                if (!result.isError() && node != null) {
                    ReviewNode review = node.getChildren().getItem(0);
                    ReviewViewAdapter<?> adapter = mViewFactory.getAdapterFactory()
                            .newSummaryAdapter(review);
                    launchView(mViewFactory.newDefaultView(adapter), getRequestCode(review));
                }
            }
        });
    }

    @Override
    public void launchFormatted(ReviewId reviewId) {
        mFormattedLauncher.launch(reviewId);
    }

    @Override
    public void launchReviewsList(AuthorId authorId) {
        ReviewNode node = mReviewsSource.asMetaReview(authorId);
        launchView(newListView(node), getRequestCode(node));
    }

    private int getRequestCode(ReviewNode node) {
        return RequestCodeGenerator.getCode(node.getSubject().getSubject());
    }

    private void launchView(ReviewView<?> ui, int requestCode) {
        mLauncher.launch(ui, new UiLauncherArgs(requestCode));
    }

    private ReviewView<?> newListView(ReviewNode reviewNode) {
        boolean menu = !reviewNode.getAuthorId().toString().equals(mSessionAuthor.toString());
        return mViewFactory.newReviewsListView(reviewNode, menu);
    }
}
