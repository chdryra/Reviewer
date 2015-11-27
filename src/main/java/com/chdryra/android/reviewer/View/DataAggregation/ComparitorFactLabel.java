/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 3 July, 2015
 */

package com.chdryra.android.reviewer.View.DataAggregation;

import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Interfaces.DataFact;
import com.chdryra.android.reviewer.View.DataAggregation.Interfaces.DataGetter;

/**
 * Created by: Rizwan Choudrey
 * On: 03/07/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ComparitorFactLabel extends ComparitorStringable<DataFact> {
    public ComparitorFactLabel() {
        super(new DataGetter<DataFact, String>() {
            @Override
            public String getData(DataFact datum) {
                return datum.getLabel().toLowerCase();
            }
        });
    }
}
