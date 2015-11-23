package com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewViewing.Implementation;

import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.Interfaces.ReviewViewAdapter;
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewViewing.Factories
        .FactoryGridDataViewer;
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewViewing.Factories
        .FactoryReviewViewAdapter;
import com.chdryra.android.reviewer.View.GvDataAggregation.GvDataAggregater;
import com.chdryra.android.reviewer.View.GvDataModel.Implementation.GvCanonical;
import com.chdryra.android.reviewer.View.GvDataModel.Implementation.GvCanonicalCollection;
import com.chdryra.android.reviewer.View.GvDataModel.Implementation.GvCriterion;
import com.chdryra.android.reviewer.View.GvDataModel.Implementation.GvCriterionList;

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
        aggregate = mAggregater.getAggregate((GvCriterionList) datum.toList(), true);

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
