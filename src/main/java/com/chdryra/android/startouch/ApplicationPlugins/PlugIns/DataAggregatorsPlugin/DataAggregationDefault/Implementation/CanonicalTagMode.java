/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.ApplicationPlugins.PlugIns.DataAggregatorsPlugin.DataAggregationDefault.Implementation;


import android.support.annotation.NonNull;

import com.chdryra.android.startouch.DataDefinitions.Data.Factories.FactoryNullData;
import com.chdryra.android.startouch.DataDefinitions.Data.Implementation.DatumTag;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataTag;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.IdableList;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.ReviewId;
import com.chdryra.android.mygenerallibrary.Aggregation.ItemGetter;

/**
 * Created by: Rizwan Choudrey
 * On: 08/07/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class CanonicalTagMode extends CanonicalStringMaker<DataTag> {
    @Override
    public DataTag getCanonical(IdableList<? extends DataTag> data) {
        ReviewId id = data.getReviewId();
        if (data.size() == 0) return FactoryNullData.nullTag(id);
        return new DatumTag(id, getModeString(data));
    }

    @NonNull
    @Override
    protected ItemGetter<DataTag, String> getStringGetter() {
        return new ItemGetter<DataTag, String>() {
            @Override
            public String getItem(DataTag datum) {
                return datum.getTag();
            }
        };
    }
}
