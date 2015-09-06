package com.chdryra.android.reviewer.View.GvDataSorting;

import com.chdryra.android.reviewer.View.GvDataModel.GvUrlList;

import java.util.Comparator;

/**
 * Created by: Rizwan Choudrey
 * On: 04/09/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class UrlComparators extends ComparatorCollection<GvUrlList.GvUrl> {
    private static UrlComparators sComparators = new UrlComparators();

    private UrlComparators() {
        super(new UrlLabelComparator());
    }

    public static UrlComparators getComparators() {
        return sComparators;
    }

    private static class UrlLabelComparator implements Comparator<GvUrlList.GvUrl> {
        @Override
        public int compare(GvUrlList.GvUrl lhs, GvUrlList.GvUrl rhs) {
            int comp = lhs.getLabel().compareToIgnoreCase(rhs.getLabel());
            if (comp == 0) {
                comp = lhs.getValue().compareToIgnoreCase(rhs.getValue());
            }

            return comp;
        }
    }
}
