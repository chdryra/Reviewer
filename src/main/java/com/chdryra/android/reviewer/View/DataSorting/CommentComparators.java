package com.chdryra.android.reviewer.View.DataSorting;

import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Interfaces.DataComment;

import java.util.Comparator;

/**
 * Created by: Rizwan Choudrey
 * On: 04/09/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class CommentComparators extends ComparatorCollection<DataComment> {
    private static CommentComparators sComparators = new CommentComparators();

    private CommentComparators() {
        super(new HeadlineThenAlphabetical());
    }

    //Static methods
    public static CommentComparators getComparators() {
        return sComparators;
    }

    private static class HeadlineThenAlphabetical implements Comparator<DataComment> {
        //Overridden
        @Override
        public int compare(DataComment lhs, DataComment rhs) {
            int comp;
            if (lhs.isHeadline() && !rhs.isHeadline()) {
                comp = -1;
            } else if (!lhs.isHeadline() && rhs.isHeadline()) {
                comp = 1;
            } else {
                comp = lhs.getComment().compareToIgnoreCase(rhs.getComment());
            }

            return comp;
        }
    }
}
