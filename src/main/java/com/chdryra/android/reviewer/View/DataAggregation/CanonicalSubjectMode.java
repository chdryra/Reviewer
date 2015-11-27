/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 8 July, 2015
 */

package com.chdryra.android.reviewer.View.DataAggregation;

import android.support.annotation.NonNull;

import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Interfaces.DataSubject;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Interfaces.IdableList;
import com.chdryra.android.reviewer.View.DataAggregation.Interfaces.CanonicalDatumMaker;
import com.chdryra.android.reviewer.View.DataAggregation.Interfaces.DataGetter;

/**
 * Created by: Rizwan Choudrey
 * On: 08/07/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class CanonicalSubjectMode implements CanonicalDatumMaker<DataSubject> {
    //Overridden
    @Override
    public DataSubject getCanonical(IdableList<DataSubject> data) {
        String id = data.getReviewId();
        if (data.size() == 0) return new DatumSubject(id, "");
        return new DatumSubject(id, getSubjectMode(getSubjectCounter(data)));
    }

    private String getSubjectMode(DatumCounter<DataSubject, String> counter) {
        String maxSubject = counter.getMaxItem();
        int nonMax = counter.getNonMaxCount();
        if (nonMax > 0) maxSubject += " + " + String.valueOf(nonMax);
        return maxSubject;
    }

    @NonNull
    private DatumCounter<DataSubject, String> getSubjectCounter(IdableList<DataSubject> data) {
        return new DatumCounter<>(data,
                    new DataGetter<DataSubject, String>() {
                        @Override
                        public String getData(DataSubject datum) {
                            return datum.getSubject();
                        }
                    });
    }

}
