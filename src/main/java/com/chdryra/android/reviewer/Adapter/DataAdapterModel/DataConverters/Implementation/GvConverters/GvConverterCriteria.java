package com.chdryra.android.reviewer.Adapter.DataAdapterModel.DataConverters.Implementation.GvConverters;

import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Interfaces.DataCriterion;
import com.chdryra.android.reviewer.View.GvDataModel.Implementation.Data.GvCriterion;
import com.chdryra.android.reviewer.View.GvDataModel.Implementation.Data.GvCriterionList;

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
