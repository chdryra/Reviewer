/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.BackendFirebase.Implementation;



import com.chdryra.android.reviewer.DataDefinitions.Implementation.DatumDateReview;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.DatumRating;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.DatumReviewId;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.DatumSubject;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewDataHolder;
import com.chdryra.android.reviewer.Model.Factories.FactoryReviews;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.Review;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewMaker;

/**
 * Created by: Rizwan Choudrey
 * On: 12/04/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class FirebaseReviewMaker implements ReviewMaker {
    private FactoryReviews mFactory;

    public FirebaseReviewMaker(FactoryReviews factory) {
        mFactory = factory;
    }

    public ReviewBasicInfo makePartialReview(String reviewId, String subject, Rating rating, long date) {
        DatumReviewId id = new DatumReviewId(reviewId);
        return new ReviewBasicInfo(id, new DatumSubject(id, subject),
                new DatumRating(id, (float)rating.getRating(), (int)rating.getRatingWeight()),
                new DatumDateReview(id, date)) ;
    }

    @Override
    public Review makeReview(ReviewDataHolder reviewData) {
        return mFactory.makeReview(reviewData);
    }
}
