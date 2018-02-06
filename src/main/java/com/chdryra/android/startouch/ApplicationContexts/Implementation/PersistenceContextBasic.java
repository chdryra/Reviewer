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
import com.chdryra.android.startouch.Persistence.Interfaces.ReviewsRepoWriteable;
import com.chdryra.android.startouch.Persistence.Interfaces.ReviewsNodeRepo;

/**
 * Created by: Rizwan Choudrey
 * On: 04/12/2015
 * Email: rizwan.choudrey@gmail.com
 */
public abstract class PersistenceContextBasic implements PersistenceContext {
    private ReviewsRepoWriteable mLocalRepo;
    private AuthorsRepo mAuthorsRepo;
    private AccountsManager mAccountsManager;
    private ReviewsNodeRepo mReviewsRepo;
    private FactoryReviewsRepo mRepoFactory;

    protected void setLocalRepo(ReviewsRepoWriteable localRepo) {
        mLocalRepo = localRepo;
    }

    protected void setAuthorsRepo(AuthorsRepo authorsRepo) {
        mAuthorsRepo = authorsRepo;
    }

    protected void setAccountsManager(AccountsManager accountsManager) {
        mAccountsManager = accountsManager;
    }

    protected void setReviewsRepo(ReviewsNodeRepo reviewsRepo) {
        mReviewsRepo = reviewsRepo;
    }

    protected void setReposFactory(FactoryReviewsRepo repoFactory) {
        mRepoFactory = repoFactory;
    }

    @Override
    public ReviewsNodeRepo getReviewsRepo() {
        return mReviewsRepo;
    }

    @Override
    public ReviewsRepoWriteable getLocalRepo() {
        return mLocalRepo;
    }

    @Override
    public AuthorsRepo getAuthorsRepo() {
        return mAuthorsRepo;
    }

    @Override
    public AccountsManager getAccountsManager() {
        return mAccountsManager;
    }

    @Override
    public FactoryReviewsRepo getRepoFactory() {
        return mRepoFactory;
    }
}
