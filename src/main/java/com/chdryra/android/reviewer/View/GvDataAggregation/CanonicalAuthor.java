/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 8 July, 2015
 */

package com.chdryra.android.reviewer.View.GvDataAggregation;

import com.chdryra.android.reviewer.View.GvDataModel.GvAuthorList;
import com.chdryra.android.reviewer.View.GvDataModel.GvDataList;

/**
 * Created by: Rizwan Choudrey
 * On: 08/07/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class CanonicalAuthor implements CanonicalDatumMaker<GvAuthorList.GvAuthor> {
    @Override
    public GvAuthorList.GvAuthor getCanonical(GvDataList<GvAuthorList.GvAuthor> data) {
        GvAuthorList.GvAuthor nullAuthor = new GvAuthorList.GvAuthor(data.getReviewId(), "", "");
        if (data.size() == 0) return nullAuthor;

        GvAuthorList.GvAuthor reference = data.getItem(0);
        ComparitorGvAuthor comparitor = new ComparitorGvAuthor();
        DifferenceBoolean none = new DifferenceBoolean(false);
        for (GvAuthorList.GvAuthor author : data) {
            if (!comparitor.compare(reference, author).lessThanOrEqualTo(none)) return nullAuthor;
        }

        return new GvAuthorList.GvAuthor(data.getReviewId(), reference.getName(), reference
                .getUserId());
    }
}
