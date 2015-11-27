/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 3 July, 2015
 */

package com.chdryra.android.reviewer.View.DataAggregation;

import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Interfaces.DataLocation;
import com.chdryra.android.reviewer.View.DataAggregation.Interfaces.DataGetter;

/**
 * Created by: Rizwan Choudrey
 * On: 03/07/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ComparitorLocationName extends ComparitorStringable<DataLocation> {
    public ComparitorLocationName() {
        super(new DataGetter<DataLocation, String>() {
            @Override
            public String getData(DataLocation datum) {
                return datum.getName().toLowerCase();
            }
        });
    }
}
