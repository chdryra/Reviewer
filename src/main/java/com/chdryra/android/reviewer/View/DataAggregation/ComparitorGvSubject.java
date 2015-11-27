/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 3 July, 2015
 */

package com.chdryra.android.reviewer.View.DataAggregation;

import com.chdryra.android.reviewer.View.Implementation.GvDataModel.Implementation.Data.GvSubject;

/**
 * Created by: Rizwan Choudrey
 * On: 03/07/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ComparitorGvSubject extends ComparitorStringable<GvSubject> {
    //Constructors
    public ComparitorGvSubject() {
        super(new DataGetter<GvSubject, String>() {
            //Overridden
            @Override
            public String getData(GvSubject datum) {
                return datum.getString().toLowerCase();
            }
        });
    }
}