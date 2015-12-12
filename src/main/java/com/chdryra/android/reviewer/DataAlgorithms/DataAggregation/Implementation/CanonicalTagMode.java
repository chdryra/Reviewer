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
import com.chdryra.android.reviewer.DataAlgorithms.DataAggregation.Interfaces.DataGetter;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.DatumTag;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataTag;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.IdableList;

/**
 * Created by: Rizwan Choudrey
 * On: 08/07/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class CanonicalTagMode implements CanonicalDatumMaker<DataTag> {
    //Overridden
    @Override
    public DataTag getCanonical(IdableList<? extends DataTag> data) {
        ReviewId id = data.getReviewId();
        if (data.size() == 0) return new DatumTag(id, "");
        return new DatumTag(id, getTagMode(getTagCounter(data)));
    }

    private String getTagMode(DatumCounter<? extends DataTag, String> counter) {
        String maxTag = counter.getModeItem();
        int nonMax = counter.getNonModeCount();
        if (nonMax > 0) maxTag += " + " + String.valueOf(nonMax);
        return maxTag;
    }

    @NonNull
    private DatumCounter<DataTag, String> getTagCounter(IdableList<? extends DataTag> data) {
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
