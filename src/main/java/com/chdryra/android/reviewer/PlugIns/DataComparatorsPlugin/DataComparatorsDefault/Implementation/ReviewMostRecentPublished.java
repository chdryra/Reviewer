package com.chdryra.android.reviewer.PlugIns.DataComparatorsPlugin.DataComparatorsDefault.Implementation;

import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataDate;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataReviewSummary;

import java.util.Comparator;

/**
 * Created by: Rizwan Choudrey
 * On: 27/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ReviewMostRecentPublished implements Comparator<DataReviewSummary> {
    private Comparator<DataDate> mDateComparator;

    public ReviewMostRecentPublished(Comparator<DataDate> dateComparator) {
        mDateComparator = dateComparator;
    }

    @Override
    public int compare(DataReviewSummary lhs, DataReviewSummary rhs) {
        return mDateComparator.compare(lhs.getPublishDate(), rhs.getPublishDate());
    }
}
