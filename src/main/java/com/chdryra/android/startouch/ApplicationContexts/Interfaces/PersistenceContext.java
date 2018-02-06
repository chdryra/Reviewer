/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.ApplicationContexts.Interfaces;

import com.chdryra.android.startouch.Authentication.Interfaces.AccountsManager;
import com.chdryra.android.startouch.Persistence.Factories.FactoryReviewsRepo;
import com.chdryra.android.startouch.Persistence.Interfaces.AuthorsRepo;
import com.chdryra.android.startouch.Persistence.Interfaces.ReviewsNodeRepo;
import com.chdryra.android.startouch.Persistence.Interfaces.ReviewsRepoWriteable;

/**
 * Created by: Rizwan Choudrey
 * On: 04/12/2015
 * Email: rizwan.choudrey@gmail.com
 */
public interface PersistenceContext {
    ReviewsNodeRepo getReviewsRepo();

    AuthorsRepo getAuthorsRepo();

    ReviewsRepoWriteable getLocalRepo();

    AccountsManager getAccountsManager();

    FactoryReviewsRepo getRepoFactory();
}
