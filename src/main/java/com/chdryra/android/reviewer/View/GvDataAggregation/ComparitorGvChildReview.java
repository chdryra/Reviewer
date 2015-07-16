/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 3 July, 2015
 */

package com.chdryra.android.reviewer.View.GvDataAggregation;

import com.chdryra.android.reviewer.View.GvDataModel.GvChildReviewList;

/**
 * Created by: Rizwan Choudrey
 * On: 03/07/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ComparitorGvChildReview extends ComparitorStringable<GvChildReviewList.GvChildReview> {
    public ComparitorGvChildReview() {
        super(new DataGetter<GvChildReviewList.GvChildReview, String>() {
            @Override
            public String getData(GvChildReviewList.GvChildReview datum) {
                return datum.getSubject().toLowerCase();
            }
        });
    }
}
