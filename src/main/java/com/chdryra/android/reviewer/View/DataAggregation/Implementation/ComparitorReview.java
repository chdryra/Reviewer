/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 3 July, 2015
 */

package com.chdryra.android.reviewer.View.DataAggregation.Implementation;

import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Interfaces.DataReviewSummary;
import com.chdryra.android.reviewer.View.DataAggregation.Interfaces.DifferenceComparitor;

/**
 * Created by: Rizwan Choudrey
 * On: 03/07/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ComparitorReview implements DifferenceComparitor<DataReviewSummary, DifferenceBoolean> {
    @Override
    public DifferenceBoolean compare(DataReviewSummary lhs, DataReviewSummary rhs) {
        boolean sameId = lhs.getId().equals(rhs.getId());
        boolean diff = lhs.getAuthor() != rhs.getAuthor() || !lhs.getHeadline().equals(rhs
                .getHeadline()) || lhs.getPublishDate() != rhs.getPublishDate() || !lhs
                .getLocationString().equals(rhs.getLocationString()) || lhs.getRating() != rhs
                .getRating();
        if (sameId && diff) {
            throw new RuntimeException("ReviewId same but other data different!");
        }

        return new DifferenceBoolean(sameId);
    }
}
