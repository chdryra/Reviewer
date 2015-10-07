package com.chdryra.android.reviewer.Adapter.ReviewAdapterModel;

import android.content.Context;

import com.chdryra.android.reviewer.ReviewsProviderModel.ReviewsRepository;
import com.chdryra.android.reviewer.View.GvDataAggregation.Aggregater;
import com.chdryra.android.reviewer.View.GvDataModel.GvCanonical;
import com.chdryra.android.reviewer.View.GvDataModel.GvCanonicalCollection;
import com.chdryra.android.reviewer.View.GvDataModel.GvCriterionList;

/**
 * Created by: Rizwan Choudrey
 * On: 05/10/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ViewerAggregateCriteria extends ViewerAggregateToData<GvCriterionList.GvCriterion> {
    //Constructors
    public ViewerAggregateCriteria(Context context,
                                   GvCanonicalCollection<GvCriterionList.GvCriterion> data,
                                   ReviewsRepository repository) {
        super(context, data, repository);
    }

    //Overridden
    @Override
    protected ReviewViewAdapter newDataToReviewsAdapter(GvCanonical datum) {
        GvCanonicalCollection<GvCriterionList.GvCriterion> newAggregate
                = Aggregater.aggregateCriteriaMode((GvCriterionList) datum.toList());

        int diffSubject = 0;
        GvCriterionList.GvCriterion reference = newAggregate.getItem(0).getCanonical();
        String refSubject = reference.getSubject();
        for (int i = 1; i < newAggregate.size(); ++i) {
            GvCriterionList.GvCriterion next = newAggregate.getItem(i).getCanonical();
            String subject = next.getSubject();
            if (!refSubject.equals(subject)) diffSubject++;
        }

        String diff = diffSubject > 0 ? " + " + String.valueOf(diffSubject) : "";
        String subject = refSubject + diff;

        return FactoryReviewViewAdapter.newDataToReviewsAdapter(getContext(),
                newAggregate, getRepository(), subject);
    }
}
