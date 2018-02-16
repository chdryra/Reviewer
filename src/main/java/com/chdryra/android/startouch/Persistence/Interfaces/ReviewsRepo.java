/*
 * Copyright (c) Rizwan Choudrey 2018 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.Persistence.Interfaces;

import com.chdryra.android.startouch.Authentication.Interfaces.UserSession;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.AuthorId;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.ReviewId;

/**
 * Created by: Rizwan Choudrey
 * On: 30/09/2015
 * Email: rizwan.choudrey@gmail.com
 */
public interface ReviewsRepo extends ReviewsRepoReadable {
    ReviewsRepoReadable getReviewsByAuthor(AuthorId authorId);

    ReviewCollection getCollectionForAuthor(AuthorId authorId, String name);

    ReviewsRepoWriteable getRepoForUser(UserSession session);

    @Override
    void getReference(ReviewId reviewId, RepoCallback callback);

    @Override
    void getReview(ReviewId reviewId, RepoCallback callback);
}
