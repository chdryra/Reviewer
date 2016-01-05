/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 8 July, 2015
 */

package com.chdryra.android.reviewer.DataAlgorithms.DataAggregation.Implementation;

import android.support.annotation.NonNull;

import com.chdryra.android.reviewer.DataAlgorithms.DataAggregation.Interfaces.CanonicalDatumMaker;
import com.chdryra.android.reviewer.DataAlgorithms.DataAggregation.Interfaces.ItemGetter;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.DatumCriterion;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataCriterion;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.IdableList;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;

/**
 * Created by: Rizwan Choudrey
 * On: 08/07/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class CanonicalCriterionAverage implements CanonicalDatumMaker<DataCriterion> {
    @Override
    public DataCriterion getCanonical(IdableList<? extends DataCriterion> data) {
        ReviewId id = data.getReviewId();
        if (data.size() == 0) return new DatumCriterion(id, "", 0f);

        return new DatumCriterion(id, getSubject(data), getAverage(data));
    }

    private String getSubject(IdableList<? extends DataCriterion> data) {
        ItemCounter<DataCriterion, String> counter = getSubjectCounter();
        counter.performCount(data);
        String maxSubject = counter.getModeItem();
        int nonMax = counter.getNonModeCount();
        if (nonMax > 0) maxSubject += " + " + String.valueOf(nonMax);
        return maxSubject;
    }

    private float getAverage(IdableList<? extends DataCriterion> data) {
        float average = 0f;
        for (DataCriterion child : data) {
            average += child.getRating() / (float) data.size();
        }
        return average;
    }

    @NonNull
    private ItemCounter<DataCriterion, String> getSubjectCounter() {
        return new ItemCounter<>(new ItemGetter<DataCriterion, String>() {
                @Override
                public String getItem(DataCriterion datum) {
                    return datum.getSubject();
                }
        });
    }
}
