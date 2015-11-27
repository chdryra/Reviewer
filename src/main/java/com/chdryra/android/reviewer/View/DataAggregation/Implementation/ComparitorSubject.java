/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 3 July, 2015
 */

package com.chdryra.android.reviewer.View.DataAggregation.Implementation;

import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Interfaces.DataSubject;
import com.chdryra.android.reviewer.View.DataAggregation.Interfaces.DataGetter;

/**
 * Created by: Rizwan Choudrey
 * On: 03/07/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ComparitorSubject extends ComparitorStringable<DataSubject> {
    public ComparitorSubject() {
        super(new DataGetter<DataSubject, String>() {
            @Override
            public String getData(DataSubject datum) {
                return datum.getSubject().toLowerCase();
            }
        });
    }
}
