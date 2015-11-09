/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 8 July, 2015
 */

package com.chdryra.android.reviewer.View.GvDataAggregation;

import com.chdryra.android.reviewer.View.GvDataModel.GvDataList;
import com.chdryra.android.reviewer.View.GvDataModel.GvSubjectList;

/**
 * Created by: Rizwan Choudrey
 * On: 08/07/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class CanonicalSubjectMode implements CanonicalDatumMaker<GvSubjectList.GvSubject> {
    //Overridden
    @Override
    public GvSubjectList.GvSubject getCanonical(GvDataList<GvSubjectList.GvSubject> data) {
        if (data.size() == 0) return new GvSubjectList.GvSubject(data.getReviewId(), "");

        DatumCounter<GvSubjectList.GvSubject, String> counter = new DatumCounter<>(data,
                new DataGetter<GvSubjectList.GvSubject, String>() {
                    //Overridden
                    @Override
                    public String getData(GvSubjectList.GvSubject datum) {
                        return datum.getString();
                    }
                });

        String maxSubject = counter.getMaxItem();
        int nonMax = counter.getNonMaxCount();
        if (nonMax > 0) maxSubject += " + " + String.valueOf(nonMax);
        return new GvSubjectList.GvSubject(data.getReviewId(), maxSubject);
    }
}
