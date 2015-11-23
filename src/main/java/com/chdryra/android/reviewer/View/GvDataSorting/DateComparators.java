package com.chdryra.android.reviewer.View.GvDataSorting;

import com.chdryra.android.reviewer.View.GvDataModel.Implementation.GvDate;

import java.util.Comparator;
import java.util.Date;

/**
 * Created by: Rizwan Choudrey
 * On: 04/09/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class DateComparators extends ComparatorCollection<GvDate> {
    private static DateComparators sComparators = new DateComparators();

    private DateComparators() {
        super(new MostRecentFirst());
    }

    //Static methods
    public static DateComparators getComparators() {
        return sComparators;
    }

    private static class MostRecentFirst implements Comparator<GvDate> {
        //Overridden
        @Override
        public int compare(GvDate lhs, GvDate rhs) {
            Date lhsDate = new Date(lhs.getTime());
            Date rhsDate = new Date(rhs.getTime());
            return rhsDate.compareTo(lhsDate);
        }
    }
}
