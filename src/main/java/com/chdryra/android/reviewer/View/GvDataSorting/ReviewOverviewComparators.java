package com.chdryra.android.reviewer.View.GvDataSorting;

import com.chdryra.android.reviewer.View.GvDataModel.GvReviewOverviewList;

import java.util.Comparator;

/**
 * Created by: Rizwan Choudrey
 * On: 04/09/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ReviewOverviewComparators extends ComparatorCollection<GvReviewOverviewList
        .GvReviewOverview> {
    private static ReviewOverviewComparators sComparators = new ReviewOverviewComparators();

    private ReviewOverviewComparators() {
        super(new MostRecentPublished());
    }

    //Static methods
    public static ReviewOverviewComparators getComparators() {
        return sComparators;
    }

    private static class MostRecentPublished implements Comparator<GvReviewOverviewList
            .GvReviewOverview> {

        //Overridden
        @Override
        public int compare(GvReviewOverviewList.GvReviewOverview lhs, GvReviewOverviewList
                .GvReviewOverview rhs) {
            return rhs.getPublishDate().compareTo(lhs.getPublishDate());
        }
    }
}
