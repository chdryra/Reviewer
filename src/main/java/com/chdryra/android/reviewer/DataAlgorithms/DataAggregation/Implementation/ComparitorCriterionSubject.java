/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 3 July, 2015
 */

package com.chdryra.android.reviewer.DataAlgorithms.DataAggregation.Implementation;

import com.chdryra.android.reviewer.DataAlgorithms.DataAggregation.Interfaces.ComparitorString;
import com.chdryra.android.reviewer.DataAlgorithms.DataAggregation.Interfaces.ItemGetter;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataCriterion;

/**
 * Created by: Rizwan Choudrey
 * On: 03/07/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ComparitorCriterionSubject extends ComparitorStringable<DataCriterion> {
    public ComparitorCriterionSubject(ComparitorString comparitor) {
        super(comparitor, new ItemGetter<DataCriterion, String>() {
            @Override
            public String getItem(DataCriterion datum) {
                return datum.getSubject().toLowerCase();
            }
        });
    }
}
