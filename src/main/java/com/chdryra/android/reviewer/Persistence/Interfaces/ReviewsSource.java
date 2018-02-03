/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Persistence.Interfaces;

import com.chdryra.android.reviewer.Authentication.Interfaces.UserSession;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.AuthorId;

/**
 * Created by: Rizwan Choudrey
 * On: 30/09/2015
 * Email: rizwan.choudrey@gmail.com
 */
public interface ReviewsSource extends ReviewsRepository {
    ReviewsRepository getReviewsByAuthor(AuthorId authorId);

    ReviewCollection getCollectionForAuthor(AuthorId authorId, String name);

    MutableRepository getMutableRepository(UserSession session);
}
