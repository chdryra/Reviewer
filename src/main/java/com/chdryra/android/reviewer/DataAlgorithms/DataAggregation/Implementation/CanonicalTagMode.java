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
import com.chdryra.android.reviewer.DataDefinitions.Implementation.DatumTag;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataTag;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.IdableList;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;

/**
 * Created by: Rizwan Choudrey
 * On: 08/07/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class CanonicalTagMode implements CanonicalDatumMaker<DataTag> {
    @Override
    public DataTag getCanonical(IdableList<? extends DataTag> data) {
        ReviewId id = data.getReviewId();
        if (data.size() == 0) return new DatumTag(id, "");
        return new DatumTag(id, getTag(data));
    }

    private String getTag(IdableList<? extends DataTag> data) {
        ItemCounter<DataTag, String> tagCounter = getTagCounter();
        tagCounter.performCount(data);
        String maxTag = tagCounter.getModeItem();
        int nonMax = tagCounter.getNonModeCount();
        if (nonMax > 0) maxTag += " + " + String.valueOf(nonMax);
        return maxTag;
    }

    @NonNull
    private ItemCounter<DataTag, String> getTagCounter() {
        return new ItemCounter<>(new ItemGetter<DataTag, String>() {
                        //Overridden
                        @Override
                        public String getData(DataTag datum) {
                            return datum.getTag();
                        }
                    });
    }
}
