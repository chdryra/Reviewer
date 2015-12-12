/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 3 July, 2015
 */

package com.chdryra.android.reviewer.DataAlgorithms.DataAggregation.Implementation;

import com.chdryra.android.reviewer.DataAlgorithms.DataAggregation.Interfaces.DifferenceComparitor;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataDateReview;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by: Rizwan Choudrey
 * On: 03/07/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ComparitorDate implements DifferenceComparitor<DataDateReview, DifferenceDate> {
    @Override
    public DifferenceDate compare(DataDateReview lhs, DataDateReview rhs) {
        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        cal1.setTime(new Date(lhs.getTime()));
        cal2.setTime(new Date(rhs.getTime()));

        DifferenceDate.DateBucket difference = DifferenceDate.DateBucket.MORE_THAN_YEAR;
        if (cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR)) {
            if (cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR)) {
                difference = DifferenceDate.DateBucket.DAY;
            } else if (cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH)) {
                difference = DifferenceDate.DateBucket.MONTH;
            } else {
                difference = DifferenceDate.DateBucket.YEAR;
            }
        }

        return new DifferenceDate(difference);
    }
}
