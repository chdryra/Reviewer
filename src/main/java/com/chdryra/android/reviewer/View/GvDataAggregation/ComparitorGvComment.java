/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 7 July, 2015
 */

package com.chdryra.android.reviewer.View.GvDataAggregation;

import com.chdryra.android.reviewer.View.GvDataModel.Implementation.GvComment;

/**
 * Created by: Rizwan Choudrey
 * On: 07/07/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ComparitorGvComment extends ComparitorStringable<GvComment> {
    //Constructors
    public ComparitorGvComment() {
        super(new DataGetter<GvComment, String>() {
            //Overridden
            @Override
            public String getData(GvComment datum) {
                return datum.getComment().toLowerCase();
            }
        });
    }
}
