/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvConverters;

import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataCriterion;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewItemReference;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvCriterion;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvCriterionList;

/**
 * Created by: Rizwan Choudrey
 * On: 10/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class GvConverterCriteria extends GvConverterReviewData<DataCriterion,
        GvCriterion, GvCriterionList, GvCriterion.Reference> {

    public GvConverterCriteria() {
        super(GvCriterionList.class, GvCriterion.Reference.TYPE);
    }

    @Override
    public GvCriterion convert(DataCriterion datum, ReviewId reviewId) {
        return new GvCriterion(newId(reviewId), datum.getSubject(), datum.getRating());
    }

    @Override
    protected GvCriterion.Reference convertReference(ReviewItemReference<DataCriterion> reference) {
        return new GvCriterion.Reference(reference, this);
    }

    public static class SubjectOnly extends GvConverterCriteria {
        @Override
        public GvCriterion convert(DataCriterion datum, ReviewId reviewId) {
            return new GvCriterion(newId(reviewId), datum.getSubject(), 0f);
        }
    }
}
