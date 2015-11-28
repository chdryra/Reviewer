/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 8 July, 2015
 */

package com.chdryra.android.reviewer.View.DataAggregation.Implementation;

import android.support.annotation.NonNull;

import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Implementation.DatumCounter;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Implementation.DatumTag;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Interfaces.DataTag;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Interfaces.IdableList;
import com.chdryra.android.reviewer.View.DataAggregation.Interfaces.CanonicalDatumMaker;
import com.chdryra.android.reviewer.View.DataAggregation.Interfaces.DataGetter;

/**
 * Created by: Rizwan Choudrey
 * On: 08/07/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class CanonicalTagMode implements CanonicalDatumMaker<DataTag> {
    //Overridden
    @Override
    public DataTag getCanonical(IdableList<DataTag> data) {
        String id = data.getReviewId();
        if (data.size() == 0) return new DatumTag(id, "");
        return new DatumTag(id, getTagMode(getTagCounter(data)));
    }

    private String getTagMode(DatumCounter<DataTag, String> counter) {
        String maxTag = counter.getMaxItem();
        int nonMax = counter.getNonMaxCount();
        if (nonMax > 0) maxTag += " + " + String.valueOf(nonMax);
        return maxTag;
    }

    @NonNull
    private DatumCounter<DataTag, String> getTagCounter(IdableList<DataTag> data) {
        return new DatumCounter<>(data,
                    new DataGetter<DataTag, String>() {
                        //Overridden
                        @Override
                        public String getData(DataTag datum) {
                            return datum.getTag();
                        }
                    });
    }
}
