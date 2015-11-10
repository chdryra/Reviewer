/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 8 July, 2015
 */

package com.chdryra.android.reviewer.View.GvDataAggregation;

import com.chdryra.android.reviewer.View.GvDataModel.GvCommentList;
import com.chdryra.android.reviewer.View.GvDataModel.GvDataList;
import com.chdryra.android.reviewer.View.GvDataModel.GvReviewId;

/**
 * Created by: Rizwan Choudrey
 * On: 08/07/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class CanonicalCommentMode implements CanonicalDatumMaker<GvCommentList.GvComment> {
    //Overridden
    @Override
    public GvCommentList.GvComment getCanonical(GvDataList<GvCommentList.GvComment> data) {
        GvReviewId id = new GvReviewId(data.getReviewId());
        if (data.size() == 0) return new GvCommentList.GvComment(id, "");

        DatumCounter<GvCommentList.GvComment, String> counter = new DatumCounter<>(data,
                new DataGetter<GvCommentList.GvComment, String>() {
                    //Overridden
                    @Override
                    public String getData(GvCommentList.GvComment datum) {
                        return datum.getComment();
                    }
                });

        String maxComment = counter.getMaxItem();
        int nonMax = counter.getNonMaxCount();
        if (nonMax > 0) maxComment += " + " + String.valueOf(nonMax);
        return new GvCommentList.GvComment(id, maxComment);
    }
}
