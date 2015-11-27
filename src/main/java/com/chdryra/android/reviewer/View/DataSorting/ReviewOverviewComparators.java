package com.chdryra.android.reviewer.View.DataSorting;

import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Interfaces.DataReviewSummary;

import java.util.Comparator;

/**
 * Created by: Rizwan Choudrey
 * On: 04/09/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ReviewOverviewComparators extends ComparatorCollection<DataReviewSummary> {
    private static ReviewOverviewComparators sComparators = new ReviewOverviewComparators();

    private ReviewOverviewComparators() {
        super(new MostRecentPublished());
    }

    //Static methods
    public static ReviewOverviewComparators getComparators() {
        return sComparators;
    }

    private static class MostRecentPublished implements Comparator<DataReviewSummary> {

        //Overridden
        @Override
        public int compare(DataReviewSummary lhs, DataReviewSummary rhs) {
            return DateComparators.getComparators().getDefault().compare(lhs.getPublishDate(), rhs
                    .getPublishDate());
        }
    }
}
