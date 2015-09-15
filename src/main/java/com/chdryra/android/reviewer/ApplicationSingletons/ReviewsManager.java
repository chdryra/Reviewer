package com.chdryra.android.reviewer.ApplicationSingletons;

import android.content.Context;

import com.chdryra.android.reviewer.Model.ReviewStructure.ReviewNode;
import com.chdryra.android.reviewer.View.GvDataModel.GvData;
import com.chdryra.android.reviewer.View.GvDataModel.GvDataCollection;
import com.chdryra.android.reviewer.View.GvDataModel.GvReviewId;

/**
 * Created by: Rizwan Choudrey
 * On: 31/08/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ReviewsManager extends ApplicationSingleton {
    private static final String NAME = "ReviewManager";
    private static ReviewsManager sSingleton;

    public ReviewsManager(Context context) {
        super(context, NAME);
    }

    public static ReviewsManager get(Context c) {
        sSingleton = getSingleton(sSingleton, ReviewsManager.class, c);
        return sSingleton;
    }

    private static ReviewNode getReview(Context context, String reviewId) {
        return ReviewFeed.findInFeed(context, reviewId);
    }

    public static ReviewNode getReview(Context context, GvData datum) {
        GvReviewId id = datum.getReviewId();
        if (!datum.isCollection()) {
            return getReview(context, id.getId());
        } else {
            return ReviewMaker.createMetaReview(context, (GvDataCollection<? extends GvData>) datum,
                    datum.getStringSummary());
        }
    }
}
