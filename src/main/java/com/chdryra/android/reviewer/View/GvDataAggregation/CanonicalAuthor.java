/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 8 July, 2015
 */

package com.chdryra.android.reviewer.View.GvDataAggregation;

import com.chdryra.android.reviewer.View.Implementation.GvDataModel.Implementation.Data.GvAuthor;
import com.chdryra.android.reviewer.View.Implementation.GvDataModel.Interfaces.GvDataList;

/**
 * Created by: Rizwan Choudrey
 * On: 08/07/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class CanonicalAuthor implements CanonicalDatumMaker<GvAuthor> {
    //Overridden
    @Override
    public GvAuthor getCanonical(GvDataList<GvAuthor> data) {
        GvAuthor nullAuthor = new GvAuthor(data.getGvReviewId(), "", "");
        if (data.size() == 0) return nullAuthor;

        GvAuthor reference = data.getItem(0);
        ComparitorGvAuthor comparitor = new ComparitorGvAuthor();
        DifferenceBoolean none = new DifferenceBoolean(false);
        for (GvAuthor author : data) {
            if (!comparitor.compare(reference, author).lessThanOrEqualTo(none)) return nullAuthor;
        }

        return new GvAuthor(data.getGvReviewId(), reference.getName(), reference
                .getUserId());
    }
}
