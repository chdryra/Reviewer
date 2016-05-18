/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Application;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.chdryra.android.mygenerallibrary.CacheUtils.ObjectHolder;
import com.chdryra.android.mygenerallibrary.OtherUtils.TagKeyGenerator;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.Review;

/**
 * Created by: Rizwan Choudrey
 * On: 18/10/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ReviewPacker {
    private static final String REVIEW_ID = TagKeyGenerator.getKey(ReviewPacker.class, "ReviewId");
    private final ObjectHolder mReviews;

    public ReviewPacker() {
        mReviews = new ObjectHolder();
    }

    public void packReview(Review review, Bundle args) {
        String id = review.getReviewId().toString();
        mReviews.addObject(id, review);
        args.putString(REVIEW_ID, id);
    }

    public
    @Nullable
    Review unpackReview(Bundle args) {
        return getReview(args.getString(REVIEW_ID));
    }

    @Nullable
    private Review getReview(@Nullable String id) {
        Review review = null;
        if (id != null && id.length() > 0) {
            review = (Review) mReviews.getObject(id);
            mReviews.removeObject(id);
        }
        return review;
    }
}