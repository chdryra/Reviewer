package com.chdryra.android.reviewer.View.GvDataSorting;

import com.chdryra.android.reviewer.View.GvDataModel.GvReviewOverviewList;

import java.util.Comparator;

/**
 * Created by: Rizwan Choudrey
 * On: 04/09/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ReviewOverviewSorters extends SortingCollection<GvReviewOverviewList
        .GvReviewOverview> {
    private static ReviewOverviewSorters sSorters = new ReviewOverviewSorters();

    private ReviewOverviewSorters() {
        super(new DefaultComparator());
    }

    public static ReviewOverviewSorters getSorters() {
        return sSorters;
    }

    private static class DefaultComparator implements Comparator<GvReviewOverviewList
            .GvReviewOverview> {

        @Override
        public int compare(GvReviewOverviewList.GvReviewOverview lhs, GvReviewOverviewList
                .GvReviewOverview rhs) {
            return rhs.getPublishDate().compareTo(lhs.getPublishDate());
        }
    }
}
