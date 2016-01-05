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
import com.chdryra.android.reviewer.DataDefinitions.Implementation.DatumSubject;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataSubject;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.IdableList;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;

/**
 * Created by: Rizwan Choudrey
 * On: 08/07/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class CanonicalSubjectMode implements CanonicalDatumMaker<DataSubject> {
    @Override
    public DataSubject getCanonical(IdableList<? extends DataSubject> data) {
        ReviewId id = data.getReviewId();
        if (data.size() == 0) return new DatumSubject(id, "");

        return new DatumSubject(id, getSubject(data));
    }

    private String getSubject(IdableList<? extends DataSubject> data) {
        ItemCounter<DataSubject, String> subjectCounter = getSubjectCounter();
        subjectCounter.performCount(data);
        String maxSubject = subjectCounter.getModeItem();
        int nonMax = subjectCounter.getNonModeCount();
        if (nonMax > 0) maxSubject += " + " + String.valueOf(nonMax);
        return maxSubject;
    }

    @NonNull
    private ItemCounter<DataSubject, String> getSubjectCounter() {
        return new ItemCounter<>(new ItemGetter<DataSubject, String>() {
                        @Override
                        public String getData(DataSubject datum) {
                            return datum.getSubject();
                        }
                    });
    }

}
