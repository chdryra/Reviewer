package com.chdryra.android.reviewer.Adapter.ReviewAdapterModel;

import android.content.Context;

import com.chdryra.android.reviewer.Model.ReviewStructure.ReviewNode;
import com.chdryra.android.reviewer.View.GvDataAggregation.Aggregater;
import com.chdryra.android.reviewer.View.GvDataModel.GvCanonical;
import com.chdryra.android.reviewer.View.GvDataModel.GvCanonicalCollection;
import com.chdryra.android.reviewer.View.GvDataModel.GvCriterionList;
import com.chdryra.android.reviewer.View.GvDataModel.GvData;

/**
 * Created by: Rizwan Choudrey
 * On: 29/09/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class AdapterCriteriaAggregate extends AdapterReviewNode<GvCanonical> {
    private Context mContext;

    public AdapterCriteriaAggregate(Context context, ReviewNode node,
                                    GvCanonicalCollection<GvCriterionList.GvCriterion> criteria) {
        super(node);
        mContext = context;
        ExpanderToReviews<GvCanonical> expander = new ExpanderToReviews<>(mContext);
        setWrapper(new ViewerGvDataCollection<>(expander, criteria));
    }

    @Override
    public ReviewViewAdapter<? extends GvData> expandGridCell(GvCanonical datum) {
        if (!isExpandable(datum)) return null;

        GvCanonicalCollection<GvCriterionList.GvCriterion> newAggregate
                = Aggregater.aggregateCriteriaMode((GvCriterionList) datum.toList());

        int diffSubject = 0;
        GvCriterionList.GvCriterion reference = newAggregate.getItem(0).getCanonical();
        String refSubject = reference.getSubject();
        for(int i = 1; i < newAggregate.size(); ++i) {
            GvCriterionList.GvCriterion next = newAggregate.getItem(i).getCanonical();
            String subject = next.getSubject();
            if(!refSubject.equals(refSubject)) diffSubject++;
        }

        String diff = diffSubject > 0 ? " + " + String.valueOf(diffSubject) : "";
        String subject = refSubject + diff;
        return FactoryReviewViewAdapter.newExpandToReviewsAdapter(mContext, newAggregate,
                subject);
    }
}
