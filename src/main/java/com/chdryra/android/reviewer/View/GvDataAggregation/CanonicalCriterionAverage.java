/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 8 July, 2015
 */

package com.chdryra.android.reviewer.View.GvDataAggregation;

import com.chdryra.android.reviewer.View.GvDataModel.GvCriterionList;
import com.chdryra.android.reviewer.View.GvDataModel.GvDataList;

/**
 * Created by: Rizwan Choudrey
 * On: 08/07/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class CanonicalCriterionAverage implements CanonicalDatumMaker<GvCriterionList.GvCriterion> {
    //Overridden
    @Override
    public GvCriterionList.GvCriterion getCanonical(GvDataList<GvCriterionList.GvCriterion> data) {
        if (data.size() == 0)
            return new GvCriterionList.GvCriterion(data.getGvReviewId(), "", 0f);

        DatumCounter<GvCriterionList.GvCriterion, String> counter = new DatumCounter<>(data,
                new DataGetter<GvCriterionList.GvCriterion, String>() {
                    //Overridden
                    @Override
                    public String getData(GvCriterionList.GvCriterion datum) {
                        return datum.getSubject();
                    }
                });

        String maxSubject = counter.getMaxItem();
        int nonMax = counter.getNonMaxCount();
        if (nonMax > 0) maxSubject += " + " + String.valueOf(nonMax);
        float average = 0f;
        for (GvCriterionList.GvCriterion child : data) {
            average += child.getRating() / (float) data.size();
        }
        return new GvCriterionList.GvCriterion(data.getGvReviewId(), maxSubject, average);
    }
}
