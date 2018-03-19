/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.ApplicationPlugins.PlugIns.DataAggregatorsPlugin
        .DataAggregationDefault.Implementation;


import com.chdryra.android.corelibrary.Aggregation.DifferenceComparator;
import com.chdryra.android.corelibrary.Aggregation.DifferenceDate;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DateTime;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by: Rizwan Choudrey
 * On: 03/07/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ComparatorDate implements DifferenceComparator<DateTime, DifferenceDate> {
    @Override
    public DifferenceDate compare(DateTime lhs, DateTime rhs) {
        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        cal1.setTime(new Date(lhs.getTime()));
        cal2.setTime(new Date(rhs.getTime()));

        DifferenceDate.DateBucket difference = DifferenceDate.DateBucket.MORE_THAN_YEAR;
        if (cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR)) {
            difference = DifferenceDate.DateBucket.YEAR;
            int m1 = cal1.get(Calendar.MONTH);
            int m2 = cal2.get(Calendar.MONTH);
            if (cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR)) {
                difference = DifferenceDate.DateBucket.DAY;
            } else if (cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH)) {
                difference = DifferenceDate.DateBucket.MONTH;
            }
        }

        return new DifferenceDate(difference);
    }
}
