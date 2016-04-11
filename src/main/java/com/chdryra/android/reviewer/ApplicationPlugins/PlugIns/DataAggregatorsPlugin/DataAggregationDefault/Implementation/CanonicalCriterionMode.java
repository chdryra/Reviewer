/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.DataAggregatorsPlugin.DataAggregationDefault.Implementation;


import android.support.annotation.NonNull;

import com.chdryra.android.reviewer.DataDefinitions.Factories.FactoryNullData;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.DatumCriterion;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataCriterion;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.IdableList;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.DataAggregatorsPlugin.DataAggregationDefault
        .Interfaces.ItemGetter;

/**
 * Created by: Rizwan Choudrey
 * On: 29/09/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class CanonicalCriterionMode extends CanonicalStringMaker<DataCriterion> {

    @Override
    public DataCriterion getCanonical(IdableList<? extends DataCriterion> data) {
        ReviewId id = data.getReviewId();
        if (data.size() == 0) return FactoryNullData.nullCriterion(id);

        return new DatumCriterion(id, getModeString(data), getRating(data));
    }

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

    @NonNull
    private ItemCounter<DataCriterion, Float> getRatingCounter() {
        return new ItemCounter<>(new ItemGetter<DataCriterion, Float>() {
            @Override
            public Float getItem(DataCriterion datum) {
                return datum.getRating();
            }
        });
    }

    private float getRating(IdableList<? extends DataCriterion> data) {
        ItemCounter<DataCriterion, Float> ratingCounter = getRatingCounter();
        ratingCounter.performCount(data);
        return ratingCounter.getModeItem();
    }
}
