/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.Persistence.Implementation;

import com.chdryra.android.corelibrary.CacheUtils.QueueCache;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.ReviewId;
import com.chdryra.android.startouch.Model.ReviewsModel.Interfaces.Review;
import com.chdryra.android.startouch.Persistence.Interfaces.ReviewsCache;

/**
 * Created by: Rizwan Choudrey
 * On: 31/03/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class ReviewsCacheHybrid implements ReviewsCache {
    private final QueueCache<Review> mFastCache;
    private final QueueCache<Review> mSlowCache;

    public ReviewsCacheHybrid(QueueCache<Review> fastCache, QueueCache<Review> slowCache) {
        mFastCache = fastCache;
        mSlowCache = slowCache;
    }

    @Override
    public void add(Review review) {
        if (!review.isCacheable()) return;

        Review overflow = mFastCache.add(review.getReviewId().toString(), review);
        if (overflow != null) mSlowCache.add(overflow.getReviewId().toString(), overflow);
    }

    @Override
    public Review get(ReviewId id) {
        String reviewId = id.toString();
        if (mFastCache.containsId(reviewId)) {
            return mFastCache.get(reviewId);
        } else {
            return mSlowCache.get(reviewId);
        }
    }

    @Override
    public Review remove(ReviewId id) {
        String reviewId = id.toString();
        if (mFastCache.containsId(reviewId)) return mFastCache.remove(reviewId);
        return mSlowCache.remove(reviewId);
    }

    @Override
    public boolean contains(ReviewId id) {
        return mFastCache.containsId(id.toString()) || mSlowCache.containsId(id.toString());
    }
}
