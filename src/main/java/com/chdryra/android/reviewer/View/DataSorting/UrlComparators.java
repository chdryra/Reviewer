package com.chdryra.android.reviewer.View.DataSorting;

import com.chdryra.android.reviewer.View.Implementation.GvDataModel.Implementation.Data.GvUrl;

import java.util.Comparator;

/**
 * Created by: Rizwan Choudrey
 * On: 04/09/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class UrlComparators extends ComparatorCollection<GvUrl> {
    private static UrlComparators sComparators = new UrlComparators();

    private UrlComparators() {
        super(new UrlLabelComparator());
    }

    //Static methods
    public static UrlComparators getComparators() {
        return sComparators;
    }

    private static class UrlLabelComparator implements Comparator<GvUrl> {
        //Overridden
        @Override
        public int compare(GvUrl lhs, GvUrl rhs) {
            int comp = lhs.getLabel().compareToIgnoreCase(rhs.getLabel());
            if (comp == 0) {
                comp = lhs.getValue().compareToIgnoreCase(rhs.getValue());
            }

            return comp;
        }
    }
}
