/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.DataDefinitions.Interfaces;

/**
 * Created by: Rizwan Choudrey
 * On: 13/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public interface VerboseDataReview extends HasReviewId, VerboseData {
    @Override
    ReviewId getReviewId();

    @Override
    String getStringSummary();

    @Override
    boolean hasElements();

    @Override
    boolean isVerboseCollection();
}
