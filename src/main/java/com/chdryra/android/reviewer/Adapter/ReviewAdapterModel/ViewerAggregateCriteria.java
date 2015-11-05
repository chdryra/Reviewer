package com.chdryra.android.reviewer.Adapter.ReviewAdapterModel;

import com.chdryra.android.reviewer.View.GvDataAggregation.FactoryGvDataAggregate;
import com.chdryra.android.reviewer.View.GvDataModel.GvCanonical;
import com.chdryra.android.reviewer.View.GvDataModel.GvCanonicalCollection;
import com.chdryra.android.reviewer.View.GvDataModel.GvCriterionList;

/**
 * Created by: Rizwan Choudrey
 * On: 05/10/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ViewerAggregateCriteria extends ViewerAggregateToData<GvCriterionList.GvCriterion> {
    FactoryGvDataAggregate mAggregateFactory;

    //Constructors
    public ViewerAggregateCriteria(GvCanonicalCollection<GvCriterionList.GvCriterion> data,
                                   FactoryReviewViewAdapter adapterFactory,
                                   FactoryGvDataAggregate aggregateFactory) {
        super(data, adapterFactory);
        mAggregateFactory = aggregateFactory;
    }

    //Overridden
    @Override
    protected ReviewViewAdapter newDataToReviewsAdapter(GvCanonical datum) {
        GvCanonicalCollection<GvCriterionList.GvCriterion> aggregate;
        aggregate = mAggregateFactory.getAggregate((GvCriterionList) datum.toList(), true);

        int diffSubject = 0;
        GvCriterionList.GvCriterion reference = aggregate.getCanonical(0);
        String refSubject = reference.getSubject();
        for (int i = 1; i < aggregate.size(); ++i) {
            GvCriterionList.GvCriterion next = aggregate.getCanonical(i);
            String subject = next.getSubject();
            if (!refSubject.equals(subject)) diffSubject++;
        }

        String diff = diffSubject > 0 ? " + " + String.valueOf(diffSubject) : "";
        String subject = refSubject + diff;

        return getAdapterFactory().newDataToReviewsAdapter(aggregate, subject);
    }
}
