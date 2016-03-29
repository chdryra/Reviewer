/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.View;

import android.support.annotation.Nullable;

import com.chdryra.android.reviewer.Model.Factories.FactoryReviews;
import com.chdryra.android.reviewer.Model.ReviewsModel.Implementation.ReviewTreeMutableAsync;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.Review;
import com.chdryra.android.reviewer.Model.ReviewsRepositoryModel.Implementation.RepositoryError;
import com.chdryra.android.reviewer.Model.ReviewsRepositoryModel.Interfaces.RepositoryCallback;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by: Rizwan Choudrey
 * On: 24/03/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class ReviewTreeRepoCallback extends ReviewTreeMutableAsync implements RepositoryCallback {
    private FactoryReviews mReviewsFactory;
    private String mTitle;

    public ReviewTreeRepoCallback(Collection<Review> initial, FactoryReviews reviewsFactory,
                                  String title) {
        super(reviewsFactory.createMetaReviewMutable(initial, title));
        mReviewsFactory = reviewsFactory;
        mTitle = title;
    }

    @Override
    public void onFetchedFromRepo(@Nullable Review review, RepositoryError error) {
        if(review != null && !error.isError()) {
            ArrayList<Review> reviews = new ArrayList<>();
            reviews.add(review);
            updateNode(reviews);
        }
    }

    @Override
    public void onCollectionFetchedFromRepo(Collection<Review> reviews, RepositoryError error) {
        if(!error.isError()) updateNode(reviews);
    }

    private void updateNode(Collection<Review> reviews) {
        updateNode(mReviewsFactory.createMetaReviewMutable(reviews, mTitle));
    }
}
