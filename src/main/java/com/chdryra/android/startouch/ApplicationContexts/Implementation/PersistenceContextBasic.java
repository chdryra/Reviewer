/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.ApplicationContexts.Implementation;

import com.chdryra.android.startouch.ApplicationContexts.Interfaces.PersistenceContext;
import com.chdryra.android.startouch.Authentication.Interfaces.AccountsManager;
import com.chdryra.android.startouch.Persistence.Factories.FactoryReviewsRepo;
import com.chdryra.android.startouch.Persistence.Interfaces.AuthorsRepo;
import com.chdryra.android.startouch.Persistence.Interfaces.ReviewNodeRepo;
import com.chdryra.android.startouch.Persistence.Interfaces.ReviewsRepoWriteable;

/**
 * Created by: Rizwan Choudrey
 * On: 04/12/2015
 * Email: rizwan.choudrey@gmail.com
 */
public abstract class PersistenceContextBasic implements PersistenceContext {
    private ReviewsRepoWriteable mLocalRepo;
    private AuthorsRepo mAuthorsRepo;
    private AccountsManager mAccountsManager;
    private ReviewNodeRepo mReviewsRepo;
    private FactoryReviewsRepo mRepoFactory;

    protected void setReposFactory(FactoryReviewsRepo repoFactory) {
        mRepoFactory = repoFactory;
    }

    @Override
    public ReviewNodeRepo getReviewsRepo() {
        return mReviewsRepo;
    }

    protected void setReviewsRepo(ReviewNodeRepo reviewsRepo) {
        mReviewsRepo = reviewsRepo;
    }

    @Override
    public ReviewsRepoWriteable getLocalRepo() {
        return mLocalRepo;
    }

    protected void setLocalRepo(ReviewsRepoWriteable localRepo) {
        mLocalRepo = localRepo;
    }

    @Override
    public AuthorsRepo getAuthorsRepo() {
        return mAuthorsRepo;
    }

    protected void setAuthorsRepo(AuthorsRepo authorsRepo) {
        mAuthorsRepo = authorsRepo;
    }

    @Override
    public AccountsManager getAccountsManager() {
        return mAccountsManager;
    }

    protected void setAccountsManager(AccountsManager accountsManager) {
        mAccountsManager = accountsManager;
    }

    @Override
    public FactoryReviewsRepo getRepoFactory() {
        return mRepoFactory;
    }
}
