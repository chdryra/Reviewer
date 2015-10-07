package com.chdryra.android.reviewer.View.GvDataSorting;

import com.chdryra.android.reviewer.View.GvDataModel.GvCriterionList;

import java.util.Comparator;

/**
 * Created by: Rizwan Choudrey
 * On: 04/09/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ChildReviewComparators extends ComparatorCollection<GvCriterionList.GvCriterion> {
    private static ChildReviewComparators sComparators = new ChildReviewComparators();

    //Constructors
    public ChildReviewComparators() {
        super(new SubjectThenRating());
    }

    //Static methods
    public static ChildReviewComparators getComparators() {
        return sComparators;
    }

    private static class SubjectThenRating implements Comparator<GvCriterionList.GvCriterion> {
        //Overridden
        @Override
        public int compare(GvCriterionList.GvCriterion lhs, GvCriterionList.GvCriterion
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
