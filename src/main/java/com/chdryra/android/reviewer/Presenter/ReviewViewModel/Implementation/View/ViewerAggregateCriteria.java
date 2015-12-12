package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.View;

import com.chdryra.android.reviewer.Presenter.Interfaces.View.ReviewViewAdapter;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Factories.FactoryGridDataViewer;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Factories.FactoryReviewViewAdapter;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData
        .GvCanonical;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData
        .GvCanonicalCollection;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData
        .GvCriterion;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData
        .GvCriterionList;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData
        .GvDataAggregater;

/**
 * Created by: Rizwan Choudrey
 * On: 05/10/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ViewerAggregateCriteria extends ViewerAggregateToData<GvCriterion> {
    GvDataAggregater mAggregater;

    //Constructors
    public ViewerAggregateCriteria(GvCanonicalCollection<GvCriterion> data,
                                   FactoryGridDataViewer viewerFactory,
                                   FactoryReviewViewAdapter adapterFactory,
                                   GvDataAggregater aggregater) {
        super(data, viewerFactory, adapterFactory);
        mAggregater = aggregater;
    }

    //Overridden
    @Override
    protected ReviewViewAdapter newDataToReviewsAdapter(GvCanonical datum) {
        GvCanonicalCollection<GvCriterion> aggregate;
        aggregate = mAggregater.aggregateCriteria((GvCriterionList) datum.toList(),
                GvDataAggregater.CriterionAggregation.SUBJECT_RATING);

        int diffSubject = 0;
        GvCriterion reference = aggregate.getCanonical(0);
        String refSubject = reference.getSubject();
        for (int i = 1; i < aggregate.size(); ++i) {
            GvCriterion next = aggregate.getCanonical(i);
            String subject = next.getSubject();
            if (!refSubject.equals(subject)) diffSubject++;
        }

        String diff = diffSubject > 0 ? " + " + String.valueOf(diffSubject) : "";
        String subject = refSubject + diff;

        return getAdapterFactory().newDataToReviewsAdapter(aggregate, subject);
    }
}
