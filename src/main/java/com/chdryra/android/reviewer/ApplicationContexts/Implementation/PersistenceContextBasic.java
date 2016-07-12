/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationContexts.Implementation;

import com.chdryra.android.reviewer.ApplicationContexts.Interfaces.PersistenceContext;
import com.chdryra.android.reviewer.Authentication.Implementation.UsersManager;
import com.chdryra.android.reviewer.Persistence.Factories.FactoryReviewsRepository;
import com.chdryra.android.reviewer.Persistence.Interfaces.LocalRepository;
import com.chdryra.android.reviewer.Persistence.Interfaces.ReviewsRepository;
import com.chdryra.android.reviewer.Persistence.Interfaces.ReviewsSource;

/**
 * Created by: Rizwan Choudrey
 * On: 04/12/2015
 * Email: rizwan.choudrey@gmail.com
 */
public abstract class PersistenceContextBasic implements PersistenceContext {
    private LocalRepository mLocalRepo;
    private ReviewsRepository mBackendRepo;
    private UsersManager mUsersManager;
    private ReviewsSource mReviewsSource;
    private FactoryReviewsRepository mRepoFactory;

    public void setLocalRepository(LocalRepository localRepo) {
        mLocalRepo = localRepo;
    }

    public void setBackendRepository(ReviewsRepository backendRepo) {
        mBackendRepo = backendRepo;
    }

    public void setUsersManager(UsersManager usersManager) {
        mUsersManager = usersManager;
    }

    public void setReviewsSource(ReviewsSource reviewsSource) {
        mReviewsSource = reviewsSource;
    }

    public void setReposFactory(FactoryReviewsRepository repoFactory) {
        mRepoFactory = repoFactory;
    }

    @Override
    public ReviewsSource getReviewsSource() {
        return mReviewsSource;
    }

    @Override
    public LocalRepository getLocalRepository() {
        return mLocalRepo;
    }

    @Override
    public ReviewsRepository getBackendRepository() {
        return mBackendRepo;
    }

    @Override
    public UsersManager getUsersManager() {
        return mUsersManager;
    }

    @Override
    public FactoryReviewsRepository getRepoFactory() {
        return mRepoFactory;
    }
}
