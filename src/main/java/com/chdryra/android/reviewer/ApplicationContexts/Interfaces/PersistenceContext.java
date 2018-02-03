/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationContexts.Interfaces;

import com.chdryra.android.reviewer.Authentication.Interfaces.AccountsManager;
import com.chdryra.android.reviewer.Persistence.Factories.FactoryReviewsRepo;
import com.chdryra.android.reviewer.Persistence.Interfaces.AuthorsRepo;
import com.chdryra.android.reviewer.Persistence.Interfaces.NodeRepo;
import com.chdryra.android.reviewer.Persistence.Interfaces.ReviewsRepoMutable;

/**
 * Created by: Rizwan Choudrey
 * On: 04/12/2015
 * Email: rizwan.choudrey@gmail.com
 */
public interface PersistenceContext {
    NodeRepo getReviewsRepo();

    AuthorsRepo getAuthorsRepo();

    ReviewsRepoMutable getLocalRepo();

    AccountsManager getAccountsManager();

    FactoryReviewsRepo getRepoFactory();
}
