/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationContexts.Implementation;

import com.chdryra.android.reviewer.ApplicationContexts.Interfaces.PersistenceContext;
import com.chdryra.android.reviewer.Authentication.Interfaces.AccountsManager;
import com.chdryra.android.reviewer.Persistence.Factories.FactoryReviewsRepository;
import com.chdryra.android.reviewer.Persistence.Interfaces.AuthorsRepository;
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
    private AuthorsRepository mAuthorsRepo;
    private AccountsManager mAccountsManager;
    private ReviewsSource mReviewsSource;
    private FactoryReviewsRepository mRepoFactory;

    protected void setLocalRepository(LocalRepository localRepo) {
        mLocalRepo = localRepo;
    }

    protected void setBackendRepository(ReviewsRepository backendRepo) {
        mBackendRepo = backendRepo;
    }

    protected void setAuthorsRepository(AuthorsRepository authorsRepo) {
        mAuthorsRepo = authorsRepo;
    }

    protected void setAccountsManager(AccountsManager accountsManager) {
        mAccountsManager = accountsManager;
    }

    protected void setReviewsSource(ReviewsSource reviewsSource) {
        mReviewsSource = reviewsSource;
    }

    protected void setReposFactory(FactoryReviewsRepository repoFactory) {
        mRepoFactory = repoFactory;
    }

    @Override
    public ReviewsSource getReviewsRepository() {
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
    public AuthorsRepository getAuthorsRepository() {
        return mAuthorsRepo;
    }

    @Override
    public AccountsManager getAccountsManager() {
        return mAccountsManager;
    }

    @Override
    public FactoryReviewsRepository getRepoFactory() {
        return mRepoFactory;
    }
}
