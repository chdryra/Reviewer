package com.chdryra.android.reviewer.Adapter.DataConverters;

import com.chdryra.android.reviewer.Interfaces.Data.DataCriterion;
import com.chdryra.android.reviewer.View.GvDataModel.GvCriterionList;
import com.chdryra.android.reviewer.View.GvDataModel.GvReviewId;

/**
 * Created by: Rizwan Choudrey
 * On: 10/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class GvConverterCriteria extends GvConverterBasic<DataCriterion,
        GvCriterionList.GvCriterion, GvCriterionList> {

    public GvConverterCriteria() {
        super(GvCriterionList.class);
    }

    @Override
    public GvCriterionList.GvCriterion convert(DataCriterion datum) {
        GvReviewId id = new GvReviewId(datum.getReviewId());
        return new GvCriterionList.GvCriterion(id, datum.getSubject(), datum.getRating());
    }
}
