/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Persistence.Implementation;



import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation.Backend.Implementation.BackendReviewsRepo;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation.Backend.Implementation.Rating;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.DatumDateReview;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.DatumRating;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.DatumReviewId;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.DatumSubject;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewDataHolder;
import com.chdryra.android.reviewer.Model.Factories.FactoryReviews;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.Review;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewMaker;
import com.chdryra.android.reviewer.Persistence.Interfaces.ReviewsCache;

/**
 * Created by: Rizwan Choudrey
 * On: 12/04/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class LazyReviewMaker implements ReviewMaker {
    private final ReviewsCache mCache;
    private FactoryReviews mFactory;

    public LazyReviewMaker(FactoryReviews factory, ReviewsCache cache) {
        mFactory = factory;
        mCache = cache;
    }

    public Review makeLazyReview(String reviewId, String subject, Rating rating, long date, BackendReviewsRepo backend) {
        DatumReviewId id = new DatumReviewId(reviewId);
        return new ReviewLazy(id, new DatumSubject(id, subject),
                new DatumRating(id, (float)rating.getRating(), (int)rating.getRatingWeight()),
                new DatumDateReview(id, date), backend, mCache) ;
    }

    @Override
    public Review makeReview(ReviewDataHolder reviewData) {
        return mFactory.makeReview(reviewData);
    }
}
