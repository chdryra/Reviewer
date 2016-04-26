/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationContexts.Implementation;

import com.chdryra.android.reviewer.ApplicationContexts.Interfaces.PersistenceContext;
import com.chdryra.android.reviewer.Authentication.Interfaces.Authenticator;
import com.chdryra.android.reviewer.Persistence.Factories.FactoryReviewsFeed;
import com.chdryra.android.reviewer.Persistence.Interfaces.ReviewsRepositoryMutable;
import com.chdryra.android.reviewer.Persistence.Interfaces.ReviewsSource;

/**
 * Created by: Rizwan Choudrey
 * On: 04/12/2015
 * Email: rizwan.choudrey@gmail.com
 */
public abstract class PersistenceContextBasic implements PersistenceContext {
    private ReviewsRepositoryMutable mLocalRepo;
    private ReviewsRepositoryMutable mBackendRepo;
    private Authenticator mAuthenticator;
    private ReviewsSource mReviewsSource;
    private FactoryReviewsFeed mFeedFactory;

    public void setLocalRepository(ReviewsRepositoryMutable localRepo) {
        mLocalRepo = localRepo;
    }

    public void setBackendRepository(ReviewsRepositoryMutable backendRepo) {
        mBackendRepo = backendRepo;
    }

    public void setAuthenticator(Authenticator authenticator) {
        mAuthenticator = authenticator;
    }

    public void setReviewsSource(ReviewsSource reviewsSource) {
        mReviewsSource = reviewsSource;
    }

    public void setFeedFactory(FactoryReviewsFeed feedFactory) {
        mFeedFactory = feedFactory;
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

    @Override
    public Authenticator getBackendAuthenticator() {
        return mAuthenticator;
    }

    @Override
    public FactoryReviewsFeed getFeedFactory() {
        return mFeedFactory;
    }
}
