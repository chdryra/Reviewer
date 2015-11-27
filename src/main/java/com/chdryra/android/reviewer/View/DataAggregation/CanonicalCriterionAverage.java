/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 8 July, 2015
 */

package com.chdryra.android.reviewer.View.DataAggregation;

import android.support.annotation.NonNull;

import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Interfaces.DataCriterion;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Interfaces.IdableList;
import com.chdryra.android.reviewer.View.DataAggregation.Interfaces.CanonicalDatumMaker;
import com.chdryra.android.reviewer.View.DataAggregation.Interfaces.DataGetter;

/**
 * Created by: Rizwan Choudrey
 * On: 08/07/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class CanonicalCriterionAverage implements CanonicalDatumMaker<DataCriterion> {
    //Overridden
    @Override
    public DataCriterion getCanonical(IdableList<DataCriterion> data) {
        String id = data.getReviewId();
        if (data.size() == 0) return new DatumCriterion(id, "", 0f);

        DatumCounter<DataCriterion, String> counter = getCounter(data);

        return new DatumCriterion(id, getModeSubject(counter), getAverage(data));
    }

    private float getAverage(IdableList<DataCriterion> data) {
        float average = 0f;
        for (DataCriterion child : data) {
            average += child.getRating() / (float) data.size();
        }
        return average;
    }

    private String getModeSubject(DatumCounter<DataCriterion, String> counter) {
        String maxSubject = counter.getMaxItem();
        int nonMax = counter.getNonMaxCount();
        if (nonMax > 0) maxSubject += " + " + String.valueOf(nonMax);
        return maxSubject;
    }

    @NonNull
    private DatumCounter<DataCriterion, String> getCounter(IdableList<DataCriterion> data) {
        return new DatumCounter<>(data, new DataGetter<DataCriterion, String>() {
                @Override
                public String getData(DataCriterion datum) {
                    return datum.getSubject();
                }
        });
    }

}
