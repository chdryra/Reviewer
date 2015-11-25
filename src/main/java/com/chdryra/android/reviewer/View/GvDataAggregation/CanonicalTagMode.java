/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 8 July, 2015
 */

package com.chdryra.android.reviewer.View.GvDataAggregation;

import com.chdryra.android.reviewer.View.GvDataModel.Interfaces.GvDataList;
import com.chdryra.android.reviewer.View.GvDataModel.Implementation.Data.GvTag;

/**
 * Created by: Rizwan Choudrey
 * On: 08/07/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class CanonicalTagMode implements CanonicalDatumMaker<GvTag> {
    //Overridden
    @Override
    public GvTag getCanonical(GvDataList<GvTag> data) {
        if (data.size() == 0) return new GvTag(data.getGvReviewId(), "");

        DatumCounter<GvTag, String> counter = new DatumCounter<>(data,
                new DataGetter<GvTag, String>() {
                    //Overridden
                    @Override
                    public String getData(GvTag datum) {
                        return datum.getString();
                    }
                });

        String maxTag = counter.getMaxItem();
        int nonMax = counter.getNonMaxCount();
        if (nonMax > 0) maxTag += " + " + String.valueOf(nonMax);
        return new GvTag(data.getGvReviewId(), maxTag);
    }
}
