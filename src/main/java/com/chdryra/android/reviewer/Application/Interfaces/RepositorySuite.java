/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Application.Interfaces;

import com.chdryra.android.reviewer.Authentication.Interfaces.ProfileSocial;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.ReviewId;
import com.chdryra.android.reviewer.NetworkServices.ReviewDeleting.Interfaces.ReviewDeleter;
import com.chdryra.android.reviewer.NetworkServices.ReviewPublishing.Interfaces.ReviewPublisher;
import com.chdryra.android.reviewer.Persistence.Interfaces.AuthorsRepo;
import com.chdryra.android.reviewer.Persistence.Interfaces.ReviewsRepoReadable;
import com.chdryra.android.reviewer.Persistence.Interfaces.ReviewsNodeRepo;

/**
 * Created by: Rizwan Choudrey
 * On: 03/10/2016
 * Email: rizwan.choudrey@gmail.com
 */

public interface RepositorySuite {
    AuthorsRepo getAuthors();

    ReviewsNodeRepo getReviews();

    ReviewsRepoReadable getFeed(ProfileSocial profile);

    ReviewDeleter newReviewDeleter(ReviewId id);

    ReviewPublisher getReviewPublisher();
}
