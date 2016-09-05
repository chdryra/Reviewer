/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

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
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvDataAggregator;

/**
 * Created by: Rizwan Choudrey
 * On: 05/10/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ViewerAggregateCriteria extends ViewerAggregateToData<GvCriterion> {
    private final GvDataAggregator mAggregator;

    public ViewerAggregateCriteria(GvCanonicalCollection<GvCriterion> data,
                                   FactoryGridDataViewer viewerFactory,
                                   FactoryReviewViewAdapter adapterFactory,
                                   GvDataAggregator aggregator) {
        super(data, viewerFactory, adapterFactory);
        mAggregator = aggregator;
    }

    @Override
    protected ReviewViewAdapter newDataToReviewsAdapter(GvCanonical datum) {
        GvCanonicalCollection<GvCriterion> aggregate;
        aggregate = mAggregator.aggregateCriteria((GvCriterionList) datum.toList(),
                GvDataAggregator.CriterionAggregation.SUBJECT_RATING);

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

        return getAdapterFactory().newAggregateToReviewsAdapter(aggregate, subject);
    }
}
