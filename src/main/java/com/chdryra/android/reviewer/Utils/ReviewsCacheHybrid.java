/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Utils;

import android.support.annotation.Nullable;

import com.chdryra.android.mygenerallibrary.QueueCache;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.Review;

/**
 * Created by: Rizwan Choudrey
 * On: 31/03/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class ReviewsCacheHybrid implements ReviewsCache {
    private QueueCache<Review> mFastCache;
    private QueueCache<Review> mSlowCache;

    public ReviewsCacheHybrid(QueueCache<Review> fastCache, QueueCache<Review> slowCache) {
        mFastCache = fastCache;
        mSlowCache = slowCache;
    }

    @Override
    @Nullable
    public Review add(Review review) {
        Review overflow = mFastCache.add(review.getReviewId().toString(), review);
        if(overflow != null) {
            overflow = mSlowCache.add(overflow.getReviewId().toString(), overflow);
        }

        return overflow;
    }

    @Override
    public Review get(ReviewId id) {
        String reviewId = id.toString();
        if(mFastCache.containsId(reviewId)) {
            return mFastCache.get(reviewId);
        } else {
            return mSlowCache.get(reviewId);
        }
    }

    @Override
    public Review remove(ReviewId id) {
        String reviewId = id.toString();
        if(mFastCache.containsId(reviewId)) return mFastCache.remove(reviewId);
        return mSlowCache.remove(reviewId);
    }

    @Override
    public boolean contains(ReviewId id) {
        return mFastCache.containsId(id.toString()) || mSlowCache.containsId(id.toString());
    }
}
