package com.chdryra.android.reviewer.DataAlgorithms.DataSorting.Implementation;

import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataDate;

import java.util.Comparator;
import java.util.Date;

/**
 * Created by: Rizwan Choudrey
 * On: 27/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class DateMostRecentFirst implements Comparator<DataDate> {
    //Overridden
    @Override
    public int compare(DataDate lhs, DataDate rhs) {
        Date lhsDate = new Date(lhs.getTime());
        Date rhsDate = new Date(rhs.getTime());
        return rhsDate.compareTo(lhsDate);
    }
}
