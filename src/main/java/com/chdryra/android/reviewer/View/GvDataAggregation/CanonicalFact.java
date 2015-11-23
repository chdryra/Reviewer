/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 8 July, 2015
 */

package com.chdryra.android.reviewer.View.GvDataAggregation;

import com.chdryra.android.reviewer.View.GvDataModel.Implementation.GvDataList;
import com.chdryra.android.reviewer.View.GvDataModel.Implementation.GvFact;

/**
 * Created by: Rizwan Choudrey
 * On: 08/07/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class CanonicalFact implements CanonicalDatumMaker<GvFact> {
    //Overridden
    @Override
    public GvFact getCanonical(GvDataList<GvFact> data) {
        if (data.size() == 0) return new GvFact(data.getGvReviewId(), "", "");

        DatumCounter<GvFact, String> counter = new DatumCounter<>(data,
                new DataGetter<GvFact, String>() {
                    //Overridden
                    @Override
                    public String getData(GvFact datum) {
                        return datum.getLabel();
                    }
                });

        String maxLabel = counter.getMaxItem();
        int nonMax = counter.getNonMaxCount();
        if (nonMax > 0) maxLabel += " + " + String.valueOf(nonMax);

        counter = new DatumCounter<>(data,
                new DataGetter<GvFact, String>() {
//Overridden
                    @Override
                    public String getData(GvFact datum) {
                        return datum.getValue();
                    }
                });

        String maxValue = counter.getMaxItem();
        nonMax = counter.getNonMaxCount();
        if (nonMax > 0) maxValue = String.valueOf(nonMax + 1) + " values";

        return new GvFact(data.getGvReviewId(), maxLabel, maxValue);
    }
}
