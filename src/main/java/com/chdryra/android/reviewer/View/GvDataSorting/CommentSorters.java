package com.chdryra.android.reviewer.View.GvDataSorting;

import com.chdryra.android.reviewer.View.GvDataModel.GvCommentList;

import java.util.Comparator;

/**
 * Created by: Rizwan Choudrey
 * On: 04/09/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class CommentSorters extends SortingCollection<GvCommentList.GvComment> {
    private static CommentSorters sSorters = new CommentSorters();

    private CommentSorters() {
        super(new HeadlineComparator());
    }

    public static CommentSorters getSorters() {
        return sSorters;
    }

    private static class HeadlineComparator implements Comparator<GvCommentList.GvComment> {
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
