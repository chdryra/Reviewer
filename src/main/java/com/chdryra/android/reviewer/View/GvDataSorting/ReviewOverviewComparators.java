package com.chdryra.android.reviewer.View.GvDataSorting;

import com.chdryra.android.reviewer.View.GvDataModel.Implementation.Data.GvReviewOverview;

import java.util.Comparator;

/**
 * Created by: Rizwan Choudrey
 * On: 04/09/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ReviewOverviewComparators extends ComparatorCollection<GvReviewOverview> {
    private static ReviewOverviewComparators sComparators = new ReviewOverviewComparators();

    private ReviewOverviewComparators() {
        super(new MostRecentPublished());
    }

    //Static methods
    public static ReviewOverviewComparators getComparators() {
        return sComparators;
    }

    private static class MostRecentPublished implements Comparator<GvReviewOverview> {

        //Overridden
        @Override
        public int compare(GvReviewOverview lhs, GvReviewOverview rhs) {
            return DateComparators.getComparators().getDefault().compare(lhs.getPublishDate(), rhs
                    .getPublishDate());
        }
    }
}
