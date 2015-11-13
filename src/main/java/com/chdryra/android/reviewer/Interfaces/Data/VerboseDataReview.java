package com.chdryra.android.reviewer.Interfaces.Data;

/**
 * Created by: Rizwan Choudrey
 * On: 13/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public interface VerboseDataReview extends DataReview, VerboseData {
    @Override
    String getReviewId();

    @Override
    String getStringSummary();

    @Override
    boolean hasElements();

    @Override
    boolean isVerboseCollection();
}
