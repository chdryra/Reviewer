/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.Persistence.Interfaces;

import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.ReviewId;

/**
 * Created by: Rizwan Choudrey
 * On: 30/09/2015
 * Email: rizwan.choudrey@gmail.com
 */
public interface ReviewsRepoReadable {
    void getReference(ReviewId reviewId, RepoCallback callback);

    void getReview(ReviewId reviewId, RepoCallback callback);

    void subscribe(ReviewsSubscriber subscriber);

    void unsubscribe(ReviewsSubscriber subscriber);
}