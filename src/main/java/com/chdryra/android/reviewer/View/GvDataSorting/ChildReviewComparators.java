package com.chdryra.android.reviewer.View.GvDataSorting;

import com.chdryra.android.reviewer.View.GvDataModel.GvChildReviewList;

import java.util.Comparator;

/**
 * Created by: Rizwan Choudrey
 * On: 04/09/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ChildReviewComparators extends ComparatorCollection<GvChildReviewList.GvChildReview> {
    private static ChildReviewComparators sComparators = new ChildReviewComparators();

    public ChildReviewComparators() {
        super(new SubjectThenRating());
    }

    public static ChildReviewComparators getComparators() {
        return sComparators;
    }

    private static class SubjectThenRating implements Comparator<GvChildReviewList.GvChildReview> {
        @Override
        public int compare(GvChildReviewList.GvChildReview lhs, GvChildReviewList.GvChildReview
                rhs) {
            int comp = lhs.getSubject().compareToIgnoreCase(rhs.getSubject());
            if (comp == 0) {
                if (lhs.getRating() < rhs.getRating()) {
                    comp = 1;
                } else if (lhs.getRating() > rhs.getRating()) {
                    comp = -1;
                }
            }

            return comp;
        }
    }
}
