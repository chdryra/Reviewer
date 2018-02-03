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
import com.chdryra.android.reviewer.Persistence.Factories.FactoryReviewsRepo;
import com.chdryra.android.reviewer.Persistence.Interfaces.AuthorsRepo;
import com.chdryra.android.reviewer.Persistence.Interfaces.ReviewsArchiveMutable;
import com.chdryra.android.reviewer.Persistence.Interfaces.NodeRepo;

/**
 * Created by: Rizwan Choudrey
 * On: 04/12/2015
 * Email: rizwan.choudrey@gmail.com
 */
public abstract class PersistenceContextBasic implements PersistenceContext {
    private ReviewsArchiveMutable mLocalRepo;
    private AuthorsRepo mAuthorsRepo;
    private AccountsManager mAccountsManager;
    private NodeRepo mReviewsRepo;
    private FactoryReviewsRepo mRepoFactory;

    protected void setLocalRepo(ReviewsArchiveMutable localRepo) {
        mLocalRepo = localRepo;
    }

    protected void setAuthorsRepo(AuthorsRepo authorsRepo) {
        mAuthorsRepo = authorsRepo;
    }

    protected void setAccountsManager(AccountsManager accountsManager) {
        mAccountsManager = accountsManager;
    }

    protected void setReviewsRepo(NodeRepo reviewsRepo) {
        mReviewsRepo = reviewsRepo;
    }

    protected void setReposFactory(FactoryReviewsRepo repoFactory) {
        mRepoFactory = repoFactory;
    }

    @Override
    public NodeRepo getReviewsRepo() {
        return mReviewsRepo;
    }

    @Override
    public ReviewsArchiveMutable getLocalRepo() {
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
