/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 3 July, 2015
 */

package com.chdryra.android.reviewer.View.GvDataAggregation;

import com.chdryra.android.reviewer.View.GvDataModel.GvDateList;

import java.util.Calendar;

/**
 * Created by: Rizwan Choudrey
 * On: 03/07/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ComparitorGvDate implements DifferenceComparitor<GvDateList.GvDate, DifferenceDate> {

    @Override
    public DifferenceDate compare(GvDateList.GvDate lhs, GvDateList.GvDate rhs) {
        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        cal1.setTime(lhs.getDate());
        cal2.setTime(rhs.getDate());

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
