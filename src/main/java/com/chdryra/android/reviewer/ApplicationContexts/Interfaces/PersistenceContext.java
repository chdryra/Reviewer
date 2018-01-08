/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationContexts.Interfaces;

import com.chdryra.android.reviewer.Authentication.Interfaces.AccountsManager;
import com.chdryra.android.reviewer.Persistence.Factories.FactoryReviewsRepository;
import com.chdryra.android.reviewer.Persistence.Interfaces.AuthorsRepository;
import com.chdryra.android.reviewer.Persistence.Interfaces.LocalRepository;
import com.chdryra.android.reviewer.Persistence.Interfaces.ReviewsSource;

/**
 * Created by: Rizwan Choudrey
 * On: 04/12/2015
 * Email: rizwan.choudrey@gmail.com
 */
public interface PersistenceContext {
    LocalRepository getLocalRepo();

    ReviewsSource getReviewsRepo();

    AuthorsRepository getAuthorsRepo();

    AccountsManager getAccountsManager();

    FactoryReviewsRepository getRepoFactory();
}
