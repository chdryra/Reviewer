package com.chdryra.android.reviewer.DataDefinitions.DataConverters.Implementation.GvConverters;

import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataCriterion;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvCriterion;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvCriterionList;

/**
 * Created by: Rizwan Choudrey
 * On: 10/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class GvConverterCriteria extends GvConverterDataReview<DataCriterion,
        GvCriterion, GvCriterionList> {

    public GvConverterCriteria() {
        super(GvCriterionList.class);
    }

    @Override
    public GvCriterion convert(DataCriterion datum) {
        return new GvCriterion(newId(datum.getReviewId()),
                datum.getSubject(), datum.getRating());
    }
}
