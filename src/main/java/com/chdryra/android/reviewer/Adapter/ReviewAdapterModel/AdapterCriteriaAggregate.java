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
    private GvCanonicalCollection<GvCriterionList.GvCriterion> mCriteria;
    private Context mContext;

    public AdapterCriteriaAggregate(Context context, ReviewNode node,
                                    GvCanonicalCollection<GvCriterionList.GvCriterion> criteria) {
        super(node);
        mContext = context;
        mCriteria = criteria;
        ExpanderToReviews<GvCanonical> expander = new ExpanderToReviews<>(mContext, mCriteria);
        setWrapper(new ViewerGvDataCollection<>(expander, mCriteria));
    }

    private GvCanonicalCollection<GvCriterionList.GvCriterion> aggregate
            (GvCanonical<GvCriterionList.GvCriterion> canonical) {
        return Aggregater.aggregateCriteriaMode((GvCriterionList) canonical.toList());
    }

    @Override
    public ReviewViewAdapter<? extends GvData> expandItem(GvCanonical datum) {
        if (!isExpandable(datum)) return null;

        GvCanonicalCollection<GvCriterionList.GvCriterion> newAggregate
                = Aggregater.aggregateCriteriaMode((GvCriterionList) datum.toList());
        return FactoryReviewViewAdapter.newExpandToReviewsAdapter(mContext, newAggregate,
                getSubject());
    }
}
