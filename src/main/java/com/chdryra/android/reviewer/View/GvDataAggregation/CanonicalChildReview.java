/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 8 July, 2015
 */

package com.chdryra.android.reviewer.View.GvDataAggregation;

import com.chdryra.android.reviewer.View.GvDataModel.GvChildReviewList;
import com.chdryra.android.reviewer.View.GvDataModel.GvDataList;

/**
 * Created by: Rizwan Choudrey
 * On: 08/07/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class CanonicalChildReview implements CanonicalDatumMaker<GvChildReviewList.GvChildReview> {
    @Override
    public GvChildReviewList.GvChildReview getCanonical(GvDataList<GvChildReviewList
            .GvChildReview> data) {
        if (data.size() == 0)
            return new GvChildReviewList.GvChildReview(data.getReviewId(), "", 0f);

        DatumCounter<GvChildReviewList.GvChildReview, String> counter = new DatumCounter<>(data,
                new DataGetter<GvChildReviewList.GvChildReview, String>() {
                    @Override
                    public String getData(GvChildReviewList.GvChildReview datum) {
                        return datum.getSubject();
                    }
                });

        String maxSubject = counter.getMaxItem();
        int nonMax = counter.getNonMaxCount();
        if (nonMax > 0) maxSubject += " + " + String.valueOf(nonMax);
        float average = 0f;
        for (GvChildReviewList.GvChildReview child : data) {
            average += child.getRating() / (float) data.size();
        }
        return new GvChildReviewList.GvChildReview(data.getReviewId(), maxSubject, average);
    }
}
