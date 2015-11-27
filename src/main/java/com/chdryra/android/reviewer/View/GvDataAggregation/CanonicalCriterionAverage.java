/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 8 July, 2015
 */

package com.chdryra.android.reviewer.View.GvDataAggregation;

import com.chdryra.android.reviewer.View.Implementation.GvDataModel.Implementation.Data.GvCriterion;
import com.chdryra.android.reviewer.View.Implementation.GvDataModel.Interfaces.GvDataList;

/**
 * Created by: Rizwan Choudrey
 * On: 08/07/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class CanonicalCriterionAverage implements CanonicalDatumMaker<GvCriterion> {
    //Overridden
    @Override
    public GvCriterion getCanonical(GvDataList<GvCriterion> data) {
        if (data.size() == 0)
            return new GvCriterion(data.getGvReviewId(), "", 0f);

        DatumCounter<GvCriterion, String> counter = new DatumCounter<>(data,
                new DataGetter<GvCriterion, String>() {
                    //Overridden
                    @Override
                    public String getData(GvCriterion datum) {
                        return datum.getSubject();
                    }
                });

        String maxSubject = counter.getMaxItem();
        int nonMax = counter.getNonMaxCount();
        if (nonMax > 0) maxSubject += " + " + String.valueOf(nonMax);
        float average = 0f;
        for (GvCriterion child : data) {
            average += child.getRating() / (float) data.size();
        }
        return new GvCriterion(data.getGvReviewId(), maxSubject, average);
    }
}
