/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.ApplicationPlugins.PlugIns.DataAggregatorsPlugin
        .DataAggregationDefault.Implementation;


import android.support.annotation.NonNull;

import com.chdryra.android.corelibrary.Aggregation.ItemGetter;
import com.chdryra.android.startouch.DataDefinitions.Data.Factories.FactoryNullData;
import com.chdryra.android.startouch.DataDefinitions.Data.Implementation.DatumCriterion;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataCriterion;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.IdableList;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.ReviewId;

/**
 * Created by: Rizwan Choudrey
 * On: 08/07/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class CanonicalCriterionAverage extends CanonicalStringMaker<DataCriterion> {
    @NonNull
    @Override
    protected ItemGetter<DataCriterion, String> getStringGetter() {
        return new ItemGetter<DataCriterion, String>() {
            @Override
            public String getItem(DataCriterion datum) {
                return datum.getSubject();
            }
        };
    }

    @Override
    public DataCriterion getCanonical(IdableList<? extends DataCriterion> data) {
        ReviewId id = data.getReviewId();
        if (data.size() == 0) return FactoryNullData.nullCriterion(id);

        return new DatumCriterion(id, getModeString(data), getAverageRating(data));
    }

    private float getAverageRating(IdableList<? extends DataCriterion> data) {
        float average = 0f;
        for (DataCriterion child : data) {
            average += child.getRating() / (float) data.size();
        }
        return average;
    }
}
