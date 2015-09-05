package com.chdryra.android.reviewer.View.GvDataSorting;

import com.chdryra.android.reviewer.View.GvDataModel.GvFactList;

import java.util.Comparator;

/**
 * Created by: Rizwan Choudrey
 * On: 04/09/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class FactSorters extends SorterCollection<GvFactList.GvFact> {
    private static FactSorters sSorters = new FactSorters();

    private FactSorters() {
        super(new FactComparator());
    }

    public static FactSorters getSorters() {
        return sSorters;
    }

    private static class FactComparator implements Comparator<GvFactList.GvFact> {
        @Override
        public int compare(GvFactList.GvFact lhs, GvFactList.GvFact rhs) {
            int comp = lhs.getLabel().compareTo(rhs.getLabel());
            if (comp == 0) {
                comp = lhs.getValue().compareTo(rhs.getValue());
            }

            return comp;
        }
    }
}
