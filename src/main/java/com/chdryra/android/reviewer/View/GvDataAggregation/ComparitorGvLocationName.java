/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 3 July, 2015
 */

package com.chdryra.android.reviewer.View.GvDataAggregation;

import com.chdryra.android.reviewer.View.GvDataModel.Implementation.Data.GvLocation;

/**
 * Created by: Rizwan Choudrey
 * On: 03/07/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ComparitorGvLocationName extends ComparitorStringable<GvLocation> {
    //Constructors
    public ComparitorGvLocationName() {
        super(new DataGetter<GvLocation, String>() {
            //Overridden
            @Override
            public String getData(GvLocation datum) {
                return datum.getName().toLowerCase();
            }
        });
    }
}
