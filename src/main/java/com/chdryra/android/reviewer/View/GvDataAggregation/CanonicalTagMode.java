/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 8 July, 2015
 */

package com.chdryra.android.reviewer.View.GvDataAggregation;

import com.chdryra.android.reviewer.View.GvDataModel.GvDataList;
import com.chdryra.android.reviewer.View.GvDataModel.GvTagList;

/**
 * Created by: Rizwan Choudrey
 * On: 08/07/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class CanonicalTagMode implements CanonicalDatumMaker<GvTagList.GvTag> {
    //Overridden
    @Override
    public GvTagList.GvTag getCanonical(GvDataList<GvTagList.GvTag> data) {
        if (data.size() == 0) return new GvTagList.GvTag(data.getReviewId(), "");

        DatumCounter<GvTagList.GvTag, String> counter = new DatumCounter<>(data,
                new DataGetter<GvTagList.GvTag, String>() {
                    //Overridden
                    @Override
                    public String getData(GvTagList.GvTag datum) {
                        return datum.get();
                    }
                });

        String maxTag = counter.getMaxItem();
        int nonMax = counter.getNonMaxCount();
        if (nonMax > 0) maxTag += " + " + String.valueOf(nonMax);
        return new GvTagList.GvTag(data.getReviewId(), maxTag);
    }
}
