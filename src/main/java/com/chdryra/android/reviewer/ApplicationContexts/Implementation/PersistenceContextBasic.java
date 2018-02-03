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
import com.chdryra.android.reviewer.Persistence.Interfaces.MutableRepository;
import com.chdryra.android.reviewer.Persistence.Interfaces.NodeRepository;

/**
 * Created by: Rizwan Choudrey
 * On: 04/12/2015
 * Email: rizwan.choudrey@gmail.com
 */
public abstract class PersistenceContextBasic implements PersistenceContext {
    private MutableRepository mLocalRepo;
    private AuthorsRepository mAuthorsRepo;
    private AccountsManager mAccountsManager;
    private NodeRepository mNodeRepository;
    private FactoryReviewsRepository mRepoFactory;

    protected void setLocalRepository(MutableRepository localRepo) {
        mLocalRepo = localRepo;
    }

    protected void setAuthorsRepository(AuthorsRepository authorsRepo) {
        mAuthorsRepo = authorsRepo;
    }

    protected void setAccountsManager(AccountsManager accountsManager) {
        mAccountsManager = accountsManager;
    }

    protected void setNodeRepository(NodeRepository nodeRepository) {
        mNodeRepository = nodeRepository;
    }

    protected void setReposFactory(FactoryReviewsRepository repoFactory) {
        mRepoFactory = repoFactory;
    }

    @Override
    public NodeRepository getReviewsRepo() {
        return mNodeRepository;
    }

    @Override
    public MutableRepository getLocalRepo() {
        return mLocalRepo;
    }

    @Override
    public AuthorsRepository getAuthorsRepo() {
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
