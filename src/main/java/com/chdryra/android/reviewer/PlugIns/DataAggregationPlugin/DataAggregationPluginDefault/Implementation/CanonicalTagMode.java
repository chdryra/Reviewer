/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 8 July, 2015
 */

package com.chdryra.android.reviewer.PlugIns.DataAggregationPlugin.DataAggregationPluginDefault.Implementation;


import android.support.annotation.NonNull;

import com.chdryra.android.reviewer.DataDefinitions.Factories.NullData;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.DatumTag;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataTag;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.IdableList;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
import com.chdryra.android.reviewer.PlugIns.DataAggregationPlugin.DataAggregationPluginDefault
        .Interfaces.ItemGetter;

/**
 * Created by: Rizwan Choudrey
 * On: 08/07/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class CanonicalTagMode extends CanonicalStringMaker<DataTag> {
    @Override
    public DataTag getCanonical(IdableList<? extends DataTag> data) {
        ReviewId id = data.getReviewId();
        if (data.size() == 0) return NullData.nullTag(id);
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
