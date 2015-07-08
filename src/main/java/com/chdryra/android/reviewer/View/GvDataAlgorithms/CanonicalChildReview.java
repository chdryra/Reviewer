/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 8 July, 2015
 */

package com.chdryra.android.reviewer.View.GvDataAlgorithms;

import com.chdryra.android.reviewer.View.GvDataModel.GvChildList;
import com.chdryra.android.reviewer.View.GvDataModel.GvDataList;

/**
 * Created by: Rizwan Choudrey
 * On: 08/07/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class CanonicalChildReview implements CanonicalDatumMaker<GvChildList.GvChildReview> {
    @Override
    public GvChildList.GvChildReview getCanonical(GvDataList<GvChildList.GvChildReview> data) {
        if (data.size() == 0) return new GvChildList.GvChildReview(data.getReviewId(), "", 0f);

        DatumCounter<GvChildList.GvChildReview, String> counter = new DatumCounter<>(data,
                new DataGetter<GvChildList.GvChildReview, String>() {
                    @Override
                    public String getData(GvChildList.GvChildReview datum) {
                        return datum.getSubject();
                    }
                });

        String maxSubject = counter.getMaxItem();
        int nonMax = counter.getNonMaxCount();
        if (nonMax > 0) maxSubject += " + " + String.valueOf(nonMax);
        float average = 0f;
        for (GvChildList.GvChildReview child : data) {
            average += child.getRating() / (float) data.size();
        }
        return new GvChildList.GvChildReview(data.getReviewId(), maxSubject, average);
    }
}
