/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 7 July, 2015
 */

package com.chdryra.android.reviewer.View.GvDataAlgorithms;

import com.chdryra.android.reviewer.View.GvDataModel.GvCommentList;

/**
 * Created by: Rizwan Choudrey
 * On: 07/07/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ComparitorGvComment extends ComparitorStringable<GvCommentList.GvComment> {
    public ComparitorGvComment() {
        super(new DataGetter<GvCommentList.GvComment, String>() {
            @Override
            public String getData(GvCommentList.GvComment datum) {
                return datum.getComment().toLowerCase();
            }
        });
    }
}
