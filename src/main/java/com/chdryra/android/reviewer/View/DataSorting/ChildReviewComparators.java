package com.chdryra.android.reviewer.View.DataSorting;

import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Interfaces.DataCriterion;

import java.util.Comparator;

/**
 * Created by: Rizwan Choudrey
 * On: 04/09/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ChildReviewComparators extends ComparatorCollection<DataCriterion> {
    private static ChildReviewComparators sComparators = new ChildReviewComparators();

    //Constructors
    public ChildReviewComparators() {
        super(new SubjectThenRating());
    }

    //Static methods
    public static ChildReviewComparators getComparators() {
        return sComparators;
    }

    private static class SubjectThenRating implements Comparator<DataCriterion> {
        //Overridden
        @Override
        public int compare(DataCriterion lhs, DataCriterion
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
