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
import com.chdryra.android.startouch.DataDefinitions.Data.Implementation.DatumSubject;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataSubject;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.IdableList;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.ReviewId;

/**
 * Created by: Rizwan Choudrey
 * On: 08/07/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class CanonicalSubjectMode extends CanonicalStringMaker<DataSubject> {
    @NonNull
    @Override
    protected ItemGetter<DataSubject, String> getStringGetter() {
        return new ItemGetter<DataSubject, String>() {
            @Override
            public String getItem(DataSubject datum) {
                return datum.getSubject().toLowerCase();
            }
        };
    }

    @Override
    public DataSubject getCanonical(IdableList<? extends DataSubject> data) {
        ReviewId id = data.getReviewId();
        if (data.size() == 0) return FactoryNullData.nullSubject(id);

        return new DatumSubject(id, getModeString(data));
    }
}
