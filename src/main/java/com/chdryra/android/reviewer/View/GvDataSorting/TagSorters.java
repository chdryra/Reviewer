package com.chdryra.android.reviewer.View.GvDataSorting;

import com.chdryra.android.reviewer.View.GvDataModel.GvTagList;

import java.util.Comparator;

/**
 * Created by: Rizwan Choudrey
 * On: 04/09/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class TagSorters extends SortingCollection<GvTagList.GvTag> {
    private static TagSorters sSorters = new TagSorters();

    private TagSorters() {
        super(new CompareIgnoreCaseComparator());
    }

    public static TagSorters getSorters() {
        return sSorters;
    }

    private static class CompareIgnoreCaseComparator implements Comparator<GvTagList.GvTag> {
        @Override
        public int compare(GvTagList.GvTag lhs, GvTagList.GvTag rhs) {
            return lhs.get().compareToIgnoreCase(rhs.get());
        }
    }
}
