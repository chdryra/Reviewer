package com.chdryra.android.reviewer.DataDefinitions.Interfaces;

/**
 * Created by: Rizwan Choudrey
 * On: 13/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public interface VerboseDataReview extends DataReviewIdable, VerboseData {
    @Override
    String getReviewId();

    @Override
    String getStringSummary();

    @Override
    boolean hasElements();

    @Override
    boolean isVerboseCollection();
}