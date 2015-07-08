/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 3 July, 2015
 */

package com.chdryra.android.reviewer.View.GvDataAlgorithms;

import com.chdryra.android.reviewer.View.GvDataModel.GvFactList;

/**
 * Created by: Rizwan Choudrey
 * On: 03/07/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ComparitorGvFactLabel extends ComparitorStringable<GvFactList.GvFact> {
    public ComparitorGvFactLabel() {
        super(new DataGetter<GvFactList.GvFact, String>() {
            @Override
            public String getData(GvFactList.GvFact datum) {
                return datum.getLabel().toLowerCase();
            }
        });
    }
}
