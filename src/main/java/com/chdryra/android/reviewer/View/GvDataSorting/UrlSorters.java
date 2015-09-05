package com.chdryra.android.reviewer.View.GvDataSorting;

import com.chdryra.android.reviewer.View.GvDataModel.GvUrlList;

import java.util.Comparator;

/**
 * Created by: Rizwan Choudrey
 * On: 04/09/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class UrlSorters extends SorterCollection<GvUrlList.GvUrl> {
    private static UrlSorters sSorters = new UrlSorters();

    private UrlSorters() {
        super(new UrlLabelComparator());
    }

    public static UrlSorters getSorters() {
        return sSorters;
    }

    private static class UrlLabelComparator implements Comparator<GvUrlList.GvUrl> {
        @Override
        public int compare(GvUrlList.GvUrl lhs, GvUrlList.GvUrl rhs) {
            return lhs.getLabel().compareToIgnoreCase(rhs.getLabel());
        }
    }
}
