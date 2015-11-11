package com.chdryra.android.reviewer.Adapter.DataAdapterModel.DataConverters;

import com.chdryra.android.reviewer.Interfaces.Data.DataCriterion;
import com.chdryra.android.reviewer.View.GvDataModel.GvCriterionList;

/**
 * Created by: Rizwan Choudrey
 * On: 10/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class GvConverterCriteria extends GvConverterDataReview<DataCriterion,
        GvCriterionList.GvCriterion, GvCriterionList> {

    public GvConverterCriteria() {
        super(GvCriterionList.class);
    }

    @Override
    public GvCriterionList.GvCriterion convert(DataCriterion datum) {
        return new GvCriterionList.GvCriterion(newId(datum.getReviewId()),
                datum.getSubject(), datum.getRating());
    }
}
