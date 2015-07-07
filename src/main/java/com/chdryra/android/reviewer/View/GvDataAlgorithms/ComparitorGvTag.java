/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 3 July, 2015
 */

package com.chdryra.android.reviewer.View.GvDataAlgorithms;

import com.chdryra.android.reviewer.View.GvDataModel.GvTagList;

/**
 * Created by: Rizwan Choudrey
 * On: 03/07/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ComparitorGvTag extends ComparitorStringable<GvTagList.GvTag> {
    public ComparitorGvTag() {
        super(new StringGetter<GvTagList.GvTag>() {
            @Override
            public String getString(GvTagList.GvTag datum) {
                return datum.get().toLowerCase();
            }
        });
    }
}
