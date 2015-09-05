package com.chdryra.android.reviewer.View.GvDataSorting;

import com.chdryra.android.reviewer.View.GvDataModel.GvDateList;

import java.util.Comparator;

/**
 * Created by: Rizwan Choudrey
 * On: 04/09/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class DateSorters extends SorterCollection<GvDateList.GvDate> {
    private static DateSorters sSorters = new DateSorters();

    private DateSorters() {
        super(new DateComparator());
    }

    public static DateSorters getSorters() {
        return sSorters;
    }

    private static class DateComparator implements Comparator<GvDateList.GvDate> {
        @Override
        public int compare(GvDateList.GvDate lhs, GvDateList.GvDate rhs) {
            return lhs.getDate().compareTo(rhs.getDate());
        }
    }
}
