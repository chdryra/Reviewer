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
public class ViewerCriteria extends ViewerToReviews<GvCanonical> {
    public ViewerCriteria(Context context, GvCanonicalCollection<GvCriterionList.GvCriterion> data,
                          ReviewsRepository repository) {
        super(context, data, repository);
    }

    @Override
    public boolean isExpandable(GvCanonical datum) {
        return datum.getGvDataType() == GvCriterionList.GvCriterion.TYPE && super.isExpandable(datum);
    }

    @Override
    public ReviewViewAdapter expandGridCell(GvCanonical datum) {
        if (!isExpandable(datum)) {
            return null;
        } else if(datum.size() == 1) {
            return super.expandGridCell(datum);
        }

        GvCanonicalCollection<GvCriterionList.GvCriterion> newAggregate
                = Aggregater.aggregateCriteriaMode((GvCriterionList) datum.toList());

        int diffSubject = 0;
        GvCriterionList.GvCriterion reference = newAggregate.getItem(0).getCanonical();
        String refSubject = reference.getSubject();
        for(int i = 1; i < newAggregate.size(); ++i) {
            GvCriterionList.GvCriterion next = newAggregate.getItem(i).getCanonical();
            String subject = next.getSubject();
            if(!refSubject.equals(subject)) diffSubject++;
        }

        String diff = diffSubject > 0 ? " + " + String.valueOf(diffSubject) : "";
        String subject = refSubject + diff;
        return FactoryReviewViewAdapter.newExpandToReviewsAdapterForAggregate(getContext(),
                newAggregate, getReviewsRepository(), subject);
    }
}
