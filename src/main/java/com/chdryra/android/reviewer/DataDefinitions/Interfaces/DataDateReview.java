package com.chdryra.android.reviewer.DataDefinitions.Interfaces;

/**
 * Created by: Rizwan Choudrey
 * On: 10/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public interface DataDateReview extends HasReviewId, DataDate {
    @Override
    long getTime();

    @Override
    ReviewId getReviewId();
}
