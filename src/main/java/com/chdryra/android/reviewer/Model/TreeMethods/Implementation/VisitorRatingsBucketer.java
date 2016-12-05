/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Model.TreeMethods.Implementation;

import android.support.annotation.NonNull;

import com.chdryra.android.reviewer.Algorithms.DataSorting.BucketDistribution;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataRating;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewNode;
import com.chdryra.android.reviewer.Model.TreeMethods.Interfaces.NodeValueGetter;
import com.chdryra.android.reviewer.Model.TreeMethods.Interfaces.VisitorReviewNode;

/**
 * Created by: Rizwan Choudrey
 * On: 05/08/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class VisitorRatingsBucketer<T> implements VisitorReviewNode {
    private final BucketDistribution<T, DataRating> mDistribution;
    private final NodeValueGetter<T> mGetter;

    public VisitorRatingsBucketer(BucketDistribution<T, DataRating> distribution,
                                  NodeValueGetter<T> getter) {
        mDistribution = distribution;
        this.mGetter = getter;
    }

    @Override
    public void visit(@NonNull ReviewNode node) {
        T value = mGetter.getData(node);
        if(value != null) mDistribution.bucket(value, node.getRating());
    }
}
