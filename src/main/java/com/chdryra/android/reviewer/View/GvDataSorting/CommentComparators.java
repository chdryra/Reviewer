package com.chdryra.android.reviewer.View.GvDataSorting;

import com.chdryra.android.reviewer.View.GvDataModel.GvCommentList;

import java.util.Comparator;

/**
 * Created by: Rizwan Choudrey
 * On: 04/09/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class CommentComparators extends ComparatorCollection<GvCommentList.GvComment> {
    private static CommentComparators sComparators = new CommentComparators();

    private CommentComparators() {
        super(new HeadlineThenAlphabetical());
    }

    //Static methods
    public static CommentComparators getComparators() {
        return sComparators;
    }

    private static class HeadlineThenAlphabetical implements Comparator<GvCommentList.GvComment> {
        //Overridden
        @Override
        public int compare(GvCommentList.GvComment lhs, GvCommentList.GvComment rhs) {
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
