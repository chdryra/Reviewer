package com.chdryra.android.reviewer.View.GvDataSorting;

import com.chdryra.android.reviewer.View.GvDataModel.GvText;

import java.util.Comparator;

/**
 * Created by: Rizwan Choudrey
 * On: 04/09/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class TextSorters extends SortingCollection<GvText> {
    private static TextSorters sSorters = new TextSorters();

    private TextSorters() {
        super(new IgnoreCaseComparator());
    }

    public static TextSorters getSorters() {
        return sSorters;
    }

    private static class IgnoreCaseComparator implements Comparator<GvText> {
        @Override
        public int compare(GvText lhs, GvText rhs) {
            return lhs.get().compareToIgnoreCase(rhs.get());
        }
    }
}
