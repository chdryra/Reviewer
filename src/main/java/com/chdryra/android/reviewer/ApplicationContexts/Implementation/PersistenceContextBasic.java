/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationContexts.Implementation;

import com.chdryra.android.reviewer.ApplicationContexts.Interfaces.PersistenceContext;
import com.chdryra.android.reviewer.Model.ReviewsRepositoryModel.Interfaces.ReviewsFeedMutable;
import com.chdryra.android.reviewer.Model.ReviewsRepositoryModel.Interfaces.ReviewsRepositoryMutable;
import com.chdryra.android.reviewer.Model.ReviewsRepositoryModel.Interfaces.ReviewsSource;

/**
 * Created by: Rizwan Choudrey
 * On: 04/12/2015
 * Email: rizwan.choudrey@gmail.com
 */
public abstract class PersistenceContextBasic implements PersistenceContext {
    private ReviewsFeedMutable mAuthorsFeed;
    private ReviewsRepositoryMutable mLocalRepo;
    private ReviewsRepositoryMutable mBackendRepo;
    private ReviewsSource mReviewsSource;

    public void setAuthorsFeed(ReviewsFeedMutable authorsFeed) {
        mAuthorsFeed = authorsFeed;
    }

    public void setLocalRepository(ReviewsRepositoryMutable localRepo) {
        mLocalRepo = localRepo;
    }

    public void setBackendRepository(ReviewsRepositoryMutable backendRepo) {
        mBackendRepo = backendRepo;
    }

    public void setReviewsSource(ReviewsSource reviewsSource) {
        mReviewsSource = reviewsSource;
    }

    @Override
    public ReviewsFeedMutable getAuthorsFeed() {
        return mAuthorsFeed;
    }

    @Override
    public ReviewsSource getReviewsSource() {
        return mReviewsSource;
    }

    @Override
    public ReviewsRepositoryMutable getLocalRepository() {
        return mLocalRepo;
    }

    @Override
    public ReviewsRepositoryMutable getBackendRepository() {
        return mBackendRepo;
    }
}
